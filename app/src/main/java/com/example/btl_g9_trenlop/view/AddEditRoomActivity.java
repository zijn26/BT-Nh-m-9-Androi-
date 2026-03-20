package com.example.btl_g9_trenlop.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_g9_trenlop.R;
import com.example.btl_g9_trenlop.controller.RoomController;
import com.example.btl_g9_trenlop.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * VIEW LAYER - Add/Edit Room
 * --------------------------
 * Màn hình thêm hoặc sửa thông tin phòng trọ.
 *
 * MEMBER phụ trách: Thành viên 5
 */
public class AddEditRoomActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM_ID = "EXTRA_ROOM_ID";

    private EditText etRoomName, etRoomPrice, etTenantName, etPhone;
    private Spinner spinnerStatus;
    private Button btnSave;

    private RoomController controller;
    private String roomId;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_room);

        controller = new RoomController();

        initViews();
        setupSpinner();
        checkMode();

        btnSave.setOnClickListener(v -> saveRoom());
    }

    private void initViews() {
        etRoomName = findViewById(R.id.et_room_name);
        etRoomPrice = findViewById(R.id.et_room_price);
        etTenantName = findViewById(R.id.et_tenant_name);
        etPhone = findViewById(R.id.et_phone);
        spinnerStatus = findViewById(R.id.spinner_status);
        btnSave = findViewById(R.id.btn_save);
    }

    private void setupSpinner() {
        List<String> statuses = new ArrayList<>();
        statuses.add(getString(R.string.status_available));
        statuses.add(getString(R.string.status_rented));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
    }

    private void checkMode() {
        if (getIntent().hasExtra(EXTRA_ROOM_ID)) {
            roomId = getIntent().getStringExtra(EXTRA_ROOM_ID);
            isEditMode = true;
            setTitle(R.string.edit_room);
            loadRoomData();
        } else {
            isEditMode = false;
            setTitle(R.string.add_room);
        }
    }

    private void loadRoomData() {
        // Tìm phòng trong repository thông qua controller
        List<Room> rooms = controller.getRooms();
        Room roomToEdit = null;
        for (Room r : rooms) {
            if (r.getId().equals(roomId)) {
                roomToEdit = r;
                break;
            }
        }

        if (roomToEdit != null) {
            etRoomName.setText(roomToEdit.getName());
            etRoomPrice.setText(String.valueOf(roomToEdit.getPrice()));
            etTenantName.setText(roomToEdit.getTenantName());
            etPhone.setText(roomToEdit.getPhone());
            
            if (roomToEdit.getStatus() == Room.Status.AVAILABLE) {
                spinnerStatus.setSelection(0);
            } else {
                spinnerStatus.setSelection(1);
            }
        } else {
            Toast.makeText(this, "Không tìm thấy dữ liệu phòng!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveRoom() {
        String name = etRoomName.getText().toString().trim();
        String priceStr = etRoomPrice.getText().toString().trim();
        String tenantName = etTenantName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        
        Room.Status status = (spinnerStatus.getSelectedItemPosition() == 0) 
                ? Room.Status.AVAILABLE : Room.Status.RENTED;

        RoomController.RoomCallback callback = new RoomController.RoomCallback() {
            @Override
            public void onSuccess(String message) {
                Toast.makeText(AddEditRoomActivity.this, message, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(AddEditRoomActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        };

        if (isEditMode) {
            controller.updateRoom(roomId, name, priceStr, status, tenantName, phone, callback);
        } else {
            controller.addRoom(name, priceStr, status, tenantName, phone, callback);
        }
    }
}