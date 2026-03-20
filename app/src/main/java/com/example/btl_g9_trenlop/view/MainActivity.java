package com.example.btl_g9_trenlop.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_g9_trenlop.R;
import com.example.btl_g9_trenlop.controller.RoomController;
import com.example.btl_g9_trenlop.model.Room;
import com.example.btl_g9_trenlop.view.adapter.RoomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * VIEW LAYER - Main Screen
 * ========================
 * MainActivity là màn hình chính hiển thị danh sách phòng trọ.
 *
 * Chức năng:
 *  - Hiển thị danh sách phòng bằng RecyclerView
 *  - Nút FAB (+) để thêm phòng mới
 *  - Click item → mở AddEditRoomActivity để chỉnh sửa
 *  - Long click item → hiện AlertDialog xác nhận xóa
 *  - Refresh danh sách sau mỗi thao tác CRUD
 *
 * Chỉ tương tác với Controller - không gọi Repository trực tiếp.
 */
public class MainActivity extends AppCompatActivity
        implements RoomAdapter.OnRoomClickListener,
                   RoomAdapter.OnRoomLongClickListener {

    // -------------------------------------------------------------------------
    // Constants - dùng để truyền dữ liệu qua Intent
    // -------------------------------------------------------------------------
    public static final String EXTRA_ROOM_ID          = "extra_room_id";
    public static final String EXTRA_ROOM_NAME        = "extra_room_name";
    public static final String EXTRA_ROOM_PRICE       = "extra_room_price";
    public static final String EXTRA_ROOM_STATUS      = "extra_room_status";
    public static final String EXTRA_ROOM_TENANT      = "extra_room_tenant";
    public static final String EXTRA_ROOM_PHONE       = "extra_room_phone";
    public static final int    REQUEST_CODE_ADD_EDIT  = 100;

    // -------------------------------------------------------------------------
    // Views
    // -------------------------------------------------------------------------
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddRoom;

    // -------------------------------------------------------------------------
    // Controller & Adapter
    // -------------------------------------------------------------------------
    private RoomController controller;
    private RoomAdapter adapter;

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo Controller
        controller = new RoomController();

        // Ánh xạ Views
        initViews();

        // Thiết lập RecyclerView
        setupRecyclerView();

        // Thiết lập sự kiện
        setupListeners();
    }

    /**
     * Gọi lại mỗi khi Activity trở về foreground (sau khi Add/Edit).
     * Refresh danh sách để đảm bảo dữ liệu luôn mới nhất.
     */
    @Override
    protected void onResume() {
        super.onResume();
        refreshRoomList();
    }

    // -------------------------------------------------------------------------
    // Setup Methods
    // -------------------------------------------------------------------------

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_view_rooms);
        fabAddRoom   = findViewById(R.id.fab_add_room);
    }

    private void setupRecyclerView() {
        List<Room> rooms = controller.getRooms();
        adapter = new RoomAdapter(rooms, this, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupListeners() {
        // FAB: mở màn hình thêm phòng mới
        fabAddRoom.setOnClickListener(v -> openAddRoomScreen());
    }

    // -------------------------------------------------------------------------
    // Navigation
    // -------------------------------------------------------------------------

    /**
     * Mở AddEditRoomActivity ở chế độ THÊM MỚI.
     * Không truyền dữ liệu phòng → AddEdit biết đây là mode Add.
     */
    private void openAddRoomScreen() {
        Intent intent = new Intent(this, AddEditRoomActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_EDIT);
    }

    /**
     * Mở AddEditRoomActivity ở chế độ CHỈNH SỬA.
     * Truyền toàn bộ dữ liệu phòng qua Intent extras.
     */
    private void openEditRoomScreen(Room room) {
        Intent intent = new Intent(this, AddEditRoomActivity.class);
        intent.putExtra(EXTRA_ROOM_ID,     room.getId());
        intent.putExtra(EXTRA_ROOM_NAME,   room.getName());
        intent.putExtra(EXTRA_ROOM_PRICE,  room.getPrice());
        intent.putExtra(EXTRA_ROOM_STATUS, room.getStatus().name());
        intent.putExtra(EXTRA_ROOM_TENANT, room.getTenantName());
        intent.putExtra(EXTRA_ROOM_PHONE,  room.getPhone());
        startActivityForResult(intent, REQUEST_CODE_ADD_EDIT);
    }

    // -------------------------------------------------------------------------
    // RoomAdapter Callbacks
    // -------------------------------------------------------------------------

    /**
     * Xử lý click thường → mở màn hình chỉnh sửa.
     */
    @Override
    public void onRoomClick(Room room) {
        openEditRoomScreen(room);
    }

    /**
     * Xử lý long click → hiện dialog xác nhận xóa.
     */
    @Override
    public void onRoomLongClick(Room room) {
        showDeleteConfirmDialog(room);
    }

    // -------------------------------------------------------------------------
    // Delete Dialog
    // -------------------------------------------------------------------------

    /**
     * Hiển thị AlertDialog xác nhận xóa phòng.
     * Chỉ xóa khi người dùng nhấn "Xóa".
     */
    private void showDeleteConfirmDialog(Room room) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa phòng \"" + room.getName() + "\" không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    controller.deleteRoom(room, new RoomController.RoomCallback() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            refreshRoomList();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // -------------------------------------------------------------------------
    // Data Refresh
    // -------------------------------------------------------------------------

    /**
     * Tải lại danh sách phòng từ Controller và cập nhật RecyclerView.
     */
    private void refreshRoomList() {
        List<Room> rooms = controller.getRooms();
        adapter.updateData(rooms);
    }

    // -------------------------------------------------------------------------
    // Activity Result (deprecated but still works for Java)
    // -------------------------------------------------------------------------

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_EDIT && resultCode == RESULT_OK) {
            // Refresh danh sách sau khi thêm/sửa thành công
            refreshRoomList();
        }
    }
}
