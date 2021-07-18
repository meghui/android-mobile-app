package au.edu.utas.xhui.raffleportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    ListView myListRaffle;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

        myListRaffle = findViewById(R.id.myListRaffle);
        this.loadRaffles();
        myListRaffle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent raffleInfo=new Intent(au.edu.utas.xhui.raffleportal.MainActivity.this, RaffleInfoActivity.class);
                raffleInfo.putExtra("RAFFLE", getRaffles().get(i));
                startActivityForResult(raffleInfo, 0);
            }
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(MainActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        loadRaffles();
    }

    private void loadRaffles() {
        final ArrayList<Raffle> raffles = getRaffles();
        final RaffleAdapter raffleListAdapter = new RaffleAdapter(getApplicationContext(), R.layout.my_list_item_raffle, raffles);
        myListRaffle.setAdapter(raffleListAdapter);
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
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch(id){
            case R.id.mainActivity:
                Intent i1=new Intent(MainActivity.this,MainActivity.class);
                Log.d("ActivityNo", "1-List all raffles");
                startActivity(i1);
                break;
            case R.id.editTktInfoActivity:
                Intent i2=new Intent(MainActivity.this, EditTktInfoActivity.class);
                Log.d("ActivityNo", "2-Edit ticket info");
                startActivity(i2);
                break;
            case R.id.editRaffleActivity:
                Intent i3=new Intent(au.edu.utas.xhui.raffleportal.MainActivity.this, EditRaffleActivity.class);
                Log.d("ActivityNo", "3-Edit raffle settings");
                startActivity(i3);
                break;
            case R.id.createRaffleActivity:
                Intent i4=new Intent(au.edu.utas.xhui.raffleportal.MainActivity.this, CreateRaffleActivity.class);
                Log.d("ActivityNo", "4-Create a raffle ");
                startActivity(i4);
                break;
            case R.id.sellTktActivity:
                Intent i5=new Intent(au.edu.utas.xhui.raffleportal.MainActivity.this, SellTicketsActivity.class);
                Log.d("ActivityNo", "5-Sell tickets to a raffle");
                startActivity(i5);
                break;
            case R.id.drawRandomRaffleActivity:
                Intent i6=new Intent(MainActivity.this, DrawRaffleActivity.class);
                Log.d("ActivityNo", "6-Draw a Raffle");
                startActivity(i6);
                break;
            case R.id.listTktByRaffleActivity:
                Intent i7=new Intent(MainActivity.this, TicketsListActivity.class);
                Log.d("ActivityNo", "7-List all tickets by Raffle");
                startActivity(i7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_LONG).show();
        switch (position) {
            case 1:
                Intent i2=new Intent(MainActivity.this, EditTktInfoActivity.class);
                Log.d("Activity number", "2");
                startActivity(i2);
                break;
            case 2:
                Intent i3=new Intent(MainActivity.this, EditRaffleActivity.class);
                startActivity(i3);
                break;
            case 3:
                Intent i4=new Intent(MainActivity.this, CreateRaffleActivity.class);
                startActivity(i4);
                break;
            case 4:
                Intent i5=new Intent(MainActivity.this, SellTicketsActivity.class);
                startActivity(i5);
                break;
            case 5:
                Intent i6=new Intent(MainActivity.this, DrawRaffleActivity.class);
                startActivity(i6);
                break;
            case 6:
                Intent i7=new Intent(MainActivity.this, TicketsListActivity.class);
                startActivity(i7);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
