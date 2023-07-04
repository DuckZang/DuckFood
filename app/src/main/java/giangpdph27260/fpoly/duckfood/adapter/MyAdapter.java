package giangpdph27260.fpoly.duckfood.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import giangpdph27260.fpoly.duckfood.R;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Category> listCategory = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setListCategory(List<Category> categories) {
        this.listCategory = new ArrayList<>(categories);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_category, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgItemCategory;
        private final TextView tvItemTitle;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                //TODO xử lý click item
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
        }
    }
}
