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
        // Seed data: dữ liệu mẫu để test
        seedData();
    }

    public static synchronized RoomRepository getInstance() {
        if (instance == null) {
            instance = new RoomRepository();
        }
        return instance;
    }

    // ─── Seed Data ─────────────────────────────────────────────────────────────

    /**
     * Thêm dữ liệu mẫu vào danh sách khi khởi tạo lần đầu.
     */
    private void seedData() {
        roomList.add(new Room(
                generateId(),
                "Phòng 101",
                2_500_000,
                Room.Status.RENTED,
                "Nguyễn Văn An",
                "0901234567"
        ));

        // Delay 1ms để tránh trùng timestamp ID
        try { Thread.sleep(1); } catch (InterruptedException ignored) { }

        roomList.add(new Room(
                generateId(),
                "Phòng 102",
                2_000_000,
                Room.Status.AVAILABLE,
                "",
                ""
        ));

        try { Thread.sleep(1); } catch (InterruptedException ignored) { }

        roomList.add(new Room(
                generateId(),
                "Phòng 103",
                3_000_000,
                Room.Status.RENTED,
                "Trần Thị Bình",
                "0912345678"
        ));

        try { Thread.sleep(1); } catch (InterruptedException ignored) { }

        roomList.add(new Room(
                generateId(),
                "Phòng 201",
                2_200_000,
                Room.Status.AVAILABLE,
                "",
                ""
        ));

        try { Thread.sleep(1); } catch (InterruptedException ignored) { }

        roomList.add(new Room(
                generateId(),
                "Phòng 202",
                3_500_000,
                Room.Status.RENTED,
                "Lê Minh Châu",
                "0987654321"
        ));
    }

    // ─── CRUD Methods ──────────────────────────────────────────────────────────

    /**
     * Lấy toàn bộ danh sách phòng (trả về bản sao để tránh bị chỉnh sửa ngoài).
     */
    public List<Room> getAllRooms() {
        return new ArrayList<>(roomList);
    }

    /**
     * Thêm một phòng mới vào danh sách.
     *
     * @param room Phòng cần thêm (không được null)
     */
    public void addRoom(Room room) {
        if (room == null) return;
        roomList.add(room);
    }

    /**
     * Cập nhật thông tin phòng đã có (tìm theo id, thay thế toàn bộ).
     *
     * @param room Phòng với dữ liệu mới (phải có id trùng với phòng cần cập nhật)
     */
    public void updateRoom(Room room) {
        if (room == null || room.getId() == null) return;
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId().equals(room.getId())) {
                roomList.set(i, room);
                return;
            }
        }
    }

    /**
     * Xóa một phòng khỏi danh sách (tìm theo id).
     *
     * @param room Phòng cần xóa (phải có id hợp lệ)
     */
    public void deleteRoom(Room room) {
        if (room == null || room.getId() == null) return;
        for (int i = 0; i < roomList.size(); i++) {
            if (roomList.get(i).getId().equals(room.getId())) {
                roomList.remove(i);
                return;
            }
        }
    }

    // ─── Utility ───────────────────────────────────────────────────────────────

    /**
     * Sinh ID duy nhất dựa trên timestamp hiện tại.
     */
    public static String generateId() {
        return String.valueOf(System.currentTimeMillis());
    }
}
