package au.edu.utas.xhui.raffleportal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class DrawRaffleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView rfName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_raffle);

        final Raffle raffle = (Raffle)getIntent().getSerializableExtra("RAFFLE");
        if(raffle != null) {
            rfName = findViewById(R.id.rafflenamedraw);
            rfName.setText(raffle.getRaffleName());
        }
        Button btnSingleWinner = findViewById(R.id.button2);
        btnSingleWinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawSingleWinner(v);
            }
        });
    }

    public void drawSingleWinner(View view) {
        ArrayList<Ticket> tickets = getTicketsByRaffleId(getIntent().getIntExtra("RAFFLE_ID", -1));
        Random rand = new Random();
        Ticket ticket = tickets.get(rand.nextInt(tickets.size()));
        int winnerTktNum = ticket.getTicketID();

        String details_message = "    The Winner Number is : " + winnerTktNum;

        AlertDialog.Builder builder = new AlertDialog.Builder(DrawRaffleActivity.this);
        builder.setTitle("Winner's Ticket Info");
        final TextView details = new TextView(DrawRaffleActivity.this);
        details.setText(details_message);
        builder.setView(details);
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i1 = new Intent(DrawRaffleActivity.this, MainActivity.class);
                startActivity(i1);
            }
        });
        builder.create().show();

        final Raffle raffle = (Raffle)getIntent().getSerializableExtra("RAFFLE");
        if(raffle != null) {
            raffle.setRaffleName(rfName.getText().toString());
            raffle.setRaffleDescription(raffle.getRaffleDescription());
            raffle.setRaffleWinnerTicketId(winnerTktNum);
            Database databaseConnection = new Database(this);
            final SQLiteDatabase db = databaseConnection.open();
            RaffleTable.update(db, raffle);
            db.close();
        }
    }

    public void drawThreeWinner(View view) {
        //TODO
    }

    private ArrayList<Ticket> getTicketsByRaffleId(int raffleId) {
        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();

        ArrayList<Ticket> tickets =  TicketTable.selectByRaffleID(db, raffleId);
        db.close();
        return tickets;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //TODO low
        int id = item.getItemId();
        switch(id){
            case R.id.mainActivity:
                Intent i1=new Intent(DrawRaffleActivity.this,MainActivity.class);
                startActivity(i1);
                break;
            case R.id.editTktInfoActivity:
                Intent i2=new Intent(DrawRaffleActivity.this, EditTktInfoActivity.class);
                startActivity(i2);
                break;
            case R.id.editRaffleActivity:
                Intent i3=new Intent(DrawRaffleActivity.this, EditRaffleActivity.class);
                startActivity(i3);
                break;
            case R.id.createRaffleActivity:
                Intent i4=new Intent(DrawRaffleActivity.this, CreateRaffleActivity.class);
                startActivity(i4);
                break;
            case R.id.sellTktActivity:
                Intent i5=new Intent(DrawRaffleActivity.this, SellTicketsActivity.class);
                startActivity(i5);
                break;
            case R.id.drawRandomRaffleActivity:
                Intent i6=new Intent(DrawRaffleActivity.this, DrawRaffleActivity.class);
                startActivity(i6);
                break;
            case R.id.listTktByRaffleActivity:
                Intent i7=new Intent(DrawRaffleActivity.this, TicketsListActivity.class);
                startActivity(i7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
