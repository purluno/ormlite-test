package purluno.ormlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseOpenHelper extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    public DatabaseOpenHelper(Context context) {
        super(context, "database", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Person.class);
        } catch (SQLException e) {
            Log.w("DatabaseOpenHelper", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Person.class, true);
            TableUtils.createTable(connectionSource, Person.class);
        } catch (SQLException e) {
            Log.w("DatabaseOpenHelper", e);
        }
    }
}
