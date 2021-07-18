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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TicketsListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView textViewRfId;
    Spinner spinnerRfName;
    ArrayList<String> arrayList_rfName;
    ArrayList<String> arrayList_rfID;
    ListView ticketsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_list);

        arrayList_rfName = new ArrayList<>();
        arrayList_rfID = new ArrayList<>();
        arrayList_rfName.add("All");
        arrayList_rfID.add("-1");

        final ArrayList<Raffle> raffles = getRaffles();
        for (int counter = 0; counter < raffles.size(); counter++) {
            Raffle raffle = raffles.get(counter);
            arrayList_rfName.add(raffle.getRaffleName());
            arrayList_rfID.add(String.valueOf(raffle.getRaffleID()));
        }

        textViewRfId = findViewById(R.id.RaffleID);
        ArrayAdapter<String> mySpinnerAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayList_rfName);
        spinnerRfName = findViewById(R.id.myRaffleSpinner);
        spinnerRfName.setAdapter(mySpinnerAdapter);
        spinnerRfName.setOnItemSelectedListener(this);
        int id = getIntent().getIntExtra("RAFFLE_ID", -1);
        ticketsList = findViewById(R.id.myList);
        setTicketsList(id);
        String selectValue = arrayList_rfName.get(arrayList_rfID.indexOf(String.valueOf(id)));
        int pos = mySpinnerAdapter.getPosition(selectValue);
        spinnerRfName.setSelection(pos);
    }

    private ArrayList<Raffle> getRaffles() {
        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();
        final ArrayList<Raffle> raffles = RaffleTable.selectAll(db);
        db.close();
        return raffles;
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
                Intent i1=new Intent(TicketsListActivity.this,MainActivity.class);
                startActivity(i1);
                break;
            case R.id.secondActivity:
                Intent i2=new Intent(TicketsListActivity.this, EditTktInfoActivity.class);
                startActivity(i2);
                break;
            case R.id.thirdActivity:
                Intent i3=new Intent(TicketsListActivity.this, EditRaffleActivity.class);
                startActivity(i3);
                break;
            case R.id.fourthActivity:
                Intent i4=new Intent(TicketsListActivity.this, CreateRaffleActivity.class);
                startActivity(i4);
                break;
            case R.id.fifthActivity:
                Intent i5=new Intent(TicketsListActivity.this, SellTicketsActivity.class);
                startActivity(i5);
                break;
            case R.id.sixthActivity:
                Intent i6=new Intent(TicketsListActivity.this, DrawRaffleActivity.class);
                startActivity(i6);
                break;
            case R.id.seventhActivity:
                Intent i7=new Intent(TicketsListActivity.this, TicketsListActivity.class);
                startActivity(i7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String s1 = arrayList_rfID.get(position);
        textViewRfId.setText(s1);
        int raffle_id = -1;
        try {
            raffle_id = Integer.parseInt(s1);
            setTicketsList(raffle_id);
        } catch (NumberFormatException e) {

        }
    }

    private void setTicketsList(int raffle_id){
        final ArrayList<Ticket> tickets = getTicketsByRaffleId(raffle_id);
        final TicketAdapter ticketListAdapter = new TicketAdapter(getApplicationContext(), R.layout.my_list_item, tickets);
        ticketsList.setAdapter(ticketListAdapter);
        ticketListAdapter.notifyDataSetChanged();
        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();
        ticketsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Ticket tkt = tickets.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(TicketsListActivity.this);
                builder.setTitle("Update Ticket Address");
                final EditText input = new EditText(TicketsListActivity.this);
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

    private ArrayList<Ticket> getTicketsByRaffleId(int raffleId) {
        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();

        ArrayList<Ticket> tickets =  TicketTable.selectByRaffleID(db, raffleId);
        db.close();
        return tickets;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
