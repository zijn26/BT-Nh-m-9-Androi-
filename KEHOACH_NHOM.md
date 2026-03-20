# 📋 KẾ HOẠCH DỰ ÁN - NHÓM 9
## Ứng dụng Quản lý Nhà Trọ (Room Rental Management)

---

## 🏗️ Tổng quan dự án

| Thông tin | Chi tiết |
|-----------|----------|
| **Tên app** | Room Rental Management |
| **Ngôn ngữ** | Java |
| **Kiến trúc** | MVC (Model - View - Controller) |
| **Min SDK** | API 24 (Android 7.0) |
| **Lưu trữ** | List in-memory (không dùng database) |
| **Repository** | https://github.com/zijn26/BT-Nh-m-9-Androi-.git |

---

## 👥 Phân công thành viên (5 người)

### 🔵 Thành viên 1 — Model + Data Layer
**Branch:** `feature/tv1-model-repository`

**File phụ trách:**
- `model/Room.java`
- `data/RoomRepository.java`

**Việc cần làm:**
- [ ] Hoàn thiện `Room.java`:
  - Thêm constructor đầy đủ `(id, name, price, status, tenantName, phone)`
  - Thêm Getter/Setter cho tất cả field
  - Override `toString()`
- [ ] Hoàn thiện `RoomRepository.java`:
  - Implement `addRoom(Room room)` → thêm vào List
  - Implement `updateRoom(Room room)` → tìm theo id, thay thế
  - Implement `deleteRoom(Room room)` → tìm theo id, xóa
  - Thêm dữ liệu mẫu (seed data) trong constructor

**Lưu ý tránh conflict:**
> Chỉ chỉnh sửa 2 file trên. Không đụng vào các file khác.

---

### 🟢 Thành viên 2 — Controller (Business Logic)
**Branch:** `feature/tv2-controller`

**File phụ trách:**
- `controller/RoomController.java`

**Việc cần làm:**
- [ ] Implement `addRoom()`:
  - Gọi `validateInput()` trước
  - Nếu hợp lệ → tạo Room mới → `repository.addRoom()`
  - Gọi `callback.onSuccess()` hoặc `callback.onError()`
- [ ] Implement `updateRoom()`:
  - Validate → tạo Room cập nhật → `repository.updateRoom()`
- [ ] Implement `deleteRoom()`:
  - Kiểm tra null → `repository.deleteRoom()` → callback
- [ ] Implement `validateInput()`:
  - Tên phòng không được để trống
  - Giá phải > 0 và là số hợp lệ
  - Nếu status = RENTED: tên người thuê không trống, SĐT hợp lệ (10-11 số, bắt đầu bằng 0)

**Lưu ý tránh conflict:**
> Chỉ chỉnh sửa `RoomController.java`. Phụ thuộc vào TV1 (Room, Repository) — cần TV1 push xong mới test được đầy đủ.

---

### 🟡 Thành viên 3 — Adapter + Layout Item
**Branch:** `feature/tv3-adapter-item`

**File phụ trách:**
- `view/adapter/RoomAdapter.java`
- `res/layout/item_room.xml`

**Việc cần làm:**
- [ ] Thiết kế `item_room.xml`:
  - Thanh màu bên trái (`view_status_indicator`) — xanh/đỏ
  - `tv_room_name` — tên phòng (chữ to, đậm)
  - `tv_room_price` — giá phòng (format VND)
  - `tv_room_status` — trạng thái (Còn trống / Đã thuê)
  - `tv_tenant_name` — tên người thuê (ẩn khi Available)
- [ ] Implement `bind(Room room)` trong `RoomViewHolder`:
  - Hiển thị tên, giá (format `2,500,000 đ`), trạng thái
  - Màu XANH `#4CAF50` khi AVAILABLE
  - Màu ĐỎ `#F44336` khi RENTED
  - Ẩn `tvTenantName` khi AVAILABLE, hiện khi RENTED

**Lưu ý tránh conflict:**
> Chỉ chỉnh sửa 2 file trên. Không đụng vào `activity_main.xml` hay các Activity.

---

### 🟠 Thành viên 4 — MainActivity + Layout Main
**Branch:** `feature/tv4-main-activity`

**File phụ trách:**
- `view/MainActivity.java`
- `res/layout/activity_main.xml`

