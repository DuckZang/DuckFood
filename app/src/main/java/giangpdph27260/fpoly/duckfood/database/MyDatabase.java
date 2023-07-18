package giangpdph27260.fpoly.duckfood.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import giangpdph27260.fpoly.duckfood.dao.CategoryDao;
import giangpdph27260.fpoly.duckfood.modal.Category;

@Database(entities = {Category.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "my-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CategoryDao categoryDao();
}
