package giangpdph27260.fpoly.duckfood.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import giangpdph27260.fpoly.duckfood.modal.Category;

@Database(entities = {Category.class},version = 1)
public abstract class CategoryDb extends RoomDatabase {

    public abstract CategoryDao categoryDao();
}
