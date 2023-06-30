package giangpdph27260.fpoly.duckfood;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Objects;

import giangpdph27260.fpoly.duckfood.adapter.MyAdapter;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class MainActivity extends AppCompatActivity {
    private Category category;
    private ArrayList<Category> listCategory;
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


        btnMenu.setOnClickListener(i -> {
            if (drawerLayout.isDrawerOpen(drawer)) {
                drawerLayout.closeDrawer(drawer);
            } else {
                drawerLayout.openDrawer(drawer);
            }
        });

        String url = "https://www.cet.edu.vn/dao-tao/che-bien-mon-an/cong-thuc";

        new ParseHtmlTask().execute(url);
    }


    @SuppressLint("StaticFieldLeak")
    private class ParseHtmlTask extends AsyncTask<String, Void, Category> {

        @Override
        protected Category doInBackground(String... strings) {
            String url = strings[0];
            listCategory = new ArrayList<>();
            // Phân tích cú pháp HTML
            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.getElementsByClass("col row-box-shadow-5 medium-4 small-12 large-4");

                for (Element e : element){
                    Category item = new Category();
                    item.setTitle(e.select("h3.title a").text());
                    item.setImageUrl(e.select("img").attr("src"));

                    listCategory.add(item);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return category;
        }

        @Override
        protected void onPostExecute(Category category) {
            super.onPostExecute(category);
            MainActivity.this.category = category;
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            RecyclerView.Adapter<MyAdapter.MyViewHolder> adapter = new MyAdapter(listCategory);
            recyclerView.setAdapter(adapter);
        }
    }

}
