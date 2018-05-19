import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class SearchScreen extends JFrame implements ActionListener{
    DisplayManager displayManager;
    JRadioButton[] selection ;
    JButton bookButton ;
    ArrayList <String> ticketData;    
    SearchScreen(DisplayManager dm){
        this.displayManager = dm;
       
    }
    public void showResult(Flight[][] result,int noOfPairs) {
        ticketData = new ArrayList<>();
        Container mainContentPane = this.getContentPane();
        this.setSize(1200, 600);
        JPanel resultsPane = new JPanel();
        resultsPane.setLayout(new FlowLayout(FlowLayout.RIGHT,20,20));
        resultsPane.setBackground(new Color(51,153,255));
        JPanel results2Pane = new JPanel();
        results2Pane.setLayout(new FlowLayout(FlowLayout.RIGHT,20,20));
        results2Pane.setBackground(new Color(0,255,204));
        
        JPanel pairPanes[] = new JPanel[noOfPairs];
        ButtonGroup radioButton = new ButtonGroup();
        this.selection= new JRadioButton[noOfPairs];
        for(int i=0;i<noOfPairs;i++){
            pairPanes[i] = new JPanel();
            pairPanes[i].setLayout(new GridLayout(5,4,3,1));
            selection[i]=new JRadioButton();
        }
        
        JPanel labelPane= new JPanel();
        labelPane.setLayout(new GridLayout(2,1,3,20));
        JLabel departure=new JLabel("                 Flight Info                                              "
                + "     Departure Time                                          "
                + "     Arrival Time                                                "
                + "    Travel Time                                                                                   ");
        departure.setForeground(Color.yellow);
        departure.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel header= new JLabel("Search Results                ");
        header.setFont(new Font(Font.SANS_SERIF,Font.BOLD,28));
        header.setForeground(Color.yellow);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        labelPane.add(header);
        labelPane.add(departure);
        labelPane.setBackground(new Color(0,255,204));
        resultsPane.add(labelPane);
        String domFlightNum,intrFlightNum,domArrTime,intrArrTime,domDeptTime,intrDeptTime,domDeptDate,domArrDate,intrArrDate,intrDeptDate;
        String domDepCity,domArrCity,tData;
        int domJourneyTime,intrJourneyTime,timeGap;
        int i;
        for(i=0;i<noOfPairs;i++) {
            domFlightNum=result[0][i].flightNum;
            intrFlightNum=result[1][i].flightNum;
            domArrTime=result[0][i].arrTime;
            intrArrTime=result[1][i].arrTime;
            domDeptTime=result[0][i].depTime;
            intrDeptTime=result[1][i].depTime;
            domDepCity = result[0][i].depCity;
            domArrCity = result[0][i].arrCity;
            domJourneyTime=displayManager.frsManager.searchManager.result[0][i].journeyTime;
            intrJourneyTime=displayManager.frsManager.searchManager.result[1][i].journeyTime;
            String str2="+";
            int time;
            if(domDeptTime.charAt(6)=='P'&&domArrTime.charAt(6)=='A') // am - pm
            {
                domArrDate=this.displayManager.frsManager.searchManager.spiceDates.get(i);
                StringTokenizer st = new StringTokenizer(domArrDate,"/");
                domArrDate=((Integer.parseInt(st.nextElement()+""))+1)+"/"+st.nextElement()+"/"+st.nextElement();
            }
            else
            {
                domArrDate=this.displayManager.frsManager.searchManager.spiceDates.get(i);
            }
            if(Integer.parseInt(intrDeptTime)>Integer.parseInt(intrArrTime.substring(0,4)))
            {
                
                intrArrDate=this.displayManager.frsManager.searchManager.silkDates.get(i);
                StringTokenizer st = new StringTokenizer(intrArrDate,"/");
                intrArrDate=((Integer.parseInt(st.nextElement()+""))+1)+"/"+st.nextElement()+"/"+st.nextElement();
            }
            else {
                intrArrDate=this.displayManager.frsManager.searchManager.silkDates.get(i);
            }
            if(domArrTime.charAt(6)=='P')
            {
                timeGap=(((Integer.parseInt(intrDeptTime))/100)*60 +(Integer.parseInt(intrDeptTime))%100)-((Integer.parseInt(domArrTime.substring(0,2))*60) + (Integer.parseInt(domArrTime.substring(3,5)))+720);
            }
            else
            {
                 timeGap=((Integer.parseInt(intrDeptTime))/100)*60 +(Integer.parseInt(intrDeptTime))%100-(Integer.parseInt(domArrTime.substring(0,2))*60 + Integer.parseInt(domArrTime.substring(3,5)));
            }
            if(timeGap<0) timeGap=1440+timeGap;
            if(intrArrTime.contains(str2)){
                StringTokenizer st= new StringTokenizer(intrArrTime,"+");
                time =Integer.parseInt(st.nextElement()+"");
            }
            else{
            time =Integer.parseInt(intrArrTime);}
            
            if (time > 1200) {
                time=time-1200;
                if((time%100)/10==0)
                intrArrTime=(time/100)+":"+"0"+time%100+" PM";
                else
                    intrArrTime=(time/100)+":"+time%100+" PM";
                
            }
            else {
                if((time%100)/10==0)
                intrArrTime=(time/100)+":"+"0"+time%100+" AM";
               else
                intrArrTime=(time/100)+":"+time%100+" AM";
            }
            
            time =Integer.parseInt(intrDeptTime);
            if(time>1200) {
                time=time-1200;
                if((time%100)/10==0)
            intrDeptTime=(time/100)+":"+"0"+time%100+" PM";    
                    else
                intrDeptTime=(time/100)+":"+time%100+" PM";
            }
            else {
                if((time%100)/10==0)
            intrDeptTime=(time/100)+":"+"0"+time%100+" AM";    
                    else
                
                intrDeptTime=(time/100)+":"+time%100+" AM";
            }
            
            JLabel flight1Num = new JLabel(domFlightNum);            
            JLabel flight2Num = new JLabel(intrFlightNum);
            JLabel flight1DepTime = new JLabel(domDeptTime + " (IST)");
            JLabel flight1ArrTime = new JLabel(domArrTime+ " (IST)");
            JLabel flight1DepDate = new JLabel(this.displayManager.frsManager.searchManager.spiceDates.get(i));
            JLabel flight1ArrDate = new JLabel(domArrDate);
            JLabel flight1TotalTime = new JLabel(domJourneyTime/60+"h "+domJourneyTime%60+" min");
            JLabel flight2DepTime = new JLabel(intrDeptTime+ " (IST)");
            JLabel flight2ArrTime = new JLabel(intrArrTime + " (SGT)");
            JLabel flight2DepDate = new JLabel(this.displayManager.frsManager.searchManager.silkDates.get(i));
            JLabel flight2ArrDate = new JLabel(intrArrDate);
            JLabel flight2TotalTime = new JLabel((intrJourneyTime-150)/60+"h "+(intrJourneyTime-150)%60+" min");
            JLabel flight1Via = new JLabel(result[0][i].depCity.substring(0,3)+" to "+result[0][i].arrCity.substring(0,3));
            JLabel flight2Via = new JLabel(result[0][i].arrCity.substring(0,3)+" to SIN");
            JLabel totalVia = new JLabel("Connection in "+result[0][i].arrCity.substring(0,3)+" after "+timeGap/60+"h "+timeGap%60+" min");
            //SPICE | SILK | DTIMESPICE | ATIMESPICE | SPICEDEPTDATE | SPICEARRDATE | SILKDEPTIME | SILKARRTIME | SILKDDATE | SILKARDATE
            tData= domFlightNum+"|"+intrFlightNum+"|"+domDeptTime+"|"+domArrTime+"|"+
                    this.displayManager.frsManager.searchManager.spiceDates.get(i)+"|"+
                    domArrDate+"|"+intrDeptTime+"|"+intrArrTime+"|"+
                    this.displayManager.frsManager.searchManager.silkDates.get(i)+"|"+
                    intrArrDate+"|";
            this.ticketData.add(tData);
            
            
            pairPanes[i].add(flight1Num);
            pairPanes[i].add(flight1DepTime);
            pairPanes[i].add(flight1ArrTime);
            pairPanes[i].add(new JLabel("Total Time"));
            pairPanes[i].add(flight1Via);
            pairPanes[i].add(flight1DepDate);
            pairPanes[i].add(flight1ArrDate);
            pairPanes[i].add(flight1TotalTime);
            pairPanes[i].add(flight2Via);
            pairPanes[i].add(totalVia);
            pairPanes[i].add(new JLabel(""));
            pairPanes[i].add(new JLabel(""));
            pairPanes[i].add(new JLabel(""));
            pairPanes[i].add(flight2Num);
            pairPanes[i].add(flight2DepTime);
            pairPanes[i].add(flight2ArrTime);
            pairPanes[i].add(new JLabel("Total Time"));
            pairPanes[i].add(flight2Via);
            pairPanes[i].add(flight2DepDate);
            pairPanes[i].add(flight2ArrDate);
            
            pairPanes[i].add(flight2TotalTime);
            selection[i].setText("SELECT");
            selection[i].setActionCommand(i+"");
            selection[i].addActionListener(this);
            JPanel forBackgroundColor = new JPanel();
            forBackgroundColor.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
            forBackgroundColor.add(pairPanes[i]);
            forBackgroundColor.add(selection[i]);
            selection[i].setBackground(new Color(0,255,204));
            pairPanes[i].setBackground(new Color(0,255,204));
            forBackgroundColor.setBackground(new Color(0,255,204));
            resultsPane.add(forBackgroundColor);
        }
        
bookButton = new JButton("BOOK");
        bookButton.addActionListener(this);
        bookButton.setEnabled(false); // disabled in start
        results2Pane.add(bookButton);
resultsPane.add(results2Pane); 
                resultsPane.setLayout(new GridLayout(20,2,1,1));
        
        
            //Scrollbar
            JScrollPane jscrollpane1=new JScrollPane(resultsPane);
        jscrollpane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jscrollpane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainContentPane.add(jscrollpane1);
        
//scroll bar ends       
        
    }
    int index;
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        try{
            this.index=Integer.parseInt(s);
            for(int j = 0; j<this.displayManager.frsManager.searchManager.noOfPairs;j++){
                if(j!=this.index){
                    selection[j].setSelected(false);
                }
            }
            this.bookButton.setEnabled(true);
        }
        catch(NumberFormatException ex){
            this.displayManager.bookFlight(this.index);
        }
    }

    public ArrayList<String> getTicketData() {
        return ticketData;
    }

    
    public int getIndex() {
        return index;
    }
}