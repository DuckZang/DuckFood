package giangpdph27260.fpoly.duckfood.modal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo (name = "title")
    private String title;
    @ColumnInfo (name = "imageUrl")
    private String imageUrl;
    @ColumnInfo (name = "linkListFood")
    private String linkListFood;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinkListFood() {
        return linkListFood;
    }

    public void setLinkListFood(String linkListFood) {
        this.linkListFood = linkListFood;
    }

    public Category() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }

    @NonNull
    @Override
    public String toString() {
        return "Category: {title: " + title + ", imageUrl: " + imageUrl + "}";
    }
}
