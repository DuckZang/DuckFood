package giangpdph27260.fpoly.duckfood.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import giangpdph27260.fpoly.duckfood.MainActivity;
import giangpdph27260.fpoly.duckfood.R;
import giangpdph27260.fpoly.duckfood.adapter.ListFoodAdapter;
import giangpdph27260.fpoly.duckfood.modal.Food;

public class ListFoodFragment extends Fragment {
    private String url;
    private String imgUrlCategory;
    private String titleCategory;
    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_food, container, false);

        // Thực hiện các thao tác khác trên giao diện fragment
        RecyclerView recycler_food = view.findViewById(R.id.recycler_food);
        recycler_food.setLayoutManager(new LinearLayoutManager(getContext()));
        ListFoodAdapter adapter = new ListFoodAdapter();
        recycler_food.setAdapter(adapter);
        Bundle bundle = getArguments();
        if (bundle != null){
            url = bundle.getString("url");
            imgUrlCategory = bundle.getString("img");
            titleCategory = bundle.getString("title");
        }
        // set giao diện cho toolbar

        ImageView background_toolbar = view.findViewById(R.id.backdrop);
        Glide.with(this)
                .load(imgUrlCategory)
                .into(background_toolbar);
        Toolbar toolbar = view.findViewById(R.id.toolbar_list_food);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null){
        actionBar.setTitle(titleCategory);
        }
        ImageView btnBack = view.findViewById(R.id.backButton);
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStackImmediate());
        try {
            List<Food> listFood = new ParseHtmlTask().execute(url).get();
            adapter.setListFood(listFood);
            adapter.setItemDetailFood(food -> {
                Bundle bundleDetail = new Bundle();
                bundleDetail.putString("url",food.getHref());
                bundleDetail.putString("title",food.getFoodTitle());
                bundleDetail.putString("img", food.getFoodImgUrl());
                DetailFoodFragment detailFoodFragment = new DetailFoodFragment();
                detailFoodFragment.setArguments(bundleDetail);

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, detailFoodFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });
        } catch (ExecutionException | InterruptedException e) {
            Log.d("Zzzzzzzzzzzz", "errorScraping: " + e.getMessage());
        }

        return view;
    }
    static class ParseHtmlTask extends AsyncTask<String, Void, List<Food>> {

        @Override
        protected List<Food> doInBackground(String... strings) {
            String url = strings[0];
            List<Food> foodsList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.getElementsByClass("col-md-4 col-sm-4 col-xs-12 item-ltloadmore-posts");

                for (Element e : element) {
                    Log.d(MainActivity.TAG, "element: " + element.outerHtml());
                    Food item = new Food();
                    item.setFoodTitle(e.select(".title a").text());
                    item.setFoodImgUrl(e.select("img").attr("src"));
                    item.setHref(e.select("a").attr("href"));
                    item.setFoodDescription(e.select(".des-info").text());
                    foodsList.add(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            String jsonData = gson.toJson(foodsList);
            Log.d(MainActivity.TAG, "json data : " + jsonData);
            return foodsList;
        }

        @Override
        protected void onPostExecute(List<Food> foods) {
            super.onPostExecute(foods);
            for (Food cat : foods) {
                Log.d(MainActivity.TAG, "Result: " + cat.toString());
            }
        }
    }
}
