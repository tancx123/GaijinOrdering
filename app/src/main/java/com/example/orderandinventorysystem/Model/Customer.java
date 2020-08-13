package com.example.orderandinventorysystem.Model;

import java.io.Serializable;

public class Customer implements Serializable {

    private String custID, custName, icNo, email, phone, mobile, companyName, gender, custType, address;

    public Customer(String custID, String custName, String icNo, String email, String phone, String mobile, String companyName, String gender, String custType, String address) {

        this.custID = custID;
        this.custName = custName;
        this.icNo = icNo;
        this.email = email;
        this.phone = phone;
        this.mobile = mobile;
        this.companyName = companyName;
        this.gender = gender;
        this.custType = custType;
        this.address = address;
    }

    public String getCustID(){
        return custID;
    }

    public void setCustID(String custID){
        this.custID = custID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
