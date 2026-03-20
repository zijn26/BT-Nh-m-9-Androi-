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

    /** Constructor mặc định (không tham số) */
    public Room() { }

    /**
     * Constructor đầy đủ
     *
     * @param id         ID duy nhất của phòng
     * @param name       Tên phòng
     * @param price      Giá thuê (VNĐ/tháng)
     * @param status     Trạng thái: AVAILABLE hoặc RENTED
     * @param tenantName Tên người thuê (để trống nếu AVAILABLE)
     * @param phone      Số điện thoại người thuê (để trống nếu AVAILABLE)
     */
    public Room(String id, String name, double price, Status status,
                String tenantName, String phone) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.tenantName = tenantName;
        this.phone = phone;
    }

    // ─── Getter / Setter ───────────────────────────────────────────────────────

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // ─── toString() ────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", tenantName='" + tenantName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