**Việc cần làm:**
- [ ] Thiết kế `activity_main.xml`:
  - `Toolbar` với tiêu đề "Quản lý Nhà Trọ"
  - `RecyclerView` (id: `recycler_view_rooms`) chiếm phần lớn màn hình
  - `FloatingActionButton` (+) góc phải dưới (id: `fab_add_room`)
  - (Tuỳ chọn) `TextView` thông báo "Chưa có phòng nào" khi list trống
- [ ] Implement `MainActivity.java`:
  - `initViews()`, `setupRecyclerView()`, `setupListeners()`
  - FAB click → mở `AddEditRoomActivity` (Add mode)
  - `onRoomClick()` → mở `AddEditRoomActivity` (Edit mode, truyền dữ liệu qua Intent)
  - `onRoomLongClick()` → hiện `AlertDialog` xác nhận xóa
  - `refreshRoomList()` → load lại từ Controller
  - `onResume()` → gọi `refreshRoomList()`

**Lưu ý tránh conflict:**
> Chỉ chỉnh sửa 2 file trên. Sử dụng constants `EXTRA_ROOM_*` đã định nghĩa sẵn trong file.

---

### 🔴 Thành viên 5 — AddEditActivity + Layout Form + Manifest
**Branch:** `feature/tv5-addedit-activity`

**File phụ trách:**
- `view/AddEditRoomActivity.java`
- `res/layout/activity_add_edit_room.xml`
- `AndroidManifest.xml` (đăng ký AddEditRoomActivity)
- `res/values/strings.xml` (thêm string resources)
- `res/values/colors.xml` (thêm màu sắc)
- `app/build.gradle` (thêm dependency RecyclerView nếu cần)

**Việc cần làm:**
- [ ] Thiết kế `activity_add_edit_room.xml`:
  - `EditText` nhập Tên phòng (id: `et_room_name`)
  - `EditText` nhập Giá phòng (id: `et_room_price`, inputType: numberDecimal)
  - `Spinner` chọn trạng thái (id: `spinner_status`)
  - `EditText` nhập Tên người thuê (id: `et_tenant_name`)
  - `EditText` nhập SĐT (id: `et_phone`, inputType: phone)
  - `Button` Lưu (id: `btn_save`)
- [ ] Implement `AddEditRoomActivity.java`:
  - Detect mode: Add (không có extra) vs Edit (có `EXTRA_ROOM_ID`)
  - Edit mode: điền sẵn dữ liệu vào form
  - `saveRoom()` → đọc form → gọi controller.addRoom() hoặc controller.updateRoom()
  - Thành công → `setResult(RESULT_OK)` → `finish()`
  - Lỗi → hiện Toast thông báo
- [ ] Đăng ký `AddEditRoomActivity` trong `AndroidManifest.xml`
- [ ] Thêm `androidx.recyclerview:recyclerview` vào `build.gradle`

**Lưu ý tránh conflict:**
> Manifest và build.gradle chỉ có TV5 chỉnh sửa để tránh conflict.

---

## 📁 Cấu trúc project

```
app/src/main/java/com/example/btl_g9_trenlop/
│
├── model/                          ← TV1
│   └── Room.java                   (Model: id, name, price, status, tenantName, phone)
│
├── data/                           ← TV1
│   └── RoomRepository.java         (Singleton, List<Room>, CRUD)
│
├── controller/                     ← TV2
│   └── RoomController.java         (Validate + gọi Repository + Callback)
│
└── view/                           ← TV3, TV4, TV5
    ├── MainActivity.java           (TV4: RecyclerView, FAB, Delete dialog)
    ├── AddEditRoomActivity.java     (TV5: Form thêm/sửa)
    └── adapter/
        └── RoomAdapter.java        (TV3: Bind dữ liệu, màu sắc)

app/src/main/res/layout/
├── activity_main.xml               ← TV4
├── item_room.xml                   ← TV3
└── activity_add_edit_room.xml      ← TV5
```

---

## 🔄 Luồng hoạt động (App Flow)

