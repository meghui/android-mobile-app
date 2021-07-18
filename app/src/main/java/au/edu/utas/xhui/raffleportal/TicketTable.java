package au.edu.utas.xhui.raffleportal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TicketTable {
    public static final String TABLE_NAME = "ticket";
    public static final String KEY_TICKET_ID = "ticket_id";
    public static final String KEY_TICKET_NUM = "ticket_num";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PRICE = "price";
    public static final String KEY_PURCHASE_TIME = "purchase_time";
    public static final String KEY_BUYER_NAME = "buyer_name";
    public static final String TABLE_NAME_FOREIGN = "raffle";
    public static final String KEY_RAFFLE_ID = "track_raffle_id";
    public static final String FOREIGN_KEY_RAFFLE_ID = "raffle_id";

    public static final String CREATE_STATEMENT = "CREATE TABLE "
            + TABLE_NAME
            + " (" + KEY_TICKET_ID + " integer primary key autoincrement, "
            + KEY_TICKET_NUM + " integer not null, "
            + KEY_PURCHASE_TIME + " string not null, "
            + KEY_ADDRESS + " string not null, "
            + KEY_PRICE + " integer not null, "
            + KEY_BUYER_NAME + " string not null, "
            + KEY_RAFFLE_ID + " integer not null, "
            + "FOREIGN KEY(" + KEY_RAFFLE_ID + ") REFERENCES " + TABLE_NAME_FOREIGN +"(" + FOREIGN_KEY_RAFFLE_ID + ")"
            +");";

    public static Ticket createFromCursor(Cursor c) {
        if (c == null || c.isAfterLast() || c.isBeforeFirst()) {
            return null;
        }
        else
        {
            Ticket tkt = new Ticket();
            tkt.setTicketID(c.getInt(c.getColumnIndex(KEY_TICKET_ID)));
            tkt.setTicketNum(c.getInt(c.getColumnIndex(KEY_TICKET_NUM)));
            tkt.setPurchTime(c.getString(c.getColumnIndex(KEY_PURCHASE_TIME)));
            tkt.setAddress(c.getString(c.getColumnIndex(KEY_ADDRESS)));
            tkt.setPrice(c.getInt(c.getColumnIndex(KEY_PRICE)));
            tkt.setBuyerName(c.getString(c.getColumnIndex(KEY_BUYER_NAME)));

            return tkt;
        }
    }

    public static Long insert(SQLiteDatabase db, Ticket tkt){
        ContentValues values = new ContentValues();
        values.put(KEY_TICKET_NUM, tkt.getTicketNum());
        values.put(KEY_PURCHASE_TIME, tkt.getPurchTime());
        values.put(KEY_ADDRESS, tkt.getAddress());
        values.put(KEY_PRICE, tkt.getPrice());
        values.put(KEY_BUYER_NAME, tkt.getBuyerName());
        values.put(KEY_RAFFLE_ID, tkt.getRaffleId());
        return db.insert(TABLE_NAME, null, values);
    }

    public static ArrayList<Ticket> selectAll(SQLiteDatabase db){
        ArrayList<Ticket> results = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (c != null)
        {
            c.moveToFirst();
            while (!c.isAfterLast())
            {
                Ticket tkt = createFromCursor(c);
                results.add(tkt);
                c.moveToNext();
            }
        }
        return results;
    }

    public static ArrayList<Ticket> selectByRaffleID(SQLiteDatabase db, int raffle_id){
        if(raffle_id == -1){
          return selectAll(db);
        }
        ArrayList<Ticket> results = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, KEY_RAFFLE_ID + "=?", new String[]{Integer.toString(raffle_id)}, null, null, null);
        if (c != null) {
            c.moveToFirst();
            while (!c.isAfterLast())
            {
                Ticket tkt = createFromCursor(c);
                results.add(tkt);
                c.moveToNext();
            }
        }
        return results;
    }
    public static void update(SQLiteDatabase db, Ticket tkt){
        ContentValues values = new ContentValues();
        values.put(KEY_TICKET_NUM, tkt.getTicketNum());
        values.put(KEY_ADDRESS, tkt.getAddress());
        values.put(KEY_PRICE, tkt.getPrice());
        values.put(KEY_BUYER_NAME, tkt.getBuyerName());
        values.put(KEY_RAFFLE_ID, tkt.getRaffleId());
        values.put(KEY_PURCHASE_TIME, tkt.getPurchTime());
        db.update(TABLE_NAME, values, KEY_TICKET_ID+"=?", new String[]{""+tkt.getTicketID()});
    }
}

