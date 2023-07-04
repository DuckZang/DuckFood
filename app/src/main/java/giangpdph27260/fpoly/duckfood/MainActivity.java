package giangpdph27260.fpoly.duckfood;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import giangpdph27260.fpoly.duckfood.adapter.MyAdapter;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private LinearLayout drawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        drawer = findViewById(R.id.drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        ImageView btnMenu = findViewById(R.id.toolbar_menu);
//        ImageView btnSearch = findViewById(R.id.toolbar_search);

        RecyclerView rcvListCat = findViewById(R.id.recyclerView);
        rcvListCat.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter adapter = new MyAdapter();
        rcvListCat.setAdapter(adapter);

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
            Log.d(TAG, "errorScraping: " + e.getMessage());
        }
    }

}

class ParseHtmlTask extends AsyncTask<String, Void, List<Category>> {

    @Override
    protected List<Category> doInBackground(String... strings) {
        String url = strings[0];
        List<Category> categoryList = new ArrayList<>();
        // Phân tích cú pháp HTML
        try {
            Document document = Jsoup.connect(url).get();
            Elements element = document.getElementsByClass("col row-box-shadow-5 medium-4 small-12 large-4");

            for (Element e : element) {
                Category item = new Category();
                item.setTitle(e.select("h3.title a").text());
                item.setImageUrl(e.select("img").attr("src"));
                categoryList.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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

