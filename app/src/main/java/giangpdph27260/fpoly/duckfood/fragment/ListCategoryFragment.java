package giangpdph27260.fpoly.duckfood.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import java.util.List;

import giangpdph27260.fpoly.duckfood.MainActivity;
import giangpdph27260.fpoly.duckfood.R;
import giangpdph27260.fpoly.duckfood.adapter.ListCategoryAdapter;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class ListCategoryFragment extends Fragment  {
    private SwipeRefreshLayout refreshLayout;
    private final List<Category> listCategory = new ArrayList<>();
    private final ListCategoryAdapter adapter = new ListCategoryAdapter();
    private final String url = "https://www.cet.edu.vn/dao-tao/che-bien-mon-an/cong-thuc";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        DrawerLayout drawerLayout = view.findViewById(R.id.drawerLayout);
        LinearLayout drawer = view.findViewById(R.id.drawer);
        refreshLayout = view.findViewById(R.id.swipeRefresh);
        RecyclerView recycler_category = view.findViewById(R.id.recycler_category);
        recycler_category.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_category.setAdapter(adapter);


        ImageView btnMenu = view.findViewById(R.id.toolbar_menu);
//        ImageView btnSearch = findViewById(R.id.toolbar_search);

        refreshLayout.setOnRefreshListener(() -> {
            adapter.setListCategory(listCategory);
            refreshLayout.setRefreshing(false);
        });

        btnMenu.setOnClickListener(i -> {
            if (drawerLayout.isDrawerOpen(drawer)) {
                drawerLayout.closeDrawer(drawer);
            } else {
                drawerLayout.openDrawer(drawer);
            }
        });

        DatabaseReference categoryDb = FirebaseDatabase.getInstance().getReference("category");
            categoryDb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // dữ liệu tồn tại
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Category model = dataSnapshot.getValue(Category.class);
                            listCategory.add(model);
                            adapter.setListCategory(listCategory);
                        }
                    } else {
                        // dữ liệu không tồn tại
                        Log.d("aaaaaaaaaaa", "ciu taoooooooo ");
                        new ParseHtmlTask().execute(url);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            adapter.setItemCallback(category -> {
                Bundle bundle = new Bundle();
                bundle.putString("url",category.getHref());
                bundle.putString("title",category.getTitle());
                bundle.putString("img", category.getImageUrl());
                ListFoodFragment listFoodFragment = new ListFoodFragment();
                listFoodFragment.setArguments(bundle);

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, listFoodFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });
        return view;
    }

    static class ParseHtmlTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String url = strings[0];
            DatabaseReference categoryDb = FirebaseDatabase.getInstance().getReference("category");
            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.getElementsByClass("col row-box-shadow-5 medium-4 small-12 large-4");
                for (Element e : element) {
                    Log.d(MainActivity.TAG, "element: " + element.outerHtml());
                    Category item = new Category();
                    item.setTitle(e.select("h3.title a").text());
                    item.setImageUrl(e.select("img").attr("src"));
                    item.setHref(e.select("h3.title a").attr("href"));
                    String categoryKey = categoryDb.push().getKey();
                    assert categoryKey != null;
                    categoryDb.child(categoryKey).setValue(item);
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
