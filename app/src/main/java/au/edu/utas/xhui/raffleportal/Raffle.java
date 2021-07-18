package au.edu.utas.xhui.raffleportal;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Raffle implements Serializable {
    private int mRaffleID, mRaffleWinnerTicketId;
    private String mRaffleName, mRaffleDescription, mRaffleImg;

    public int getRaffleID() {
        return mRaffleID;
    }
    public void setRaffleID(int s) {

        this.mRaffleID = s;
    }

    public String getRaffleName(){

        return mRaffleName;
    }
    public void setRaffleName(String ss){

        this.mRaffleName = ss;
    }

    public String getRaffleDescription(){

        return mRaffleDescription;
    }
    public void setRaffleDescription(String sss){

        this.mRaffleDescription = sss;
    }

    public int getRaffleWinnerTicketId(){

        return mRaffleWinnerTicketId;
    }
    public void setRaffleWinnerTicketId(int ssss){

        this.mRaffleWinnerTicketId = ssss;
    }

    public String getRaffleImgPath(){

        return mRaffleImg;
    }
    public void setRaffleImgPath(String ssss){

        this.mRaffleImg = ssss;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "Raffle ID "+getRaffleID()+": "+getRaffleName() + getRaffleDescription();
    }
}

