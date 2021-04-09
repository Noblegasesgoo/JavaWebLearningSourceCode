package com.zhao.pojo;

public class StudentAddress {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "StudentAddress{" +
                "address='" + address + '\'' +
                '}';
    }
}
