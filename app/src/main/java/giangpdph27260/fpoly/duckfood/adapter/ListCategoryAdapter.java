package giangpdph27260.fpoly.duckfood.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import giangpdph27260.fpoly.duckfood.fragment.ListFoodFragment;
import giangpdph27260.fpoly.duckfood.R;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.MyViewHolder> {
    private ArrayList<Category> listCategory = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setListCategory(List<Category> categories) {
        this.listCategory = new ArrayList<>(categories);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategoryAdapter.MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgItemCategory;
        private final TextView tvItemTitle;
        private String dataUrl;
        private String title;
        private String imgUrl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                //TODO xử lý click item

                Bundle bundle = new Bundle();
                bundle.putString("url",dataUrl);
                bundle.putString("title",title);
                bundle.putString("img",imgUrl);
                ListFoodFragment listFoodFragment = new ListFoodFragment();
                listFoodFragment.setArguments(bundle);

                FragmentManager fragmentManager = ((FragmentActivity) itemView.getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, listFoodFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            });
            imgItemCategory = itemView.findViewById(R.id.img_item_category);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
        }

        public void onBind(int position) {
            Category category = listCategory.get(position);
            Glide.with(itemView)
                    .load(category.getImageUrl())
                    .into(imgItemCategory);
            tvItemTitle.setText(category.getTitle());
            dataUrl = category.getHref();
            title = category.getTitle();
            imgUrl = category.getImageUrl();
        }
    }
}
