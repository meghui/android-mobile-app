package au.edu.utas.xhui.raffleportal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class EditRaffleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_raffle);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();
        final ArrayList<Raffle> raffles = RaffleTable.selectAll(db);

        ListView myListRaffle = findViewById(R.id.myListRaffle);
        final RaffleAdapter raffleListAdapter = new RaffleAdapter(getApplicationContext(), R.layout.my_list_item_raffle, raffles);
        myListRaffle.setAdapter(raffleListAdapter);
        myListRaffle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Raffle raffle = raffles.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditRaffleActivity.this);
                builder.setTitle("Update Ticket Address");
                final EditText input = new EditText(EditRaffleActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(raffle.getRaffleName());
                builder.setView(input);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        raffle.setRaffleName(input.getText().toString());
                        RaffleTable.update(db, raffle);
                        raffleListAdapter.notifyDataSetChanged();
                    }
                });
                builder.create().show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.mainActivity:
                Intent i1=new Intent(EditRaffleActivity.this, au.edu.utas.xhui.raffleportal.MainActivity.class);
                startActivity(i1);
                break;
            case R.id.editTktInfoActivity:
                Intent i2=new Intent(EditRaffleActivity.this, EditTktInfoActivity.class);
                startActivity(i2);
                break;
            case R.id.editRaffleActivity:
                Intent i3=new Intent(EditRaffleActivity.this, EditRaffleActivity.class);
                startActivity(i3);
                break;
            case R.id.createRaffleActivity:
                Intent i4=new Intent(EditRaffleActivity.this, CreateRaffleActivity.class);
                startActivity(i4);
                break;
            case R.id.sellTktActivity:
                Intent i5=new Intent(EditRaffleActivity.this, SellTicketsActivity.class);
                startActivity(i5);
                break;
            case R.id.drawRandomRaffleActivity:
                Intent i6=new Intent(EditRaffleActivity.this, DrawRaffleActivity.class);
                startActivity(i6);
                break;
            case R.id.listTktByRaffleActivity:
                Intent i7=new Intent(EditRaffleActivity.this, TicketsListActivity.class);
                startActivity(i7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
