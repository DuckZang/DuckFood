package giangpdph27260.fpoly.duckfood.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import giangpdph27260.fpoly.duckfood.modal.Category;

@Database(entities = {Category.class}, version = 1)
public abstract class CategoryDatabase extends RoomDatabase {
    private static CategoryDatabase instance;

    public static CategoryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CategoryDatabase.class, "my-database")
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract CategoryDAO categoryDao();
}
