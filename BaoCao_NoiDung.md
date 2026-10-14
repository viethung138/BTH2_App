# Báo cáo: Ứng dụng Quản lý chi tiêu cá nhân

*(Lưu ý: Bạn hãy copy nội dung này vào file Word (.docx), sau đó chụp ảnh màn hình ứng dụng đang chạy của bạn và chèn vào các vị trí được đánh dấu `[Chèn ảnh...]` tương ứng nhé)*

## 1. Đăng nhập (LoginActivity)

**Hình ảnh:**
* [Chèn ảnh chụp màn hình đăng nhập lúc bình thường]
* [Chèn ảnh chụp màn hình đăng nhập lúc báo lỗi (để trống hoặc sai tài khoản)]

**Mô tả nội dung đã làm:**
- Đã thiết kế giao diện bằng XML (`activity_login.xml`) với các thành phần: `EditText` cho tên đăng nhập (`etUsername`), `EditText` cho mật khẩu dạng ẩn ký tự (`etPassword`), `TextView` để hiển thị thông báo lỗi (`tvError`) và nút Đăng nhập (`btnLogin`).
- Tài khoản cố định được khai báo dưới dạng hằng số trong code `LoginActivity.java`: `ADMIN_USER = "admin"` và `ADMIN_PASS = "123456"`.
- Đã xử lý sự kiện click của nút Đăng nhập: 
  - Lấy dữ liệu từ hai EditText, nếu một trong hai bị bỏ trống thì hiển thị Toast thông báo "Vui lòng nhập đầy đủ thông tin".
  - Nếu nhập sai tài khoản hoặc mật khẩu, `TextView` báo lỗi sẽ được bật hiển thị (`setVisibility(View.VISIBLE)`) dòng chữ "Sai tên đăng nhập hoặc mật khẩu".
  - Nếu thông tin đúng, khởi tạo `Intent` để chuyển sang `MainActivity` và gọi `finish()` để người dùng không quay lại màn hình này bằng nút Back.

---

## 2. Danh sách chi tiêu (MainActivity)

**Hình ảnh:**
* [Chèn ảnh chụp danh sách có dữ liệu mẫu và tổng số tiền]

**Mô tả nội dung đã làm:**
- Giao diện (`activity_main.xml`) gồm: `TextView` hiển thị tổng số tiền (`tvTotalAmount`), `RecyclerView` (`rvExpenses`) hiển thị danh sách các khoản chi, và nút "+ Thêm khoản chi" (`btnAddExpense`). Đã thiết kế thêm nút "Đăng xuất".
- Đã tạo file `item_expense.xml` để thiết kế giao diện cho từng dòng khoản chi (gồm tên, số tiền, danh mục, ngày). Sử dụng `RecyclerView` kết hợp với `ExpenseAdapter` để hiển thị dữ liệu từ danh sách lên màn hình.
- Dữ liệu được quản lý tập trung thông qua lớp `ExpenseManager` (Singleton), chứa sẵn các khoản chi mẫu ban đầu.
- Cách tính và cập nhật tổng tiền: Hàm `updateUI()` (được gọi mỗi khi `onResume()`) duyệt qua toàn bộ danh sách trong `ExpenseManager`, cộng dồn số tiền, định dạng có dấu phân cách hàng nghìn và hiển thị lên giao diện.
- Xử lý nhấn nút "Thêm khoản chi" bằng cách dùng `Intent` chuyển sang `AddExpenseActivity`.
- Xử lý sự kiện nhấn vào một khoản chi cụ thể ngay trong `ExpenseAdapter` (sự kiện click trên `itemView`), truyền đối tượng `Expense` đã chọn (thông qua interface `Serializable`) vào `Intent` và mở `ExpenseDetailActivity`.

---

## 3. Thêm khoản chi (AddExpenseActivity)

**Hình ảnh:**
* [Chèn ảnh chụp màn hình nhập liệu lúc nhập thiếu/sai (hiện thông báo lỗi)]
* [Chèn ảnh chụp màn hình sau khi điền đầy đủ (chuẩn bị bấm Lưu)]

**Mô tả nội dung đã làm:**
- Đã thêm đầy đủ các View cần thiết: `EditText` cho tên khoản chi, `EditText` số tiền (chỉ cho phép nhập số với thuộc tính `inputType="number"`), `Spinner` chứa các danh mục định sẵn, `DatePicker` để chọn ngày tháng, `EditText` ghi chú (nhiều dòng bằng `textMultiLine`), và nút "Lưu khoản chi".
- Xử lý kiểm tra dữ liệu khi nhấn Lưu: 
  - Nếu tên bị bỏ trống, dùng hàm `setError()` của `EditText` để báo lỗi.
  - Nếu số tiền bị bỏ trống, bằng 0, hoặc nhập chữ không hợp lệ, ứng dụng sẽ dùng `setError()` báo lỗi yêu cầu nhập số lớn hơn 0.
- Cách lưu dữ liệu: Thu thập thông tin từ `DatePicker` (ngày/tháng/năm) và các field khác, tạo ra một đối tượng `Expense` mới. Gọi `ExpenseManager.getInstance().addExpense(...)` để thêm vào danh sách chung.
- Hiển thị Toast báo thành công, và gọi `finish()` để đóng màn hình, trở về `MainActivity` (danh sách và tổng tiền sẽ tự động cập nhật).

---

## 4. Chi tiết khoản chi (ExpenseDetailActivity)

**Hình ảnh:**
* [Chèn ảnh chụp màn hình hiển thị thông tin chi tiết]
* [Chèn ảnh chụp màn hình danh sách (MainActivity) sau khi xóa khoản chi đó (để chứng minh đã xóa thành công và tiền bị trừ)]

**Mô tả nội dung đã làm:**
- Thiết kế giao diện gồm các `TextView` tĩnh để hiển thị đầy đủ thông tin: Tên, Số tiền (định dạng tiền tệ), Danh mục, Ngày chi tiêu, Ghi chú.
- Nhận dữ liệu `Expense` được truyền từ màn hình danh sách thông qua lệnh `getIntent().getSerializableExtra("expense")` và gán các giá trị tương ứng lên các TextView.
- Xử lý nút Quay lại: Gọi lệnh `finish()` để đóng màn hình và trở về màn hình trước đó.
- Xử lý nút Xóa: Khi bấm nút "Xóa khoản chi", ứng dụng gọi `ExpenseManager.getInstance().removeExpense(...)` để xóa đối tượng tương ứng khỏi danh sách, hiển thị Toast "Đã xóa khoản chi" và gọi lệnh `finish()` để trở về màn hình danh sách. Tổng số tiền ở màn hình danh sách sẽ tự động giảm đi đúng bằng khoản chi vừa xóa.
