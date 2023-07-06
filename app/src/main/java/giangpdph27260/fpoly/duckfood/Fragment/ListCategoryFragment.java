package giangpdph27260.fpoly.duckfood.Fragment;


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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import giangpdph27260.fpoly.duckfood.adapter.ListCategoryAdapter;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class ListCategoryFragment extends Fragment {
    private DrawerLayout drawerLayout;
    private LinearLayout drawer;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        drawerLayout = view.findViewById(R.id.drawerLayout);
        drawer = view.findViewById(R.id.drawer);
        RecyclerView recycler_category = view.findViewById(R.id.recycler_category);
        recycler_category.setLayoutManager(new LinearLayoutManager(getContext()));
        ListCategoryAdapter adapter = new ListCategoryAdapter();
        recycler_category.setAdapter(adapter);


        ImageView btnMenu = view.findViewById(R.id.toolbar_menu);
//        ImageView btnSearch = findViewById(R.id.toolbar_search);


        btnMenu.setOnClickListener(i -> {
            if (drawerLayout.isDrawerOpen(drawer)) {
                drawerLayout.closeDrawer(drawer);
            } else {
                drawerLayout.openDrawer(drawer);
            }
        });

        String url = "https://www.cet.edu.vn/dao-tao/che-bien-mon-an/cong-thuc";

        try {
            List<Category> listCategory = new ParseHtmlTask().execute(url).get();
            adapter.setListCategory(listCategory);
        } catch (ExecutionException | InterruptedException e) {
            Log.d("Zzzzzzzzzzzz", "errorScraping: " + e.getMessage());
        }

        return view;
    }

    static class ParseHtmlTask extends AsyncTask<String, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(String... strings) {
            String url = strings[0];
            List<Category> categoryList = new ArrayList<>();
            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.getElementsByClass("col row-box-shadow-5 medium-4 small-12 large-4");

                for (Element e : element) {
                    Log.d(MainActivity.TAG, "element: " + element.outerHtml());
                    Category item = new Category();
                    item.setTitle(e.select("h3.title a").text());
                    item.setImageUrl(e.select("img").attr("src"));
                    item.setHref(e.select("h3.title a").attr("href"));
                    categoryList.add(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Gson gson = new Gson();
            String jsonData = gson.toJson(categoryList);
            Log.d(MainActivity.TAG, "json data : " + jsonData);
            return categoryList;
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
