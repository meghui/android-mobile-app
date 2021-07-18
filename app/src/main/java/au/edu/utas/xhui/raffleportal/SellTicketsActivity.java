package au.edu.utas.xhui.raffleportal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class SellTicketsActivity extends AppCompatActivity{
    TextView raffleName;
    private static final int MAX_NUMBER_TICKETS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_tickets);
        final Raffle raffle = (Raffle)getIntent().getSerializableExtra("RAFFLE");
        if(raffle != null) {
            raffleName = findViewById(R.id.raffleName);
            raffleName.setText(raffle.getRaffleName());
        }
        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();

        Button btn = findViewById(R.id.buttonTkt);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int enteredTktNum = 0, enteredRaffleId = raffle.getRaffleID();
                String enteredPurchTime;
                String enteredCusName = "";
                String enteredCusAddress = "";

                EditText tktNum = findViewById(R.id.tktNum);
                enteredTktNum = Integer.parseInt(tktNum.getText().toString());
                EditText cusName = findViewById(R.id.cusName);
                enteredCusName = cusName.getText().toString();
                EditText cusAddress = findViewById(R.id.editTextPostalAddress);
                enteredCusAddress = cusAddress.getText().toString();
                String details_message = "Total Price: " + enteredTktNum*2 + "\nTicket Number:";
                enteredPurchTime = Calendar.getInstance().getTime().toString();
                int soldNum = TicketTable.selectByRaffleID(db, enteredRaffleId).size();
                if(soldNum >= MAX_NUMBER_TICKETS || soldNum + enteredTktNum > MAX_NUMBER_TICKETS) {
                    details_message = "Not enough tickets available!";
                } else {
                    for (int i=0; i < enteredTktNum; i++){
                        Ticket tkt = new Ticket();
                        tkt.setBuyerName(enteredCusName);
                        tkt.setAddress(enteredCusAddress);
                        tkt.setPurchTime(enteredPurchTime);
                        tkt.setRaffleId(enteredRaffleId);

                        Long id = TicketTable.insert(db, tkt);
                        details_message += "\n    " + id.toString();
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(SellTicketsActivity.this);
                builder.setTitle("Ticket(s) Info");
                final String summary = details_message.replaceAll("[\\n]", ", ") + ". Time: " + enteredPurchTime;
                final TextView details = new TextView(SellTicketsActivity.this);
                details.setText(details_message);
                details.setGravity(Gravity.CENTER);
                builder.setView(details);
                builder.setNegativeButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, summary);
                        sendIntent.setType("text/plain");

                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i1 = new Intent(SellTicketsActivity.this, MainActivity.class);
                        startActivity(i1);
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.mainActivity:
                Intent i1=new Intent(SellTicketsActivity.this, au.edu.utas.xhui.raffleportal.MainActivity.class);
                Log.d("ActivityNo", "1-List all Raffles");
                startActivity(i1);
                break;
            case R.id.editTktInfoActivity:
                Intent i2=new Intent(SellTicketsActivity.this, EditTktInfoActivity.class);
                Log.d("ActivityNo", "2-Edit ticket info");
                startActivity(i2);
                break;
            case R.id.editRaffleActivity:
                Intent i3=new Intent(SellTicketsActivity.this, EditRaffleActivity.class);
                Log.d("ActivityNo", "3-Edit raffle settings");
                startActivity(i3);
                break;
            case R.id.createRaffleActivity:
                Intent i4=new Intent(SellTicketsActivity.this, CreateRaffleActivity.class);
                Log.d("ActivityNo", "4-Create a raffle");
                startActivity(i4);
                break;
            case R.id.sellTktActivity:
                Intent i5=new Intent(SellTicketsActivity.this, SellTicketsActivity.class);
                Log.d("ActivityNo", "5-Sell tickets to a raffle");
                startActivity(i5);
                break;
            case R.id.drawRandomRaffleActivity:
                Intent i6=new Intent(SellTicketsActivity.this, DrawRaffleActivity.class);
                Log.d("ActivityNo", "6-Draw a Raffle");
                startActivity(i6);
                break;
            case R.id.listTktByRaffleActivity:
                Intent i7=new Intent(SellTicketsActivity.this, TicketsListActivity.class);
                Log.d("ActivityNo", "7-List tickets by Raffle");
                startActivity(i7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
