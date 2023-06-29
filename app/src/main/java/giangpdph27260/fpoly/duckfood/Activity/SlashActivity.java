package giangpdph27260.fpoly.duckfood.Activity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.time.Instant;

import giangpdph27260.fpoly.duckfood.R;

public class SlashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.trangchu));
        setContentView(R.layout.activity_slash);

        ImageView logoDuckfood = findViewById(R.id.logo_duckfood);
        Glide.with(this)
                .load(R.drawable.logo_duckfood)
                .apply(RequestOptions.circleCropTransform())
                .into(logoDuckfood);


    }
}
