package giangpdph27260.fpoly.duckfood.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import giangpdph27260.fpoly.duckfood.adapter.ListCategoryAdapter;
import giangpdph27260.fpoly.duckfood.database.MyDatabase;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class ListCategoryFragment extends Fragment  {
    private List<Category> listCategory;
    ListCategoryAdapter adapter = new ListCategoryAdapter();
    String url = "https://www.cet.edu.vn/dao-tao/che-bien-mon-an/cong-thuc";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);
//        ImageView btnSearch = findViewById(R.id.toolbar_search);
        DrawerLayout drawerLayout = view.findViewById(R.id.drawerLayout);
        LinearLayout drawer = view.findViewById(R.id.drawer);
        SwipeRefreshLayout refreshLayout = view.findViewById(R.id.swipeRefresh);
        RecyclerView recycler_category = view.findViewById(R.id.recycler_category);
        recycler_category.setLayoutManager(new LinearLayoutManager(getContext()));
        ImageView btnMenu = view.findViewById(R.id.toolbar_menu);
        recycler_category.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> {
            listCategory = MyDatabase.getInstance(getContext()).categoryDao().getAllCategories();
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int count = MyDatabase.getInstance(getContext()).categoryDao().getCategoryCount();
        if (count <= 0) {
            // Bảng "category" không có dữ liệu
            try {
                listCategory = new ParseHtmlTask(getContext()).execute(url).get();
                adapter.setListCategory(listCategory);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        listCategory = MyDatabase.getInstance(getContext()).categoryDao().getAllCategories();
        adapter.setListCategory(listCategory);

        adapter.setItemCallback(category -> {
            Bundle bundle = new Bundle();
            bundle.putString("url",category.getLinkListFood());
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


    }

    static class ParseHtmlTask extends AsyncTask<String, Void, List<Category>> {
        @SuppressLint("StaticFieldLeak")
        private final Context context;

        public ParseHtmlTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<Category> doInBackground(String... strings) {
            String url = strings[0];
            List<Category> list = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.getElementsByClass("col row-box-shadow-5 medium-4 small-12 large-4");

                for (Element e : element) {
                    Log.d(MainActivity.TAG, "element: " + element.outerHtml());
                    Category item = new Category();
                    item.setTitle(e.select("h3.title a").text());
                    item.setImageUrl(e.select("img").attr("src"));
                    item.setLinkListFood(e.select("h3.title a").attr("href"));
                    MyDatabase.getInstance(context).categoryDao().insertCategory(item);
                    list = MyDatabase.getInstance(context).categoryDao().getAllCategories();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);
            for (Category cat : categories) {
                Log.d(MainActivity.TAG, "Result: " + cat.toString());
            }
        }
    }
}
