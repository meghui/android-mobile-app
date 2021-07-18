package au.edu.utas.xhui.raffleportal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RaffleInfoActivity extends AppCompatActivity {
    private static final int SELECTED_PIC = 100;
    EditText editNameView;
    EditText editDescriptionView;
    TextView winnerIdView;
    ImageView imageView;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rafflemenu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        Raffle raffle = (Raffle)getIntent().getSerializableExtra("RAFFLE");
        assert raffle != null;
        boolean toggleItem = raffle.getRaffleWinnerTicketId() <= 0;
        menu.getItem(0).setEnabled(true);
        menu.getItem(4).setEnabled(toggleItem);
        menu.getItem(5).setEnabled(toggleItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Raffle raffle = (Raffle)getIntent().getSerializableExtra("RAFFLE");
        switch(id){
            case R.id.fifthActivity:
                Intent i5=new Intent(this, SellTicketsActivity.class);
                i5.putExtra("RAFFLE", raffle);
                startActivity(i5);
                break;
            case R.id.sixthActivity:
                Intent i6=new Intent(this, DrawRaffleActivity.class);
                i6.putExtra("RAFFLE", raffle);
                startActivity(i6);
                break;
            case R.id.seventhActivity:
                Intent i7=new Intent(this, TicketsListActivity.class);
                if(raffle != null) {
                    i7.putExtra("RAFFLE_ID", raffle.getRaffleID());
                }
                startActivity(i7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raffle_info);
        imageView = findViewById(R.id.imageRaffle);
        Raffle raffle = (Raffle)getIntent().getSerializableExtra("RAFFLE");
        if(raffle != null) {
            editNameView = findViewById(R.id.editName);
            editDescriptionView = findViewById(R.id.editDescription);
            editNameView.setText(raffle.getRaffleName());
            editDescriptionView.setText(raffle.getRaffleDescription());
            winnerIdView = findViewById(R.id.textViewWinner);
            int id = raffle.getRaffleWinnerTicketId();
            if(id != 0) {
                winnerIdView.setText(" Winner ticket number: " + id);
            }
            String imgPath = raffle.getRaffleImgPath();
            if(imgPath != null && imgPath.length() > 0) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                if(bitmap != null){
                    Drawable drawable = new BitmapDrawable(bitmap);
                    imageView.setBackground(drawable);
                }
            }
        }
    }

    public void saveRaffle(View view){
        final Raffle raffle = (Raffle)getIntent().getSerializableExtra("RAFFLE");
        if(raffle != null) {
            raffle.setRaffleName(editNameView.getText().toString());
            raffle.setRaffleDescription(editDescriptionView.getText().toString());

            Database databaseConnection = new Database(this);
            final SQLiteDatabase db = databaseConnection.open();
            RaffleTable.update(db, raffle);
        }
        finish();
    }

    @SuppressLint("SetTextI18n")
    public void deleteRaffle(View view){
        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();
        Object raf = getIntent().getSerializableExtra("RAFFLE");
        if(raf != null) {
            Raffle raffle = (Raffle)raf;
            ArrayList<Ticket> tickets = TicketTable.selectByRaffleID(db, raffle.getRaffleID());
            if(tickets.size() == 0) {
                RaffleTable.delete(db, raffle);
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RaffleInfoActivity.this);
                builder.setTitle(" Warning! ");
                final TextView warning = new TextView(RaffleInfoActivity.this);
                warning.setText("This Raffle cannot be deleted!");
                warning.setTextSize(15);
                warning.setGravity(Gravity.CENTER);
                builder.setView(warning);
                builder.setCancelable(false);
                builder.setPositiveButton("OK", null);
                builder.create().show();
            }
        }
    }

    public void imgClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PIC);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECTED_PIC:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    imageView.setBackground(drawable);
                    final Raffle raffle = (Raffle)getIntent().getSerializableExtra("RAFFLE");
                    if(raffle != null) {
                        Database databaseConnection = new Database(this);
                        final SQLiteDatabase db = databaseConnection.open();
                        raffle.setRaffleImgPath(filepath);
                        RaffleTable.update(db, raffle);
                        db.close();
                    }
                }
                break;
            default:
                break;
        }
    }
}
