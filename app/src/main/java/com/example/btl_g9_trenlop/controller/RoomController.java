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
     */
    public void addRoom(String name, String priceStr, Room.Status status,
                        String tenantName, String phone, RoomCallback callback) {
        String error = validateInput(name, priceStr, status, tenantName, phone);
        if (error != null) {
            callback.onError(error);
            return;
        }

        try {
            Room newRoom = new Room();
            newRoom.setId(RoomRepository.generateId());
            newRoom.setName(name.trim());
            newRoom.setPrice(Double.parseDouble(priceStr.trim()));
            newRoom.setStatus(status);

            if (status == Room.Status.RENTED) {
                newRoom.setTenantName(tenantName.trim());
                newRoom.setPhone(phone.trim());
            } else {
                newRoom.setTenantName("");
                newRoom.setPhone("");
            }

            repository.addRoom(newRoom);
            callback.onSuccess("Thêm phòng thành công!");
        } catch (Exception e) {
            callback.onError("Lỗi khi thêm phòng: " + e.getMessage());
        }
    }

    /**
     * Cập nhật phòng.
     */
    public void updateRoom(String id, String name, String priceStr, Room.Status status,
                           String tenantName, String phone, RoomCallback callback) {
        String error = validateInput(name, priceStr, status, tenantName, phone);
        if (error != null) {
            callback.onError(error);
            return;
        }

        try {
            Room updatedRoom = new Room();
            updatedRoom.setId(id);
            updatedRoom.setName(name.trim());
            updatedRoom.setPrice(Double.parseDouble(priceStr.trim()));
            updatedRoom.setStatus(status);

            if (status == Room.Status.RENTED) {
                updatedRoom.setTenantName(tenantName.trim());
                updatedRoom.setPhone(phone.trim());
            } else {
                updatedRoom.setTenantName("");
                updatedRoom.setPhone("");
            }

            repository.updateRoom(updatedRoom);
            callback.onSuccess("Cập nhật phòng thành công!");
        } catch (Exception e) {
            callback.onError("Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    /**
     * Xóa phòng.
     */
    public void deleteRoom(Room room, RoomCallback callback) {
        if (room == null) {
            callback.onError("Phòng không tồn tại hoặc đã bị xóa!");
            return;
        }
        try {
            repository.deleteRoom(room);
            callback.onSuccess("Xóa phòng thành công!");
        } catch (Exception e) {
            callback.onError("Lỗi khi xóa phòng: " + e.getMessage());
        }
    }

    /**
     * Validate dữ liệu đầu vào từ form.
     * @return null nếu hợp lệ, chuỗi thông báo lỗi nếu không hợp lệ
     */
    public String validateInput(String name, String priceStr, Room.Status status,
                                String tenantName, String phone) {
        if (name == null || name.trim().isEmpty()) {
            return "Tên phòng không được để trống";
        }

        if (priceStr == null || priceStr.trim().isEmpty()) {
            return "Giá phòng không được để trống";
        }

        double price;
        try {
            price = Double.parseDouble(priceStr.trim());
            if (price <= 0) {
                return "Giá phòng phải lớn hơn 0";
            }
        } catch (NumberFormatException e) {
            return "Giá phòng phải là số hợp lệ";
        }

        if (status == Room.Status.RENTED) {
            if (tenantName == null || tenantName.trim().isEmpty()) {
                return "Tên người thuê không được để trống khi phòng đã thuê";
            }
            if (phone == null || !phone.trim().matches("^0\\d{9,10}$")) {
                return "Số điện thoại không hợp lệ (10-11 số, bắt đầu bằng số 0)";
            }
        }

        return null;
    }
}
