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
import java.util.List;

import giangpdph27260.fpoly.duckfood.MainActivity;
import giangpdph27260.fpoly.duckfood.R;
import giangpdph27260.fpoly.duckfood.adapter.ListCategoryAdapter;
import giangpdph27260.fpoly.duckfood.database.CategoryDAO;
import giangpdph27260.fpoly.duckfood.database.CategoryDatabase;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class ListCategoryFragment extends Fragment  {

    private SwipeRefreshLayout refreshLayout;
//    private List<Category> listCategory;
    private CategoryDatabase db;
    private CategoryDAO categoryDAO;
    String url = "https://www.cet.edu.vn/dao-tao/che-bien-mon-an/cong-thuc";
    ListCategoryAdapter adapter = new ListCategoryAdapter();
    List listCategory = null ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

//        db = CategoryDatabase.getInstance(getActivity());
//        categoryDAO = db.categoryDao();

        DrawerLayout drawerLayout = view.findViewById(R.id.drawerLayout);
        LinearLayout drawer = view.findViewById(R.id.drawer);
        refreshLayout = view.findViewById(R.id.swipeRefresh);
        RecyclerView recycler_category = view.findViewById(R.id.recycler_category);
        recycler_category.setLayoutManager(new LinearLayoutManager(getContext()));

        recycler_category.setAdapter(adapter);

        ImageView btnMenu = view.findViewById(R.id.toolbar_menu);
//        ImageView btnSearch = findViewById(R.id.toolbar_search);

        refreshLayout.setOnRefreshListener(() -> {
//            listCategory = categoryDAO.getListCategory();

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
        new ParseHtmlTask(getContext()).execute(url);
//        listCategory = categoryDAO.getListCategory();
        adapter.setListCategory(listCategory);

            adapter.setItemCallback(category -> {
                Bundle bundle = new Bundle();
                bundle.putString("url",category.getListFoodUrl());
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
        @SuppressLint("StaticFieldLeak")
        private final Context context;

        public ParseHtmlTask(Context context) {
            this.context = context;
        }
        @Override
        protected Void doInBackground(String... strings) {
            String url = strings[0];
            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.getElementsByClass("col row-box-shadow-5 medium-4 small-12 large-4");

                for (Element e : element) {
                    Category item = new Category();
                    Log.d(MainActivity.TAG, "element: " + element.outerHtml());
                    item.setTitle(e.select("h3.title a").text());
                    item.setImageUrl(e.select("img").attr("src"));
                    item.setListFoodUrl(e.select("h3.title a").attr("href"));
                    // thêm dữ liệu vào db
                    CategoryDatabase.getInstance(context).categoryDao().insertCategory(item);
                    Log.d("zzzzzzzzzzzzzzzz", "ciuu taooooo helppp meeeee");
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
