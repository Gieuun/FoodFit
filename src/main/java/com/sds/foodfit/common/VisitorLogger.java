package com.sds.foodfit.common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class VisitorLogger {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void countVisitor(HttpServletRequest request, HttpServletResponse response) {
	Cookie[] cookies = request.getCookies();
	boolean newVisit = true;

	if (cookies != null) {
	    Optional<Cookie> visitorCookie = Arrays.stream(cookies)
		    .filter(cookie -> cookie.getName().equals("visitorId")).findFirst();
	    if (visitorCookie.isPresent()) {
		newVisit = false;
	    }
	}

	if (newVisit) {
	    String sql = "INSERT INTO visitorLog (visitDate, visitorId) VALUES (?, ?)";
	    String visitorId = getVisitorId();
	    jdbcTemplate.update(sql, Timestamp.valueOf(LocalDateTime.now()), visitorId);

	    Cookie newVisitorCookie = new Cookie("visitorId", "visited");
	    newVisitorCookie.setSecure(true);
	    newVisitorCookie.setHttpOnly(true);
	    newVisitorCookie.setMaxAge(24 * 60 * 60);
	    response.addCookie(newVisitorCookie);
	}
    }

    private String getVisitorId() {
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	if (authentication != null && authentication.isAuthenticated()) {
	    return authentication.getName();
	} else {
	    return "anonymousUser";
	}
    }
}
