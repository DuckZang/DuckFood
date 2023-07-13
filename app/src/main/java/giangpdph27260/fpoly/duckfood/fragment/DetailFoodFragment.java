package giangpdph27260.fpoly.duckfood.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import giangpdph27260.fpoly.duckfood.R;

public class DetailFoodFragment extends Fragment {
    private String url;
    private String titleFood;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detail_food,container,false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            url = bundle.getString("url");
            titleFood = bundle.getString("title");
        }
        // set giao diện cho toolbar
        ImageView btnBack = view.findViewById(R.id.btn_back_detail_food);
        TextView tvTitle = view.findViewById(R.id.tv_title_detail_food);
        tvTitle.setText(titleFood);
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStackImmediate());

        WebView wv_detail_food = view.findViewById(R.id.wv_detail_food);
        WebSettings settings = wv_detail_food.getSettings();
        settings.setJavaScriptEnabled(true);
        wv_detail_food.loadUrl(url);
        wv_detail_food.setWebViewClient(new WebViewClient());

    }
}
