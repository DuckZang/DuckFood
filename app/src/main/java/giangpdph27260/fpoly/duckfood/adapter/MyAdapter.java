package giangpdph27260.fpoly.duckfood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import giangpdph27260.fpoly.duckfood.R;
import giangpdph27260.fpoly.duckfood.modal.Category;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private final ArrayList<Category> listCategory;

    public MyAdapter(ArrayList<Category> list) {
        this.listCategory = list;
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

        Category category = listCategory.get(position);

        Glide.with(holder.itemView)
                .load(category.getImageUrl())
                .into(holder.imgItemCategory);
        holder.tvItemTitle.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgItemCategory;
        private final TextView tvItemTitle;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemCategory = itemView.findViewById(R.id.img_item_category);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);

        }
    }
}
