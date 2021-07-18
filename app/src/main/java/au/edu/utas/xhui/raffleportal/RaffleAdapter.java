package au.edu.utas.xhui.raffleportal;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;

public class RaffleAdapter extends ArrayAdapter<Raffle> {
    private int mLayoutResourceID;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(mLayoutResourceID, parent, false);
        Raffle rf = this.getItem(position);

        String imgPath = rf.getRaffleImgPath();
        if(imgPath != null && imgPath.length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            if(bitmap != null){
                Drawable drawable = new BitmapDrawable(bitmap);
                ImageView imageView = row.findViewById(R.id.iconRaffle);
                imageView.setBackground(drawable);
            }
        }
        TextView lblRaffleName = row.findViewById(R.id.RaffleName);
        lblRaffleName.setText(rf.getRaffleName());
        TextView lblRaffleDescription = row.findViewById(R.id.RaffleDescription);
        lblRaffleDescription.setText(rf.getRaffleDescription());
        return row;
    }

    public RaffleAdapter(Context context, int resource, List<Raffle> objects) {
        super(context, resource, objects);
        this.mLayoutResourceID = resource;
    }
}

