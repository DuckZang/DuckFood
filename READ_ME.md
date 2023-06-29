Note:
- Chỉ sử dụng 2 activity: SplashActivity và MainActivity
- MainActivity gồm 1 fragmentContainerView, 1 DrawerLayout(menu), các màn hình còn lại làm hết bằng fragment


Comment:
- Sai chính tả logoDuckfood -> đổi thành logoDuckFood, SlashActivity(đổi thành SplashActivity), ResourcMainActivity (để ý IDE báo gạch chân bên dưới sai chính tả).
- Đặt tên biến, resource (strings, color, drawable, mipmap) bằng tiếng Anh (vd: trangchu -> home).
- Để các file activity trong cùng 1 folder (vd: folder 'screen' gồm [MainActivity, SplashActivity]).
- Các nội dung text không được fix thẳng chữ, mà thay bằng gán strings resource, tương tự như color:
    vd: android:text="+ Bắt đầu" --> android:text="@string/text_button_splash_start"
- Trong các file java, xml chỗ nào báo warning bôi vàng thì fix hết. Dí chuột vào phần bôi vàng nó cũng gợi ý cách fix.