package com.sds.foodfit.domain;

import lombok.Data;

@Data
public class Member {

    private int memberIdx;
    private String name;
    private String id;
    private String pwd;
    private String email;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private Sns sns; // has a 관계로 부모를 보유
    private Role role; // has a 관계로 부모를 보유
    
}