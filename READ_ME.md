Note:
- Chỉ sử dụng 2 activity: SplashActivity và MainActivity
- MainActivity gồm 1 fragmentContainerView, 1 DrawerLayout(menu), các màn hình còn lại làm hết bằng fragment


Comment:
- Sửa lại UI cho chuẩn design:
    + Toolbar: khoảng cách icon menu, icon search
    + Items ở màn hình danh sách: khoảng cách, size chữ, font style, border
- Khi scraping data ở màn hình trang chủ thì lấy cả link màn hình tiếp theo ở href trong thẻ <a> ở Category
          vd: <h3 class="title"><a title="Học Nấu Món Xào" href="https://www.cet.edu.vn/dao-tao/che-bien-mon-an/cong-thuc/xao" rel="nofollow">Học Nấu Món Xào</a></h3>
- Làm thêm chức năng xem chi tiết cách nấu món ăn:
    + Khi click vào 1 item sẽ mở ra màn hình chi tiết loại đồ ăn(Figma) gồm danh sách các món ăn
    + Sẽ mở theo link màn hình tương ứng như ý bên trên
    + Danh sách các món (lấy danh sách BÀI VIẾT LIÊN QUAN)
          vd: click vào 'Học nấu món xào' -> https://www.cet.edu.vn/dao-tao/che-bien-mon-an/cong-thuc/xao -> scraping data danh sách 'BÀI VIẾT LIÊN QUAN' để hiển thị, tương tự như các món khác.