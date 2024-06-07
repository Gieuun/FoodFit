package com.sds.foodfit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sds.foodfit.domain.CustomUserDetails;
import com.sds.foodfit.domain.Member;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

//모든 컨트롤러에서 사용자명을 저장할 수 있도록 aop 구현

@Slf4j
//@Aspect
//@Component
public class AuthAspect {
	
	//포함시킬 포인터컷
	@Pointcut("execution(public * com.sds.foodfit.controller..*(..))")
	public void incluedExecution() {}
	
	//제외시킬 포인터컷
	@Pointcut("!execution(public * com.sds.foodfit.controller.Rest*Controller.*(..))")
	public void excludeExecution() {}
	
	//하위 컨트롤러로 들어가는 메서드 호출을 가로채서, 사용자 이름을 심어놓아야, main.html에서 사용자 이름을 출력할 수 있다.
	@Around("incluedExecution() && excludeExecution()")
	public Object checkSession(ProceedingJoinPoint joinPoint) throws Throwable {
		Object obj = null;
		
		
		//스플링으로부터 세션을 얻기
		ServletRequestAttributes attr =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
			Member member = userDetails.getMember();
			
			request.setAttribute("name", member.getName());
			request.setAttribute("member", member);
			
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			
		}
		
		obj = joinPoint.proceed();
		
		return obj;
	}
}


