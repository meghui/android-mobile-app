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

public class EditTktInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tkt_info);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();

        final ArrayList<Ticket> tickets = TicketTable.selectAll(db);

        ListView myList = findViewById(R.id.myList);
        final TicketAdapter ticketListAdapter = new TicketAdapter(getApplicationContext(), R.layout.my_list_item, tickets);
        myList.setAdapter(ticketListAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Ticket tkt = tickets.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTktInfoActivity.this);
                builder.setTitle("Update Ticket Address");
                final EditText input = new EditText(EditTktInfoActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setText(tkt.getAddress());
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
                        tkt.setAddress(input.getText().toString());
                        TicketTable.update(db, tkt);
                        ticketListAdapter.notifyDataSetChanged();
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
                Intent i1=new Intent(EditTktInfoActivity.this,MainActivity.class);
                startActivity(i1);
                break;
            case R.id.editTktInfoActivity:
                Intent i2=new Intent(EditTktInfoActivity.this, EditTktInfoActivity.class);
                startActivity(i2);
                break;
            case R.id.editRaffleActivity:
                Intent i3=new Intent(EditTktInfoActivity.this, EditRaffleActivity.class);
                startActivity(i3);
                break;
            case R.id.createRaffleActivity:
                Intent i4=new Intent(EditTktInfoActivity.this, CreateRaffleActivity.class);
                startActivity(i4);
                break;
            case R.id.sellTktActivity:
                Intent i5=new Intent(EditTktInfoActivity.this, SellTicketsActivity.class);
                startActivity(i5);
                break;
            case R.id.drawRandomRaffleActivity:
                Intent i6=new Intent(EditTktInfoActivity.this, DrawRaffleActivity.class);
                startActivity(i6);
                break;
            case R.id.listTktByRaffleActivity:
                Intent i7=new Intent(EditTktInfoActivity.this, TicketsListActivity.class);
                startActivity(i7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
