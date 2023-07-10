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
import giangpdph27260.fpoly.duckfood.modal.Food;

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.MyViewHolder> {
    private ArrayList<Food> listFood = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setListFood(List<Food> foods) {
        this.listFood = new ArrayList<>(foods);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListFoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_food, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFoodAdapter.MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgItemFood;
        private final TextView tvItemTitleFood;
        private final TextView tvItemDecriptionFood;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setOnClickListener(view -> {
//
//
//            });
            imgItemFood = (ImageView) itemView.findViewById(R.id.img_item_food);
            tvItemTitleFood = (TextView) itemView.findViewById(R.id.tv_item_title_food);
            tvItemDecriptionFood = (TextView) itemView.findViewById(R.id.tv_item_decription_food);
        }

        public void onBind(int position) {
            Food food = listFood.get(position);
            Glide.with(itemView)
                    .load(food.getFoodImgUrl())
                    .into(imgItemFood);
            tvItemDecriptionFood.setText(food.getFoodDescription());
            tvItemTitleFood.setText(food.getFoodTitle());
        }
    }
}
