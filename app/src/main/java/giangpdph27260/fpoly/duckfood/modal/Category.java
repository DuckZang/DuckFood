package giangpdph27260.fpoly.duckfood.modal;

import androidx.annotation.NonNull;

public class Category {
    private String title;
    private String imageUrl;

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
