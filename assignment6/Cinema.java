package assignment6;

import assignment6.Seat.SeatType;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Cinema {
//
    /**
     * Constructor to initilize the simulation based on starter parameters. 
     * 
     * @param booths maps ticket booth id to seat type preferences of customers in line.
     * @param movieTheater the theater for which tickets are sold.
     */
    MovieTheater AMC;
    Map < String , SeatType [] > ticketBooths;
    public Cinema(Map<String, SeatType[]> booths, MovieTheater movieTheater) {
        // TODO: Implement this constructor
        AMC = movieTheater;
        ticketBooths = booths;
    }
    /**
     * Starts the ticket office simulation by creating (and starting) threads
     * for each ticket booth to sell tickets for the given movie.
     *
     * @return list of threads used in the simulation,
     *   should have as many threads as there are ticket booths.
     */
    public List<Thread> simulate() throws InterruptedException {
        // TODO: Implement this method.
        ArrayBlockingQueue<TicketHold> ticketQueue = new ArrayBlockingQueue<>(100);
        //lets keep 100 for now just so that the get next available seat wont wait for the queue to empty up.
        AtomicInteger customerNum = new AtomicInteger();
        List<Thread> threadies = new ArrayList<>();
        int drugs = ticketBooths.size();
        for(String key: ticketBooths.keySet()){
            Thread t1 = new Thread(()->{
                SeatType[] seatTypes = ticketBooths.get(key);
                for (SeatType sit : seatTypes){
                        Seat sitYoAss = AMC.getNextAvailableSeat(sit);
                        if(sitYoAss!=null){
                            TicketHold myTicket = new TicketHold(key,sitYoAss,customerNum.getAndIncrement());//customerNum.getAndIncrement()
                            //Ticket thisTick = new Ticket(key, sitYoAss, customerNum.getAndIncrement());
                            //System.out.println(thisTick.toString());
                            try {
                                ticketQueue.put(myTicket);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                }
                TicketHold poison = new TicketHold("poison pill", null, -1);
                try {
                    ticketQueue.put(poison);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threadies.add(t1);
            t1.start();
        }

        Thread t2 = new Thread(()->{
            //while isnotempty or all the poisonpills havent been found
            //also implement thread.sleep to see the tickets printing out
            int pills = 0;
            int testCount = 0;
            while (!ticketQueue.isEmpty() || pills != drugs) {
                try {
                    TicketHold processTicket = ticketQueue.take();
                    if (processTicket.getBoothID() != "poison pill" && processTicket.getNumCustomer() != -1){
                        AMC.printTicket(processTicket.getBoothID(), processTicket.getSeat(), processTicket.getNumCustomer());
                        testCount++;
                    }
                    else {pills++;}
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                    throw new RuntimeException(e);
                }
            }
            System.out.println(testCount);
        });
        threadies.add(t2);
        t2.start();

        for (Thread thread : threadies) {
            thread.join();
        }

        System.out.println("all threads have finished");
        return null;
    }


    public static void main(String[] args) throws InterruptedException {
        // For your testing purposes. We will not call this method.
        Map < String , SeatType [] > booths = new HashMap< String , SeatType [] >();
        booths.put ( " TO1 " , new SeatType [] { SeatType . COMFORT , SeatType . COMFORT , SeatType . COMFORT });
        booths.put ( " TO3 " , new SeatType [] { SeatType . COMFORT , SeatType . STANDARD , SeatType . STANDARD });
        booths.put ( " TO2 " , new SeatType [] { SeatType . RUMBLE , SeatType . COMFORT , SeatType . STANDARD , SeatType . STANDARD });
        booths.put ( " TO5 " , new SeatType [] { SeatType . COMFORT , SeatType . COMFORT , SeatType . COMFORT });
        booths.put ( " TO4 " , new SeatType [] { SeatType . STANDARD , SeatType . STANDARD , SeatType . STANDARD });

        Cinema client = new Cinema ( booths , new MovieTheater (20 , 25 , 20));
        client.simulate ();

    }
}
