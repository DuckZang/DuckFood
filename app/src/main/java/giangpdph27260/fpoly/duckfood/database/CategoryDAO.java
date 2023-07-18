package giangpdph27260.fpoly.duckfood.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import giangpdph27260.fpoly.duckfood.modal.Category;
@Dao

public interface CategoryDAO {
    @Query("SELECT * FROM category")
    List<Category> getListCategory();

    @Insert
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT COUNT(*) FROM category")
    int getCategoryCount();
}
