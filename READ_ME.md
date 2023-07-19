Note:
- Chỉ sử dụng 2 activity: SplashActivity và MainActivity
- MainActivity gồm 1 fragmentContainerView, 1 DrawerLayout(menu), các màn hình còn lại làm hết bằng fragment


Comment:
- Sửa lại UI cho chuẩn design:
    + Toolbar: khoảng cách icon menu, icon search
    + Items ở màn hình danh sách: khoảng cách, size chữ, font style, border
    + Hiển thị loading ở các màn hình category, listFood khi scraping data, lưu vào db, tắt loading khi hiển thị kết quả lên màn hình
    + Click "bắt đầu" ở màn hình Splash vào Home, khi "back" không cho trở về màn hình Splash nữa mà sẽ đóng app
    + Sửa lại các chỗ đặt tên trong màn hình Splash (dùng tiếng anh)