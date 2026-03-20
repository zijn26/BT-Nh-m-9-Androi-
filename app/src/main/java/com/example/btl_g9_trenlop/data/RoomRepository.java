package com.example.btl_g9_trenlop.data;

import com.example.btl_g9_trenlop.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * DATA LAYER - Repository
 * -----------------------
 * Lưu trữ dữ liệu phòng trong List<Room> (in-memory).
 * Singleton pattern - chỉ có 1 instance duy nhất.
 *
 * MEMBER phụ trách: Thành viên 1 (Model + Repository)
 */
public class RoomRepository {

    private static RoomRepository instance;
    private final List<Room> roomList = new ArrayList<>();

    private RoomRepository() {
        // TODO: (Tuỳ chọn) Thêm dữ liệu mẫu để test
    }

    public static synchronized RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    // TODO: Implement getAllRooms()
    public List<Room> getAllRooms() {
        return new ArrayList<>(roomList);
    }

    // TODO: Implement addRoom(Room room)
    public void addRoom(Room room) {
        // ...
    }

    // TODO: Implement updateRoom(Room room) - tìm theo id rồi thay thế
    public void updateRoom(Room room) {
        // ...
    }

    // TODO: Implement deleteRoom(Room room) - tìm theo id rồi xóa
    public void deleteRoom(Room room) {
        // ...
    }

    /** Sinh ID duy nhất dựa trên timestamp */
    public static String generateId() {
        return String.valueOf(System.currentTimeMillis());
    }
}
