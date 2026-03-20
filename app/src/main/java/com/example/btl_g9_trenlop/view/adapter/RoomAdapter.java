package com.example.btl_g9_trenlop.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl_g9_trenlop.R;
import com.example.btl_g9_trenlop.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * VIEW LAYER - Adapter
 * --------------------
 * Adapter cho RecyclerView hiển thị danh sách phòng.
 * - Tô màu XANH nếu AVAILABLE, ĐỎ nếu RENTED
 * - Cung cấp callback onClick và onLongClick
 *
 * MEMBER phụ trách: Thành viên 3 (Adapter + item_room.xml)
 */
public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    /** Callback click thường → mở màn hình Edit */
    public interface OnRoomClickListener {
        void onRoomClick(Room room);
    }

    /** Callback long click → xác nhận xóa */
    public interface OnRoomLongClickListener {
        void onRoomLongClick(Room room);
    }

    private List<Room> roomList;
    private final OnRoomClickListener clickListener;
    private final OnRoomLongClickListener longClickListener;

    public RoomAdapter(List<Room> roomList,
                       OnRoomClickListener clickListener,
                       OnRoomLongClickListener longClickListener) {
        this.roomList = roomList != null ? roomList : new ArrayList<>();
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        // TODO: Bind dữ liệu room vào holder
        Room room = roomList.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    /** Cập nhật danh sách và refresh giao diện */
    public void updateData(List<Room> newList) {
        this.roomList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    // -------------------------------------------------------------------------
    class RoomViewHolder extends RecyclerView.ViewHolder {

        // TODO: Khai báo các View theo item_room.xml
        TextView tvRoomName;
        TextView tvRoomPrice;
        TextView tvRoomStatus;
        TextView tvTenantName;
        View statusIndicator;

        RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            // TODO: ánh xạ findViewById
            tvRoomName     = itemView.findViewById(R.id.tv_room_name);
            tvRoomPrice    = itemView.findViewById(R.id.tv_room_price);
            tvRoomStatus   = itemView.findViewById(R.id.tv_room_status);
            tvTenantName   = itemView.findViewById(R.id.tv_tenant_name);
            statusIndicator = itemView.findViewById(R.id.view_status_indicator);
        }

        void bind(Room room) {
            // TODO: Hiển thị tên, giá, trạng thái
            // TODO: Tô màu statusIndicator và tvRoomStatus theo room.getStatus()
            // TODO: Ẩn/hiện tvTenantName theo trạng thái

            // Click
            itemView.setOnClickListener(v -> {
                if (clickListener != null) clickListener.onRoomClick(room);
            });
            // Long click
            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null) longClickListener.onRoomLongClick(room);
                return true;
            });
        }
    }
}
