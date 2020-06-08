package com.gy.entity;

public class BookInfo {
    private Integer id;
    private Integer screenId;
    private Integer seatX;
    private Integer seatY;
    private String phone;
    private String validCode;
    private Integer status;  //0表示未取票，1表示已取票
    public BookInfo() {
    }

    public BookInfo(Integer id, Integer screenId, Integer seatX, Integer seatY,
                    String phone, String validCode, Integer status) {
        super();
        this.id = id;
        this.screenId = screenId;
        this.seatX = seatX;
        this.seatY = seatY;
        this.phone = phone;
        this.validCode = validCode;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getScreenId() {
        return screenId;
    }
    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }
    public Integer getSeatX() {
        return seatX;
    }
    public void setSeatX(Integer seatX) {
        this.seatX = seatX;
    }
    public Integer getSeatY() {
        return seatY;
    }
    public void setSeatY(Integer seatY) {
        this.seatY = seatY;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getValidCode() {
        return validCode;
    }
    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }


}
