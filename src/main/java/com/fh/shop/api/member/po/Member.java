package com.fh.shop.api.member.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member implements Serializable {
    private Long id;
    private String memberName;
    private String password;
    private String realName;
    private  String phone;
    private Long area1;
    private Long area2;
    private Long area3;
    private String email;


}
