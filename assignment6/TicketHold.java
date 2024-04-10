package assignment6;

public class TicketHold {
    private String BoothID;
    private Seat sitdown;
    private int numCustomer;

    public TicketHold(String IDEE, Seat getLow, int coostomerNum){
        BoothID = IDEE;
        sitdown = getLow;
        numCustomer = coostomerNum;
    }
    public String getBoothID(){
        return this.BoothID;
    }
    public Seat getSeat(){
        return this.sitdown;
    }
    public int getNumCustomer(){
        return this.numCustomer;
    }
}
