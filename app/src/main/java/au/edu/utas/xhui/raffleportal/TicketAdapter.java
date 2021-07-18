package au.edu.utas.xhui.raffleportal;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TicketAdapter extends ArrayAdapter<Ticket> {
    private int mLayoutResourceID;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(mLayoutResourceID, parent, false);
        Ticket tkt = this.getItem(position);
        TextView lblAddress = row.findViewById(R.id.lblAddress);
        lblAddress.setText(tkt.getAddress());
        TextView lblTicketNum = row.findViewById(R.id.lblTicketNum);
        lblTicketNum.setText("TktNum:" + tkt.getTicketID());
        TextView lblBuyerName = row.findViewById(R.id.lblBuyerName);
        lblBuyerName.setText(tkt.getBuyerName() );
        TextView lblTicketTime = row.findViewById(R.id.lblTicketTime);
        lblTicketTime.setText(tkt.getPurchTime());
        return row;
    }

    public TicketAdapter(Context context, int resource, List<Ticket> objects) {
        super(context, resource, objects);
        this.mLayoutResourceID = resource;
    }
}

