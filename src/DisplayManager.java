import java.io.*;
import java.util.*;
public class DisplayManager 
{
    InputScreen screen1;
    Booking screen2;
    Confirmation ticket;
    SearchScreen resultScreen;
    FlightReservationManager frsManager;
    
    public DisplayManager(FlightReservationManager frsm)
    { 
        this.frsManager = frsm; 
    }
    void displayMain(){ showScreen1(); }
    
    public void showScreen1(){
        this.screen1 = new InputScreen(this);
        this.screen1.setVisible(true);
    }
    String source;
    int day,seats,year,month,dat;
    public void showScreen2() throws IOException
    {
        this.resultScreen=new SearchScreen(this);
        source=this.screen1.getjComboBox1().getSelectedItem().toString();
        seats=Integer.parseInt(this.screen1.getjSpinner1().getValue().toString());
        year=this.screen1.getDateChooserCombo1().getCurrent().get(1);
        month=this.screen1.getDateChooserCombo1().getCurrent().get(2);
        dat=this.screen1.getDateChooserCombo1().getCurrent().get(5);
        day=this.screen1.getDateChooserCombo1().getCurrent().get(7);
        String str[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        String date = dat+" "+str[month]+" "+year;
        this.frsManager.searchManager.SearchFlights(source, seats, date, dat, month+1, year, day);
        this.frsManager.searchManager.SortFlights();
        this.resultScreen.showResult(this.frsManager.searchManager.result,this.frsManager.searchManager.noOfPairs);
        this.resultScreen.setVisible(true);
    }
    int selectedPair;
    
    public void bookFlight(int selectedPair) {
        this.selectedPair=selectedPair;
        this.resultScreen.setVisible(false);
        this.screen2 = new Booking(this);
        this.screen2.setVisible(true);
    }
    
    String name;
    
    public void confirmedFlight() throws IOException {
        String name = this.screen2.getjTextField1().getText();
        this.frsManager.bookingManager.bookTicket(selectedPair, name);
    }
    
    public void printTicket() {
        this.ticket= new Confirmation(this);
        String data;
        String tData=this.resultScreen.ticketData.get(this.resultScreen.getIndex());
        StringTokenizer st = new StringTokenizer(tData,"|");
        data=st.nextElement().toString();
        this.ticket.getjLabel21().setText(data);
        data=st.nextElement().toString();
        this.ticket.getjLabel24().setText(data);
        data=st.nextElement().toString();
        this.ticket.getjLabel11().setText(data);
        data=st.nextElement().toString();
        data=st.nextElement().toString();
        this.ticket.getjLabel6().setText(data);
        data=st.nextElement().toString();
        data=st.nextElement().toString();
        this.ticket.getjLabel12().setText(data);
        data=st.nextElement().toString();
        this.ticket.getjLabel13().setText(data);
        data=st.nextElement().toString();
        this.ticket.getjLabel8().setText(data);
        data=st.nextElement().toString();
        this.ticket.getjLabel10().setText(data);
        this.ticket.getjLabel2().setText(this.frsManager.searchManager.result[0][this.resultScreen.getIndex()].depCity.substring(0, 3));
        this.ticket.getjLabel3().setText(this.frsManager.searchManager.result[0][this.resultScreen.getIndex()].arrCity.substring(0, 3));
        this.ticket.getjLabel5().setText("Departs "+this.frsManager.searchManager.result[0][this.resultScreen.getIndex()].depCity);
        this.ticket.getjLabel7().setText("Departs "+this.frsManager.searchManager.result[0][this.resultScreen.getIndex()].arrCity);
        this.ticket.getjLabel18().setText(this.screen2.getjTextField1().getText());
        this.ticket.getjLabel20().setText(this.screen1.getjSpinner1().getValue().toString());
        Random rn = new Random(); // ticket-number
        String rand= rn.nextInt()+"";
        this.ticket.getjLabel19().setText(this.screen2.getjTextField1().getText().charAt(0)+rand.substring(0,7));
        this.ticket.setVisible(true);
        this.screen2.setVisible(false);
    }
}
