package au.edu.utas.xhui.raffleportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateRaffleActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_raffle);

        Button btn = findViewById(R.id.button);

        Database databaseConnection = new Database(this);
        final SQLiteDatabase db = databaseConnection.open();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredRafName = "";
                String enteredRafDescription = "";
                EditText raffleName = findViewById(R.id.raffleName);
                enteredRafName = raffleName.getText().toString();
                EditText raffleDescription = findViewById(R.id.editText);
                enteredRafDescription = raffleDescription.getText().toString();

                Raffle raffle = new Raffle();
                raffle.setRaffleName(enteredRafName);
                raffle.setRaffleDescription(enteredRafDescription);
                RaffleTable.insert(db, raffle);

                Intent i1 = new Intent(CreateRaffleActivity.this,MainActivity.class);
                startActivity(i1);
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
                Intent i1=new Intent(CreateRaffleActivity.this, au.edu.utas.xhui.raffleportal.MainActivity.class);
                startActivity(i1);
                break;
            case R.id.editTktInfoActivity:
                Intent i2=new Intent(CreateRaffleActivity.this, EditTktInfoActivity.class);
                startActivity(i2);
                break;
            case R.id.editRaffleActivity:
                Intent i3=new Intent(CreateRaffleActivity.this, EditRaffleActivity.class);
                startActivity(i3);
                break;
            case R.id.createRaffleActivity:
                Intent i4=new Intent(CreateRaffleActivity.this, CreateRaffleActivity.class);
                startActivity(i4);
                break;
            case R.id.sellTktActivity:
                Intent i5=new Intent(CreateRaffleActivity.this, SellTicketsActivity.class);
                startActivity(i5);
                break;
            case R.id.drawRandomRaffleActivity:
                Intent i6=new Intent(CreateRaffleActivity.this, DrawRaffleActivity.class);
                startActivity(i6);
                break;
            case R.id.listTktByRaffleActivity:
                Intent i7=new Intent(CreateRaffleActivity.this, TicketsListActivity.class);
                startActivity(i7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
