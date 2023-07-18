package giangpdph27260.fpoly.duckfood.modal;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;
    @ColumnInfo(name = "linkListFood")
    private String linkListFood;

    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkListFood() {
        return linkListFood;
    }

    public void setLinkListFood(String linkListFood) {
        this.linkListFood = linkListFood;
    }
}
