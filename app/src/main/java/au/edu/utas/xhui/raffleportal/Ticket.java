package au.edu.utas.xhui.raffleportal;

import androidx.annotation.NonNull;

public class Ticket {
    private int mTicketID, mTicketNum, mRaffleId;
    private String mAddress, mBuyerName, mPurchTime;
    private int mPrice;

    public int getTicketID() {

        return mTicketID;
    }
    public void setTicketID(int s) {

        this.mTicketID = s;
    }

    public String getAddress(){

        return mAddress;
    }
    public void setAddress(String ss){

        this.mAddress = ss;
    }

    public String getBuyerName(){

        return mBuyerName;
    }
    public void setBuyerName(String sss){

        this.mBuyerName = sss;
    }


    public int getPrice(){

        return mPrice;
    }
    public void setPrice(int t){

        this.mPrice = t;
    }

    public int getTicketNum() {

        return mTicketNum;
    }
    public void setTicketNum(int tt) {

        this.mTicketNum = tt;
    }

    public String getPurchTime(){

        return mPurchTime;
    }
    public void setPurchTime(String ssss){

        this.mPurchTime = ssss;
    }

    public int getRaffleId(){
        return mRaffleId;
    }

    public void setRaffleId(int sssss){
        this.mRaffleId = sssss;
    }
    //to be modular and coder reusable
    @NonNull
    @Override
    public String toString()
    {
        return "Ticket ID "+getTicketID()+"; Ticket Num "+getTicketNum() + "; Tiket buyer: " + getBuyerName() + "; Raffle name: " + getRaffleId();
    }
}

