package com.fh.shop.api.address.po;


import com.baomidou.mybatisplus.annotation.TableField;

public class Address {
    private Long id;
    private String addressName;
    private String phone;
    private Long memberId;
    private Long area1;
    private Long area2;
    private Long area3;

    private String address;
    private String email;

    @TableField(exist = false) //字段在表里不存在
    private String allAddressInfo;

    @TableField(exist = false) //字段在表里不存在
    private String allAreas;


    public String getAllAreas() {
        return allAreas;
    }

    public void setAllAreas(String allAreas) {
        this.allAreas = allAreas;
    }

    public String getAllAddressInfo() {
        return allAddressInfo;
    }

    public void setAllAddressInfo(String allAddressInfo) {
        this.allAddressInfo = allAddressInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getArea1() {
        return area1;
    }

    public void setArea1(Long area1) {
        this.area1 = area1;
    }

    public Long getArea2() {
        return area2;
    }

    public void setArea2(Long area2) {
        this.area2 = area2;
    }

    public Long getArea3() {
        return area3;
    }

    public void setArea3(Long area3) {
        this.area3 = area3;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
