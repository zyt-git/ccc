package com.fh.shop.api.address.vo;

public class AddressVo {
    private Long id;
    private String addressName;
    private String allAddressInfo;
    private String allAreas;

    public String getAllAreas() {
        return allAreas;
    }

    public void setAllAreas(String allAreas) {
        this.allAreas = allAreas;
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

    public String getAllAddressInfo() {
        return allAddressInfo;
    }

    public void setAllAddressInfo(String allAddressInfo) {
        this.allAddressInfo = allAddressInfo;
    }
}
