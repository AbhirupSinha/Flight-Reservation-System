import java.io.*;
public class BookingManager 
{
    FlightReservationManager frsManager;
    BookingManager(FlightReservationManager frs)
    { 
        this.frsManager = frs; 
    }
    public void bookTicket(int selectedPair,String name) throws IOException 
    {
        String pnrNo = "DOSS";
        //NAME|PNR NO|DATE|SILK AIR|DATE|SPICE JET|RESERVED SEATS
        frsManager.dataManager.writeBookedData(name,pnrNo,
        this.frsManager.searchManager.silkDates.get(selectedPair),
        this.frsManager.searchManager.result[1][selectedPair].flightNum,
        this.frsManager.searchManager.spiceDates.get(selectedPair),
        this.frsManager.searchManager.result[0][selectedPair].flightNum,
        this.frsManager.searchManager.requiredSeats);
        this.frsManager.displayManager.printTicket();
    }
}
