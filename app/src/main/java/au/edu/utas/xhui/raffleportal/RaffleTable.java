package au.edu.utas.xhui.raffleportal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class RaffleTable {
    public static final String TABLE_NAME = "raffle";
    public static final String KEY_RAFFLE_ID = "raffle_id";
    public static final String KEY_RAFFLE_NAME = "raffle_name";
    public static final String KEY_RAFFLE_DESCRIPTION = "raffle_description";
    public static final String KEY_WINNER_TICKET_NUMBER = "raffle_winner_ticket_number";
    public static final String KEY_IMG_PATH = "raffle_image";
    public static final String CREATE_STATEMENT = "CREATE TABLE "
            + TABLE_NAME
            + " (" + KEY_RAFFLE_ID + " integer primary key autoincrement, "
            + KEY_RAFFLE_NAME + " string not null, "
            + KEY_RAFFLE_DESCRIPTION + " string not null, "
            + KEY_WINNER_TICKET_NUMBER + " integer not null,"
            + KEY_IMG_PATH + " string"
            +");";

    public static Raffle createFromCursor(Cursor c) {

        if (c == null || c.isAfterLast() || c.isBeforeFirst()) {
            return null;
        }
        else
        {
            Raffle rf = new Raffle();
            rf.setRaffleID(c.getInt(c.getColumnIndex(KEY_RAFFLE_ID)));
            rf.setRaffleName(c.getString(c.getColumnIndex(KEY_RAFFLE_NAME)));
            rf.setRaffleDescription(c.getString(c.getColumnIndex(KEY_RAFFLE_DESCRIPTION)));
            rf.setRaffleWinnerTicketId(c.getInt(c.getColumnIndex(KEY_WINNER_TICKET_NUMBER)));
            rf.setRaffleImgPath(c.getString(c.getColumnIndex(KEY_IMG_PATH)));
            return rf;
        }
    }

    public static void insert(SQLiteDatabase db, Raffle rf){
        ContentValues values = new ContentValues();
        values.put(KEY_RAFFLE_NAME, rf.getRaffleName());
        values.put(KEY_RAFFLE_DESCRIPTION, rf.getRaffleDescription());
        values.put(KEY_WINNER_TICKET_NUMBER, rf.getRaffleWinnerTicketId());
        if(rf.getRaffleImgPath() != null) {
            values.put(KEY_IMG_PATH, rf.getRaffleImgPath());
        }
        db.insert(TABLE_NAME, null, values);
    }

    //Prepare a results ArrayList, and return it empty for now
    public static ArrayList<Raffle> selectAll(SQLiteDatabase db){
        ArrayList<Raffle> results = new ArrayList<>();
        //The nulls used are for parameters where we may want to filter, group, or order the items in the table that we aren’t using.
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        //check for error
        if (c != null) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Raffle rf = createFromCursor(c);
                results.add(rf);
                c.moveToNext();
            }
        }
        return results;
    }

    public static void update(SQLiteDatabase db, Raffle rf){
        ContentValues values = new ContentValues();
        values.put(KEY_RAFFLE_NAME, rf.getRaffleName());
        values.put(KEY_RAFFLE_DESCRIPTION, rf.getRaffleDescription());
        values.put(KEY_WINNER_TICKET_NUMBER, rf.getRaffleWinnerTicketId());
        if(rf.getRaffleImgPath()  != null) {
            values.put(KEY_IMG_PATH, rf.getRaffleImgPath());
        }
        db.update(TABLE_NAME, values, KEY_RAFFLE_ID+"=?", new String[]{""+rf.getRaffleID()});
    }

    public static void delete(SQLiteDatabase db, Raffle rf){
        db.delete(TABLE_NAME,KEY_RAFFLE_ID+"=?", new String[]{""+rf.getRaffleID()});
    }

    public static ArrayList<Raffle> selectByRaffleID(SQLiteDatabase db, int raffle_id){
        if(raffle_id == -1){
            return selectAll(db);
        }
        ArrayList<Raffle> results = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, KEY_RAFFLE_ID + "=?", new String[]{Integer.toString(raffle_id)}, null, null, null);
        if (c != null)
        {
            c.moveToFirst();
            while (!c.isAfterLast())
            {
                Raffle rf = createFromCursor(c);
                results.add(rf);
                c.moveToNext();
            }
        }
        return results;
    }
}

