# HỆ THỐNG QUẢN LÝ THƯ VIỆN (Library Management System)

## Giới thiệu dự án

**Library Management System** là ứng dụng quản lý thư viện được xây dựng bằng **Java** và **JavaFX**, áp dụng các nguyên lý của **Lập trình hướng đối tượng (OOP)**.  

Hệ thống hỗ trợ hai vai trò chính:

- 👨‍💼 **Manager (Quản lý)**
- 👤 **Reader (Độc giả)**

Dự án được thực hiện với mục tiêu mô phỏng một hệ thống quản lý thư viện thực tế, đồng thời thể hiện khả năng thiết kế kiến trúc phần mềm, tổ chức code và áp dụng các nguyên lý OOP vào bài toán thực tế.

---

## Chức năng chính

### 1. Xác thực và phân quyền
- Đăng ký tài khoản
- Đăng nhập
- Quên mật khẩu (gửi email khôi phục)
- Phân quyền theo vai trò (Manager / Reader)

---

### 2. Quản lý sách
- Thêm sách mới
- Chỉnh sửa thông tin sách
- Xóa sách
- Tìm kiếm và lọc sách
- Xem chi tiết sách
- Tạo mã QR cho sách
- Import dữ liệu sách từ Google Books API

---

### 👥 3. Quản lý người dùng
- Xem danh sách độc giả
- Quản lý tài khoản quản lý
- Theo dõi lịch sử mượn sách

---

### 📖 4. Chức năng dành cho độc giả
- Xem danh sách sách có sẵn
- Xem chi tiết sách
- Mượn sách
- Trả sách
- Xem lịch sử mượn sách cá nhân
- Cập nhật thông tin cá nhân

---

## Nguyên lý và mô hình áp dụng

- Lập trình hướng đối tượng (Encapsulation, Inheritance, Polymorphism)
- Mô hình MVC (Model - View - Controller)
- Phân tách rõ ràng giữa giao diện và xử lý nghiệp vụ
- Kiểm thử đơn vị (Unit Testing)

---

## Công nghệ sử dụng

- Java 17+
- JavaFX
- Maven
- MySQL (kết nối qua JDBC)
- Google Books API
- JUnit (Unit Testing)
- Thư viện tạo QR Code
- Gửi email tự động
