package com.example.btl_g9_trenlop.model;

/**
 * MODEL LAYER
 * -----------
 * Đại diện cho một phòng trọ.
 * Chỉ chứa dữ liệu, không có logic xử lý.
 *
 * MEMBER phụ trách: Thành viên 1 (Model + Repository)
 */
public class Room {

    public enum Status {
        AVAILABLE,
        RENTED
    }

    private String id;
    private String name;
    private double price;
    private Status status;
    private String tenantName;
    private String phone;

    // TODO: Thêm constructor đầy đủ
    public Room() { }

    // TODO: Thêm Getter / Setter cho từng field

    // TODO: Override toString()
}
