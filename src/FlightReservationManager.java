
import java.util.*;
public class FlightReservationManager 
{
    ArrayList<Flight> spicejetFlights;
    ArrayList<Flight> silkFlights;
    DataManager dataManager;
    DisplayManager displayManager;
    BookingManager bookingManager;
    SearchManager searchManager;
    public ArrayList<Flight> getSpicejetFlights() 
    { 
        return spicejetFlights; 
    }
    public ArrayList<Flight> getSilkFlights() 
    { 
        return silkFlights; 
    }
    public static void main(String[] args)  
    {
        FlightReservationManager frsManager = new FlightReservationManager();
        /*File booked = new File("bookedSeats.csv");
        if(!booked.isFile())
        {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(booked)));
            pw.println("NAME|PNR NO|SPICE JET|DATE|SILK AIR|DATE|RESERVED SEATS");
            pw.close();
        }*/
        frsManager.process();
    }
    public void restart() 
    {
        FlightReservationManager frsManager= new FlightReservationManager();
        frsManager.process();
    }  
    public void process() 
    {
        FlightReservationManager frsManager= new FlightReservationManager();
        frsManager.dataManager = new DataManager(frsManager);
        frsManager.spicejetFlights = frsManager.dataManager.readLineSpice();
        frsManager.silkFlights = frsManager.dataManager.readLineSilk();
        //if(spicejetFlights.isEmpty() || silkFlights.isEmpty()== true)
        frsManager.displayManager = new DisplayManager(frsManager);
        frsManager.displayManager.displayMain();
        frsManager.searchManager = new SearchManager(frsManager);
        frsManager.bookingManager = new BookingManager(frsManager);
    }
}