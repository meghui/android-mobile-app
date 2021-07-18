package au.edu.utas.xhui.raffleportal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database
{
    private static final String TAG = "TicketDatabase";
    private static final String DATABASE_NAME = "TicketDatabase";
    private static final int   DATABASE_VERSION   = 24;
    private SQLiteDatabase mDb;
    private DatabaseHelper mDbHelper;
    private final Context mCtx;

    public Database(Context ctx) { this.mCtx = ctx; }

    public SQLiteDatabase open()
    {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return mDb;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(TicketTable.CREATE_STATEMENT);
            db.execSQL(RaffleTable.CREATE_STATEMENT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TicketTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + RaffleTable.TABLE_NAME);
            onCreate(db);
        }
    }
}

