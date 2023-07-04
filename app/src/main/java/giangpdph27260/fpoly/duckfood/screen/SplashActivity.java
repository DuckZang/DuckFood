package giangpdph27260.fpoly.duckfood.screen;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import giangpdph27260.fpoly.duckfood.MainActivity;
import giangpdph27260.fpoly.duckfood.R;


@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.home));
        setContentView(R.layout.activity_splash);
        TextView btnStart = findViewById(R.id.btn_batDau);

        btnStart.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        ImageView logoDuckFood = findViewById(R.id.logo_duckfood);
        Glide.with(this)
                .load(R.drawable.logo_duckfood)
                .apply(RequestOptions.circleCropTransform())
                .into(logoDuckFood);


    }
}
