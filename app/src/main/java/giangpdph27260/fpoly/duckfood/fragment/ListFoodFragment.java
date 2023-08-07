package giangpdph27260.fpoly.duckfood.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import giangpdph27260.fpoly.duckfood.MainActivity;
import giangpdph27260.fpoly.duckfood.R;
import giangpdph27260.fpoly.duckfood.adapter.ListFoodAdapter;
import giangpdph27260.fpoly.duckfood.modal.Food;

public class ListFoodFragment extends Fragment {
    private String url;
    private String imgUrlCategory;
    private String titleCategory;
     ListFoodAdapter adapter = new ListFoodAdapter();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("food");
     ArrayList<Food> listFood = new ArrayList<>();
    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_food, container, false);

        // Thực hiện các thao tác khác trên giao diện fragment

        Bundle bundle = getArguments();
        if (bundle != null){
            url = bundle.getString("url");
            imgUrlCategory = bundle.getString("img");
            titleCategory = bundle.getString("title");
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Nút dữ liệu tồn tại
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Food food = dataSnapshot.getValue(Food.class);
                        listFood.add(food);
                    }
                    adapter.setListFood(listFood);
                } else {
                    // Nút dữ liệu không tồn tại
                    new ParseHtmlTask().execute(url) ;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Food food = dataSnapshot.getValue(Food.class);
                        listFood.add(food);
                    }
                    adapter.setListFood(listFood);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recycler_food = view.findViewById(R.id.recycler_food);
        recycler_food.setLayoutManager(new LinearLayoutManager(getContext()));

        recycler_food.setAdapter(adapter);
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
    }

    static class ParseHtmlTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String url = strings[0];
            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.getElementsByClass("col-md-4 col-sm-4 col-xs-12 item-ltloadmore-posts");

                for (Element e : element) {
                    Log.d(MainActivity.TAG, "element: " + element.outerHtml());
                    Food item = new Food();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    item.setFoodTitle(e.select(".title a").text());
                    item.setFoodImgUrl(e.select("img").attr("src"));
                    item.setHref(e.select("a").attr("href"));
                    item.setFoodDescription(e.select(".des-info").text());
                    String foodKey = db.getReference("food").push().getKey();
                    db.getReference("food").child(foodKey).setValue(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
        }
    }
}
