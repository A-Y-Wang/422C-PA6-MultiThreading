package assignment6;

public class Ticket {

    public static final int TICKET_STRING_ROW_LENGTH = 31;

    private String boothId;
    private Seat seat;
    private int customer;

    public Ticket(String boothId, Seat seat, int customer) {
        this.boothId = boothId;
        this.seat = seat;
        this.customer = customer;
    }

    public String getBoothId() {
        return boothId;
    }

    public void setBoothId(String boothId) {
        this.boothId = boothId;
    }
    
    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        String result, dashLine, boothLine, seatLine, customerLine, eol;

        eol = System.getProperty("line.separator");

        dashLine = new String(new char[TICKET_STRING_ROW_LENGTH]).replace('\0', '-');

        boothLine = "| Ticket Booth ID: " + boothId;
        for (int i = boothLine.length(); i < TICKET_STRING_ROW_LENGTH - 1; ++i) {
            boothLine += " ";
        }
        boothLine += "|";

        seatLine = "| Seat: " + seat.toString();
        for (int i = seatLine.length(); i < TICKET_STRING_ROW_LENGTH - 1; ++i) {
            seatLine += " ";
        }
        seatLine += "|";

        customerLine = "| Customer: " + customer;
        for (int i = customerLine.length(); i < TICKET_STRING_ROW_LENGTH - 1; ++i) {
            customerLine += " ";
        }
        customerLine += "|";

        result = dashLine + eol + boothLine + eol + seatLine + eol + customerLine + eol
                + dashLine;

        return result;
    }
}