```
MainActivity
    │
    ├── [onResume] ──────────────────→ controller.getRooms() → adapter.updateData()
    │
    ├── [FAB click] ─────────────────→ AddEditRoomActivity (Add mode)
    │                                         │
    │                                   [Save] → controller.addRoom()
    │                                         │
    │                                   setResult(OK) → finish()
    │
    ├── [Item click] ────────────────→ AddEditRoomActivity (Edit mode + Intent extras)
    │                                         │
    │                                   [Save] → controller.updateRoom()
    │                                         │
    │                                   setResult(OK) → finish()
    │
    └── [Long click] ────────────────→ AlertDialog "Xác nhận xóa?"
                                              │
                                        [Xóa] → controller.deleteRoom() → refresh list
```

---

## 🌿 Quy trình làm việc với Git

### Bước 1: Clone project
```bash
git clone https://github.com/zijn26/BT-Nh-m-9-Androi-.git
```

### Bước 2: Tạo branch của mình
```bash
# Ví dụ TV1:
git checkout -b feature/tv1-model-repository

# TV2:
git checkout -b feature/tv2-controller

# TV3:
git checkout -b feature/tv3-adapter-item

# TV4:
git checkout -b feature/tv4-main-activity

# TV5:
git checkout -b feature/tv5-addedit-activity
```

### Bước 3: Làm việc và commit thường xuyên
```bash
git add .
git commit -m "feat(tv1): implement Room constructor and getters/setters"
git push origin feature/tv1-model-repository
```

### Bước 4: Merge vào main theo thứ tự
```
TV1 (model/data) → merge trước
TV2 (controller) → merge sau TV1
TV3 (adapter)    → merge song song với TV4, TV5
TV4 (main)       → merge sau TV3
TV5 (addedit)    → merge sau TV2, TV4
```

### Convention commit message:
```
feat(tv1): mô tả tính năng mới
fix(tv2): mô tả bug đã sửa
style(tv3): chỉnh sửa giao diện
docs: cập nhật tài liệu
```

---

## ⚠️ Quy tắc để tránh Conflict

| Quy tắc | Chi tiết |
|---------|----------|
| **Mỗi người 1 branch** | Không commit thẳng vào `main` |
| **File riêng biệt** | Mỗi TV phụ trách file khác nhau (xem bảng trên) |
| **Manifest & build.gradle** | Chỉ TV5 chỉnh sửa |
| **Thứ tự merge** | TV1 → TV2 → TV3/TV4/TV5 |
| **Pull trước khi làm** | `git pull origin main` trước khi bắt đầu |
| **Commit nhỏ, thường xuyên** | Tránh commit quá lớn |

---

## 📅 Gợi ý Timeline

| Giai đoạn | Nội dung | Deadline |
|-----------|----------|----------|
| **Setup** | Clone repo, tạo branch, chạy thử app | Ngày 1 |
| **Develop** | Mỗi TV hoàn thành phần của mình | Ngày 2-3 |
| **Integrate** | Merge branch theo thứ tự, fix conflict | Ngày 4 |
| **Test** | Chạy app, kiểm tra toàn bộ luồng | Ngày 5 |
| **Submit** | Push lên GitHub, nộp bài | Ngày 6 |

---

## 📝 Checklist tổng thể

### TV1 - Model + Repository
- [ ] `Room.java` — constructor đầy đủ, getter/setter, toString
- [ ] `RoomRepository.java` — addRoom, updateRoom, deleteRoom, seed data

### TV2 - Controller
- [ ] `RoomController.java` — addRoom, updateRoom, deleteRoom, validateInput

### TV3 - Adapter + Item Layout
- [ ] `item_room.xml` — layout item đẹp, rõ ràng
- [ ] `RoomAdapter.java` — bind data, màu sắc, click/longClick

### TV4 - Main Screen
- [ ] `activity_main.xml` — RecyclerView + FAB
- [ ] `MainActivity.java` — load list, add, edit, delete

### TV5 - Add/Edit Screen + Config
- [ ] `activity_add_edit_room.xml` — form nhập liệu
- [ ] `AddEditRoomActivity.java` — Add/Edit mode, validate, save
- [ ] `AndroidManifest.xml` — đăng ký AddEditRoomActivity
- [ ] `build.gradle` — thêm RecyclerView dependency
- [ ] `strings.xml`, `colors.xml` — resource cần thiết

### Tích hợp
- [ ] Merge tất cả branch vào main
- [ ] App chạy không lỗi
- [ ] Test đầy đủ: thêm / sửa / xóa phòng
- [ ] Push lên GitHub

---

*Chúc cả nhóm làm việc hiệu quả! 🚀*
