package com.example.btl_g9_trenlop.controller;

import com.example.btl_g9_trenlop.data.RoomRepository;
import com.example.btl_g9_trenlop.model.Room;

import java.util.List;

/**
 * CONTROLLER LAYER
 * ----------------
 * Trung gian giữa View và Repository.
 * Xử lý logic nghiệp vụ và validate dữ liệu.
 *
 * MEMBER phụ trách: Thành viên 2 (Controller)
 */
public class RoomController {

    /** Callback trả kết quả về View */
    public interface RoomCallback {
        void onSuccess(String message);
        void onError(String errorMessage);
    }

    private final RoomRepository repository;

    public RoomController() {
        this.repository = RoomRepository.getInstance();
    }

    /** Lấy danh sách tất cả phòng */
    public List<Room> getRooms() {
        return repository.getAllRooms();
    }

    /**
     * Thêm phòng mới.
     * TODO: Validate input → tạo Room → gọi repository.addRoom()
     */
    public void addRoom(String name, String priceStr, Room.Status status,
                        String tenantName, String phone, RoomCallback callback) {
        // TODO: Validate
        // TODO: Tạo đối tượng Room mới
        // TODO: repository.addRoom(newRoom)
        // TODO: callback.onSuccess(...)
    }

    /**
     * Cập nhật phòng.
     * TODO: Validate input → tạo Room cập nhật → gọi repository.updateRoom()
     */
    public void updateRoom(String id, String name, String priceStr, Room.Status status,
                           String tenantName, String phone, RoomCallback callback) {
        // TODO: Validate
        // TODO: Tạo đối tượng Room đã cập nhật
        // TODO: repository.updateRoom(updatedRoom)
        // TODO: callback.onSuccess(...)
    }

    /**
     * Xóa phòng.
     * TODO: Gọi repository.deleteRoom() rồi callback
     */
    public void deleteRoom(Room room, RoomCallback callback) {
        // TODO: repository.deleteRoom(room)
        // TODO: callback.onSuccess(...)
    }

    /**
     * Validate dữ liệu đầu vào từ form.
     * TODO: Kiểm tra name không trống, price > 0, phone hợp lệ khi RENTED
     * @return null nếu hợp lệ, chuỗi thông báo lỗi nếu không hợp lệ
     */
    public String validateInput(String name, String priceStr, Room.Status status,
                                String tenantName, String phone) {
        // TODO: Implement validation
        return null;
    }
}
