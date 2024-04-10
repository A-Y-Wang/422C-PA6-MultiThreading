package assignment6;

import assignment6.Seat.SeatType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MovieTheater {
//each new row in the movie theater has seats a-f

    private int printDelay;
    private SalesLogs log;
    private ArrayList<int[]> Rumble = new ArrayList<>();
    private ArrayList<int[]> Comfort = new ArrayList<>();
    private ArrayList<int[]> Standard = new ArrayList<>();
    int R = 0;
    int C = 0;
    int S = 0;
    Lock lock = new ReentrantLock();
    
    /**
     * Constructs a MovieTheater, where there are a set number of rows per seat type.
     *
     * @param rumbleNum the number of rows with rumble seats.
     * @param comfortNum the number of rows with comfort seats.
     * @param standardNum the number of rows with standard seats.
     */
    public MovieTheater(int rumbleNum, int comfortNum, int standardNum){
        printDelay = 10;
        log = new SalesLogs();
        // TODO: Finish implementing this constructor.
        //make a basic movie theater for now
        //this movie theater will be a placeholder for an actual place holder for real seats
        for(int i=1; i<= rumbleNum; i++){
            int[] rowee = generateArray();
            Rumble.add(rowee);
        }
        for(int i=1; i<= comfortNum; i++){
            int[] rowee = generateArray();
            Comfort.add(rowee);
        }
        for(int i=1; i<= standardNum; i++){
            int[] rowee = generateArray();
            Standard.add(rowee);
        }
    }

    public int[] generateArray(){
        int[] row = {6,5,4,3,2,1,0};
        return row;
    }

    /**
     * Returns the next available seat not yet reserved for a given seat type.
     *
     * @param seatType the type of seat (RUMBLE, COMFORT, STANDARD).
     * @return the next available seat or null if the theater is full.
     */
    public Seat getNextAvailableSeat(SeatType seatType) {
        // TODO: Implement this method.
        //given a seat type => return a Seat(SeatType, row num, seatLetter)
        lock.lock();
        try {
            int rowNum = 0;
            int seatLett;
            SeatType getSeat = seatType;
            if (getSeat == SeatType.RUMBLE && (R >= Rumble.size() || Rumble.isEmpty())) {
                getSeat = SeatType.COMFORT;
            }
            if (getSeat == SeatType.COMFORT && (C >= Comfort.size() || Comfort.isEmpty())) {
                getSeat = SeatType.STANDARD;
            }
            if (getSeat == SeatType.STANDARD && (S >= Standard.size() || Standard.isEmpty())) {
                return null;
            }

            if (getSeat == SeatType.RUMBLE) {
                rowNum = R+1;
                int availableSeat = Rumble.get(R)[0];
                seatLett = Rumble.get(R)[availableSeat];
                Rumble.get(R)[0]--;
                if (Rumble.get(R)[0] == 0) {
                    R++;
                }
            } else if (getSeat == SeatType.COMFORT) {
                rowNum = Rumble.size() + C + 1;
                int availableSeat = Comfort.get(C)[0];
                seatLett = Comfort.get(C)[availableSeat];
                Comfort.get(C)[0]--;
                if (Comfort.get(C)[0] == 0) {
                    C++;
                }
            } else {
                rowNum = Rumble.size() + Comfort.size() + S + 1;
                int availableSeat = Standard.get(S)[0];
                seatLett = Standard.get(S)[availableSeat];
                Standard.get(S)[0]--;
                if (Standard.get(S)[0] == 0) {
                    S++;
                }
            }
            if (seatLett == 0) {
                Seat seaty = new Seat(getSeat, rowNum, Seat.SeatLetter.A);
                log.addSeat(seaty);
                return seaty;
            } else if (seatLett == 1) {
                Seat seaty = new Seat(getSeat, rowNum, Seat.SeatLetter.B);
                log.addSeat(seaty);
                return seaty;
            } else if (seatLett == 2) {
                Seat seaty = new Seat(getSeat, rowNum, Seat.SeatLetter.C);
                log.addSeat(seaty);
                return seaty;
            } else if (seatLett == 3) {
                Seat seaty = new Seat(getSeat, rowNum, Seat.SeatLetter.D);
                log.addSeat(seaty);
                return seaty;
            } else if (seatLett == 4) {
                Seat seaty = new Seat(getSeat, rowNum, Seat.SeatLetter.E);
                log.addSeat(seaty);
                return seaty;
            } else if (seatLett == 5) {
                Seat seaty = new Seat(getSeat, rowNum, Seat.SeatLetter.F);
                log.addSeat(seaty);
                return seaty;
            }
        }
        finally{
            lock.unlock();
        }

        return null;
    }

    /**
     * Prints a ticket to the console for the customer after they reserve a seat.
     *
     * @param boothId id of the ticket booth.
     * @param seat a particular seat in the theater.
     * @return a movie ticket or null if a ticket booth failed to reserve the seat.
     */
    public Ticket printTicket(String boothId, Seat seat, int customer) {
        // TODO: Implement this method.
        Ticket tick = new Ticket(boothId, seat, customer);
        log.addTicket(tick);
        System.out.println(tick.toString()); //pretty sure you still need the toString method
        return tick; //why tho
    }
    
    /**
     * Lists all seats sold for the movie in the order of reservation.
     *
     * @return list of seats sold.
     */
    public List<Seat> getSeatLog() {
        // TODO: Implement this method.
        return log.seatLog;
    }

    /**
     * Lists all tickets sold for the movie in order of printing.
     *
     * @return list of tickets sold.
     */
    public List<Ticket> getTransactionLog() {
        // TODO: Implement this method.
        return log.ticketLog;
    }

    static class SalesLogs {

        private ArrayList<Seat> seatLog;
        private ArrayList<Ticket> ticketLog;

        private SalesLogs() {
            seatLog = new ArrayList<Seat>();
            ticketLog = new ArrayList<Ticket>();
        }

        public List<Seat> getSeatLog() {
            return (List<Seat>)(seatLog.clone());
        }

        public List<Ticket> getTicketLog() {
            return (List<Ticket>)(ticketLog.clone());
        }

        public void addSeat(Seat s) {
            seatLog.add(s);
        }

        public void addTicket(Ticket t) {
            ticketLog.add(t);
        }
    }
}
