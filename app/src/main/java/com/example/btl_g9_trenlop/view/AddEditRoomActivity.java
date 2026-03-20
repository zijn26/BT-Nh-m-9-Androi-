package com.example.btl_g9_trenlop.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_g9_trenlop.R;
import com.example.btl_g9_trenlop.controller.RoomController;
import com.example.btl_g9_trenlop.model.Room;

/**
 * VIEW LAYER - Add / Edit Screen
 * ================================
 * AddEditRoomActivity hiển thị form để thêm mới hoặc chỉnh sửa thông tin phòng.
 *
 * Chế độ hoạt động:
 *  - ADD MODE  : không nhận Intent extras → form trống
 *  - EDIT MODE : nhận Intent extras từ MainActivity → form điền sẵn dữ liệu
 *
 * Chức năng:
 *  - Nhập: Name, Price, Status (Spinner), Tenant Name, Phone
 *  - Validate input trước khi lưu (delegate cho Controller)
 *  - Nút Save → lưu và quay về MainActivity với RESULT_OK
 *  - Back button → hủy bỏ (RESULT_CANCELED)
 */
public class AddEditRoomActivity extends AppCompatActivity {

    // -------------------------------------------------------------------------
    // Views
    // -------------------------------------------------------------------------
    private EditText etRoomName;
    private EditText etRoomPrice;
    private Spinner  spinnerStatus;
    private EditText etTenantName;
    private EditText etPhone;
    private Button   btnSave;

    // -------------------------------------------------------------------------
    // Controller & State
    // -------------------------------------------------------------------------
    private RoomController controller;
    private String editRoomId = null; // null = Add mode, có giá trị = Edit mode

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);

        // Khởi tạo Controller
        controller = new RoomController();

        // Ánh xạ Views
        initViews();

        // Thiết lập Spinner trạng thái
        setupStatusSpinner();

        // Kiểm tra mode: Add hay Edit
        checkAndFillEditData();

        // Thiết lập sự kiện nút Save
        setupListeners();
    }

    // -------------------------------------------------------------------------
    // Setup Methods
    // -------------------------------------------------------------------------

    private void initViews() {
        etRoomName    = findViewById(R.id.et_room_name);
        etRoomPrice   = findViewById(R.id.et_room_price);
        spinnerStatus = findViewById(R.id.spinner_status);
        etTenantName  = findViewById(R.id.et_tenant_name);
        etPhone       = findViewById(R.id.et_phone);
        btnSave       = findViewById(R.id.btn_save);
    }

    /**
     * Thiết lập Spinner với 2 lựa chọn: Còn trống / Đã thuê.
     */
    private void setupStatusSpinner() {
        String[] statusOptions = {"Còn trống (Available)", "Đã thuê (Rented)"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                statusOptions
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(spinnerAdapter);
    }

    /**
     * Nếu Intent có chứa EXTRA_ROOM_ID → Edit Mode.
     * Điền sẵn dữ liệu phòng vào form và cập nhật tiêu đề.
     */
    private void checkAndFillEditData() {
        Intent intent = getIntent();
        editRoomId = intent.getStringExtra(MainActivity.EXTRA_ROOM_ID);

        if (editRoomId != null) {
            // EDIT MODE - cập nhật tiêu đề
            setTitle("Chỉnh sửa phòng");

            // Điền dữ liệu vào form
            etRoomName.setText(intent.getStringExtra(MainActivity.EXTRA_ROOM_NAME));
            etRoomPrice.setText(String.valueOf((int) intent.getDoubleExtra(MainActivity.EXTRA_ROOM_PRICE, 0)));
            etTenantName.setText(intent.getStringExtra(MainActivity.EXTRA_ROOM_TENANT));
            etPhone.setText(intent.getStringExtra(MainActivity.EXTRA_ROOM_PHONE));

            // Đặt trạng thái Spinner
            String statusName = intent.getStringExtra(MainActivity.EXTRA_ROOM_STATUS);
            if (Room.Status.RENTED.name().equals(statusName)) {
                spinnerStatus.setSelection(1); // Index 1 = Đã thuê
            } else {
                spinnerStatus.setSelection(0); // Index 0 = Còn trống
            }
        } else {
            // ADD MODE
            setTitle("Thêm phòng mới");
        }
    }

    private void setupListeners() {
        btnSave.setOnClickListener(v -> saveRoom());
    }

    // -------------------------------------------------------------------------
    // Save Logic
    // -------------------------------------------------------------------------

    /**
     * Lấy dữ liệu từ form, gọi Controller để validate & lưu.
     * Nếu thành công → setResult(RESULT_OK) và finish().
     * Nếu lỗi → hiện Toast thông báo lỗi, không đóng màn hình.
     */
    private void saveRoom() {
        // Lấy dữ liệu từ form
        String name       = etRoomName.getText().toString();
        String priceStr   = etRoomPrice.getText().toString();
        String tenantName = etTenantName.getText().toString();
        String phone      = etPhone.getText().toString();

        // Chuyển đổi Spinner selection → Room.Status enum
        Room.Status status = spinnerStatus.getSelectedItemPosition() == 1
                ? Room.Status.RENTED
                : Room.Status.AVAILABLE;

        // Callback xử lý kết quả
        RoomController.RoomCallback callback = new RoomController.RoomCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(AddEditRoomActivity.this, message, Toast.LENGTH_SHORT).show();
                clearForm();
                // Báo cho MainActivity biết đã có thay đổi
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AddEditRoomActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        };

        if (editRoomId != null) {
            // EDIT MODE: cập nhật phòng hiện có
            controller.updateRoom(editRoomId, name, priceStr, status, tenantName, phone, callback);
        } else {
            // ADD MODE: thêm phòng mới
            controller.addRoom(name, priceStr, status, tenantName, phone, callback);
        }
    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    /**
     * Xóa sạch form sau khi lưu thành công.
     */
    private void clearForm() {
        etRoomName.setText("");
        etRoomPrice.setText("");
        etTenantName.setText("");
        etPhone.setText("");
        spinnerStatus.setSelection(0);
    }

    /**
     * Xử lý nút Back trên ActionBar → hủy bỏ thao tác.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
