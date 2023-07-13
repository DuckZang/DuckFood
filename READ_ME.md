Note:
- Chỉ sử dụng 2 activity: SplashActivity và MainActivity
- MainActivity gồm 1 fragmentContainerView, 1 DrawerLayout(menu), các màn hình còn lại làm hết bằng fragment


Comment:
- Sửa lại UI cho chuẩn design:
    + Toolbar: khoảng cách icon menu, icon search
    + Items ở màn hình danh sách: khoảng cách, size chữ, font style, border
- Tạo food database bao gồm các bảng:
    + category (id, title, imageUrl, listFoodUrl)
- Xử lý lưu db:
    + Khi bắt đầu vaò màn hình category:
      - Check trong db có tồn tại list category hay không?
      - Nếu có thì đọc db hiển thị
      - Nếu không thì thực hiện scraping, lưu list đã scraping vào db, lưu xong thì đọc data dã lưu ở trong db ra hiển thị