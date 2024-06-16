package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class Member {
    private int memberIdx;
    private String name;
    private String id;
    private String pwd;
    private String email;

    private Sns sns; // has a 관계로 부모를 보유
    private Role role; // has a 관계로 부모를 보유
    private MemberDetail memberDetail; // 회원추가정보객체 가짐
    private FavoriteFood favoriteFood; // 마이 푸드

    /*
     * //권한 정보를 반환하는 메서드 public Collection<? extends GrantedAuthority>
     * getAuthorities(){ return Collections.singletonList(new
     * SimpleGrantedAuthority(role.getRoleName())); }
     * 
     * public String getUsername() { return id; } public String getPassword() {
     * return pwd; }
     */
}
