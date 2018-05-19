import java.io.*;
import java.util.*;
public class SearchManager 
{  
    FlightReservationManager frsManager;
    Flight result[][]; // due to silk & spice
    int noOfPairs;
    String searchDate;
    ArrayList<String> silkDates;
    ArrayList<String> spiceDates;
    ArrayList<Integer> totalTimeDuration;
    int requiredSeats;
    SearchManager(FlightReservationManager frsm)
    {
        this.frsManager = frsm;
        this.silkDates = new ArrayList<>();
        this.spiceDates = new ArrayList<>();
        this.totalTimeDuration = new ArrayList<>();
        
    }
    void SearchFlights(String source,int seats,String date,int d,int m,int y,int day) throws IOException,IndexOutOfBoundsException
    {
       this.requiredSeats = seats;
       String str[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
       String week[]={"DAILY","SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY"};      
       this.result=new Flight[2][20];
       this.searchDate = d+"/"+m+"/"+y;
       String nextDate = (d+1)+"/"+m+"/"+y;
       int posOutput=0; // to keep track of noofRows in output[][]     
       for(int i=0;i<this.frsManager.getSpicejetFlights().size();i++)
        {
            if(this.frsManager.getSpicejetFlights().get(i).depCity.substring(0, 4).equalsIgnoreCase(source.substring(0,4)))
            {
                String fdate=this.frsManager.getSpicejetFlights().get(i).effFrom;
                int m1=0;
                int dat1=(Integer.parseInt(fdate.charAt(0)+"")*10 + Integer.parseInt(fdate.charAt(1)+""));
                String mon=(fdate.substring(3,6));
                for(int p=0;p<12;p++){
                    if(mon.equals(str[p]))
                      m1=p+1;  
                }
                int yr1=(Integer.parseInt(fdate.charAt(7)+"")*10 + Integer.parseInt(fdate.charAt(8)+""));
                
                String tdate=this.frsManager.getSpicejetFlights().get(i).effTill;
                int m2=0;
                int dat2=(Integer.parseInt(tdate.charAt(0)+"")*10 + Integer.parseInt(tdate.charAt(1)+""));
                mon=(tdate.substring(3,6));
                for(int p=0;p<12;p++){
                    if(mon.equals(str[p]))
                      m2=p+1;  
                }
                int yr2=(Integer.parseInt(tdate.charAt(7)+"")*10 + Integer.parseInt(tdate.charAt(8)+""));
                int f=0;
                y=y%100;
                
               if(y>=yr1 && y<=yr2)
                {
                    if(y==yr1 && y<yr2)
                    {
                        
                        if(m>m1)
                            f=1;
                        
                        else if(m==m1){
                        if(d>=dat1)
                            f=1;
                    }
                    }
                    else if(y==yr1 && y==yr2)
                    {
                        if(m>m1 && m<m2)
                            f=1;
                        else if(m>m1 && m==m2)
                        {
                            if(d<=dat2)
                                f=1;
                        }
                        else if(m==m1 && m<m2)
                        {
                            if(d>=dat1)
                                f=1;
                        }
                        else if(m==m1 && m==m2)
                        {
                            if(d>=dat1 && d<=dat2)
                                f=1;
                        }
                        }
                    
                    
                }
               
                if(f==1)
                {
                    
                    int f1=0;
                    String actualday=week[day];
                    for(int j=0;j<this.frsManager.getSpicejetFlights().get(i).daysOfWeek.size();j++){
                    String weekday=this.frsManager.getSpicejetFlights().get(i).daysOfWeek.get(j);
                    if(weekday.equals(actualday) || weekday.equals("DAILY"))
                    {
                        f1=1;
                        break;
                    }
                    }
                    if(f1==1)
                    {
                                
                            String actualDay,eDay;
                            String enTime=this.frsManager.getSpicejetFlights().get(i).arrTime;
                            String stTime=this.frsManager.getSpicejetFlights().get(i).depTime;
                            int diffSilk,totTime,startTime,lag,diffSpice,minTime,maxTime;
                            int startHour=(Integer.parseInt(stTime.charAt(0)+"")*10+Integer.parseInt(stTime.charAt(1)+""));
                            int startMin=(Integer.parseInt(stTime.charAt(3)+"")*10+Integer.parseInt(stTime.charAt(4)+""));
                           
                            if(stTime.charAt(6)=='A')
                            {
                                startTime=startHour*60+startMin;
                            }
                            else
                                startTime=((startHour*60)+startMin)+720;

                            int endHour=(Integer.parseInt(enTime.charAt(0)+"")*10+Integer.parseInt(enTime.charAt(1)+""));
                            int endMin=(Integer.parseInt(enTime.charAt(3)+"")*10+Integer.parseInt(enTime.charAt(4)+""));

                            if(enTime.charAt(6)=='A') // am-pm
                            {
                                

                                totTime=endHour*60+endMin;
                            }
                            else
                                totTime=((endHour*60)+endMin)+720;
                            
                            minTime=totTime+119; // 2 hours gap
                            maxTime=totTime+360; // 6 hours gap
                            int k=0;
                            for(k=0;k<16;k++){  // 16 columns in csv file
                                String plus="+";
                                if(this.frsManager.getSpicejetFlights().get(i).arrCity.substring(0,4).equalsIgnoreCase(this.frsManager.getSilkFlights().get(k).depCity.substring(0, 4))){
                                    
                                
                                    //Added code for disc. and eff.
                           
                                String frdate=this.frsManager.getSilkFlights().get(k).effFrom;
                                int m3=0;
                                int dat3,dat4;
                                if(frdate.length()==4) // finding date
                                    dat3=(Integer.parseInt(frdate.charAt(3)+""));
                                else
                                    dat3=(Integer.parseInt(frdate.charAt(3)+"")*10 + Integer.parseInt(frdate.charAt(4)+"")); // for 2 digit date
                String mon2=(frdate.substring(0,3));
                for(int p=0;p<12;p++){
                    if(mon2.equals(str[p]))
                      m3=p+1;  
                }
                int yr3=16;
                String todate=this.frsManager.getSilkFlights().get(k).effTill;
                int m4=0;
              if(todate.length()==4)
dat4=(Integer.parseInt(todate.charAt(3)+""));
else
                dat4=(Integer.parseInt(todate.charAt(3)+"")*10 + Integer.parseInt(todate.charAt(4)+""));
   mon2=(todate.substring(0,3));
                for(int p=0;p<12;p++){
                    if(mon2.equals(str[p]))
                      m4=p+1;  
                }
                int yr4=16;
int g=0;
       
                    if(m==m3 && m<m4){
                        if(d>=dat3)
                            g=1;
                    }
                    else if(m==m4 && m>m3){
                        if(d<=dat4)
                            g=1;
                    }
                    else if(m==m3 && m==m4){
                        if(d>=dat3 && d<=dat4)
                            g=1;
                    }
                    else if(m>m3 && m<m4){
                        g=1;
                    }
                    //code for excluded date
                     String exdate;
                     try{
                                    for(int a=3;a<this.frsManager.getSilkFlights().get(k).remarks.size();a++)
                            { // remarks excluding dates
                                exdate=this.frsManager.getSilkFlights().get(k).remarks.get(a).substring(0,5);
                                
                                int date1;
                                String month1;
                                int month2=0;
                                date1=(Integer.parseInt(exdate.charAt(3)+"")*10 + Integer.parseInt(exdate.charAt(4)+""));
                                month1=(exdate.substring(0,3));
                                for(int p=0;p<12;p++){
                    if(month1.equals(str[p]))
                      month2=p+1;  }
                    if(month2==m && d==date1)
                        g=0; // excluded
                }
                              
                            }catch(NullPointerException e){
                            }catch(IndexOutOfBoundsException e){
                                    
                                    }
                     //code ends for excluded date
//Code ends for disc. and eff
if(this.frsManager.getSilkFlights().get(k).flightNum.equalsIgnoreCase("MI 471") && d==13 && m==11)
    g=0;

    if(g==1)
            {
                                    int silkStartTime = (Integer.parseInt(this.frsManager.getSilkFlights().get(k).depTime));
                                    silkStartTime = (silkStartTime/100)*60+(silkStartTime%100);
                                    int silkEndTime;
                                    if(this.frsManager.getSilkFlights().get(k).arrTime.contains(plus))
                                    {
                                        silkEndTime=(Integer.parseInt(this.frsManager.getSilkFlights().get(k).arrTime.substring(0,4)));
                                        silkEndTime=(silkEndTime/100)*60+(silkEndTime%100);
                                    }
                                    else
                                    {
                                        silkEndTime=(Integer.parseInt(this.frsManager.getSilkFlights().get(k).arrTime));
                                        silkEndTime=(silkEndTime/100)*60+(silkEndTime%100);
                                    }
                                    if(maxTime<1440 && silkStartTime>minTime && silkStartTime<maxTime) {
                                        actualDay=week[day];
                                        for(int j=0;j<this.frsManager.getSilkFlights().get(k).daysOfWeek.size();j++)
                                        {
                                            String weekday=this.frsManager.getSilkFlights().get(k).daysOfWeek.get(j);
                                            if(weekday.equalsIgnoreCase(actualDay.substring(0,3))){
                                                //insert the pair
                                                if(this.frsManager.dataManager.checkSeatsAvailability(this.frsManager.getSpicejetFlights().get(i).flightNum,
                                                        searchDate, this.frsManager.getSilkFlights().get(k).flightNum, searchDate)){
                                                    result[0][posOutput]=this.frsManager.getSpicejetFlights().get(i);
                                                    result[1][posOutput++]=this.frsManager.getSilkFlights().get(k);
                                                    if(startTime>totTime)
                                                    {
                                                        diffSpice=1440-startTime+totTime;
                                                    }
                                                    else
                                                    {
                                                        diffSpice=totTime-startTime;
                                                    }
                                                    //lag=totTime-Integer.parseInt(result[1][posOutput-1].depTime);
                                                    lag=totTime-silkStartTime;
                                                    if(result[1][posOutput-1].arrTime.contains(plus))
                                                        {
                                                            diffSilk=silkEndTime+1440-silkStartTime;
                                                        }
                                                        else
                                                        diffSilk=silkEndTime-silkStartTime;
                                                    result[0][posOutput-1].journeyTime=diffSpice;
                                                    result[1][posOutput-1].journeyTime=diffSilk;
                                                    totalTimeDuration.add(diffSpice+diffSilk+lag);
                                                    this.spiceDates.add(searchDate);
                                                    this.silkDates.add(searchDate);
                                                }
                                            }

                                        }
                                    }
                                    
                                    if(minTime<1440 && maxTime>1440){
                                        actualDay=week[day];
                                        eDay=week[((day+1)%8==0?1:(day+1)%8)];
                                        for(int j=0;j<this.frsManager.getSilkFlights().get(k).daysOfWeek.size();j++)
                                        {
                                           
                                            String weekday=this.frsManager.getSilkFlights().get(k).daysOfWeek.get(j);
                                            if(silkStartTime<1440 && silkStartTime>minTime)
                                            {
                                                if(weekday.equalsIgnoreCase(actualDay.substring(0,3))){
                                                    //insert pair
                                                    if(this.frsManager.dataManager.checkSeatsAvailability(this.frsManager.getSpicejetFlights().get(i).flightNum,
                                                        searchDate, this.frsManager.getSilkFlights().get(k).flightNum, searchDate)){
                                                        
                                                        result[0][posOutput]=this.frsManager.getSpicejetFlights().get(i);
                                                        result[1][posOutput++]=this.frsManager.getSilkFlights().get(k);
                                                        if(startTime>totTime)
                                                        {
                                                            diffSpice=1440-startTime+totTime;
                                                        }
                                                        else
                                                        {
                                                            diffSpice=totTime-startTime;
                                                        }
                                                        //lag=totTime-Integer.parseInt(result[1][posOutput-1].depTime);
                                                        lag=totTime-silkStartTime;
                                                        if(result[1][posOutput-1].arrTime.contains(plus))
                                                        {
                                                            diffSilk=silkEndTime+1440-silkStartTime;
                                                        }
                                                        else
                                                        diffSilk=silkEndTime-silkStartTime;
                                                        result[0][posOutput-1].journeyTime=diffSpice;
                                                        result[1][posOutput-1].journeyTime=diffSilk;
                                                        totalTimeDuration.add(diffSpice+diffSilk+lag);
                                                        this.spiceDates.add(searchDate);
                                                        this.silkDates.add(searchDate);
                                                    }
                                                }
                                            }
                                            if(silkStartTime>0 && silkStartTime<maxTime%1440) {
                                                if(weekday.equalsIgnoreCase(eDay.substring(0,3))){
                                                    //insert pair
                                                    if(this.frsManager.dataManager.checkSeatsAvailability(this.frsManager.getSpicejetFlights().get(i).flightNum,
                                                        searchDate, this.frsManager.getSilkFlights().get(k).flightNum, nextDate)){
                                                        result[0][posOutput]=this.frsManager.getSpicejetFlights().get(i);
                                                        result[1][posOutput++]=this.frsManager.getSilkFlights().get(k);
                                                        if(startTime>totTime)
                                                        {
                                                            diffSpice=1440-startTime+totTime;
                                                        }
                                                        else
                                                        {
                                                            diffSpice=totTime-startTime;
                                                        }
                                                        //lag=totTime-Integer.parseInt(result[1][posOutput-1].depTime);
                                                        lag=totTime-silkStartTime;
                                                        if(result[1][posOutput-1].arrTime.contains(plus))
                                                        {
                                                            diffSilk=silkEndTime+1440-silkStartTime;
                                                        }
                                                        else
                                                        diffSilk=silkEndTime-silkStartTime;
                                                        result[0][posOutput-1].journeyTime=diffSpice;
                                                        result[1][posOutput-1].journeyTime=diffSilk;
                                                        totalTimeDuration.add(diffSpice+diffSilk+lag);
                                                        this.spiceDates.add(searchDate);
                                                        this.silkDates.add(nextDate);
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    if(d!=13 && m!=11)
                                    {
                                    if(minTime>1440 && silkStartTime>minTime%1440 && silkStartTime<maxTime%1440){
                                        eDay=week[((day+1)%8==0?1:(day+1)%8)];
                                        for(int j=0;j<this.frsManager.getSilkFlights().get(k).daysOfWeek.size();j++)
                                        {
                                            String weekday=this.frsManager.getSilkFlights().get(k).daysOfWeek.get(j);
                                        
                                            if(weekday.equalsIgnoreCase(eDay.substring(0,3))){
                                                //insert pair
                                        
                                                if(this.frsManager.dataManager.checkSeatsAvailability(this.frsManager.getSpicejetFlights().get(i).flightNum,
                                                        searchDate, this.frsManager.getSilkFlights().get(k).flightNum, nextDate)){
                                                    result[0][posOutput]=this.frsManager.getSpicejetFlights().get(i);
                                                    result[1][posOutput++]=this.frsManager.getSilkFlights().get(k);
                                                    if(startTime>totTime)
                                                        {
                                                            diffSpice=1440-startTime+totTime;
                                                        }
                                                        else
                                                        {
                                                            diffSpice=totTime-startTime;
                                                        }
                                                        //lag=totTime-Integer.parseInt(result[1][posOutput-1].depTime);
                                                        lag=totTime-silkStartTime;    
                                                        if(result[1][posOutput-1].arrTime.contains(plus))
                                                        {
                                                            diffSilk=silkEndTime+1440-silkStartTime;
                                                        }
                                                        else
                                                        diffSilk=silkEndTime-silkStartTime;
                                                        result[0][posOutput-1].journeyTime=diffSpice;
                                                        result[1][posOutput-1].journeyTime=diffSilk;
                                                        totalTimeDuration.add(diffSpice+diffSilk+lag);
                                                    this.spiceDates.add(searchDate);
                                                    this.silkDates.add(nextDate);
                                                }
                                            }
                                        }

                                    }
                                    }
                                    if(stTime.charAt(6)=='P'&& enTime.charAt(6)=='A' && silkStartTime<maxTime && silkStartTime>minTime)
                                    {
                                        
                                        eDay=week[((day+1)%8==0?1:(day+1)%8)];
                                        for(int j=0;j<this.frsManager.getSilkFlights().get(k).daysOfWeek.size();j++)
                                        {
                                            String weekday=this.frsManager.getSilkFlights().get(k).daysOfWeek.get(j);
                                            if(weekday.equalsIgnoreCase(eDay.substring(0,3))){
                                                //insert pair
                                                if(this.frsManager.dataManager.checkSeatsAvailability(this.frsManager.getSpicejetFlights().get(i).flightNum,
                                                        searchDate, this.frsManager.getSilkFlights().get(k).flightNum, nextDate)){
                                                    result[0][posOutput]=this.frsManager.getSpicejetFlights().get(i);
                                                    result[1][posOutput++]=this.frsManager.getSilkFlights().get(k);
                                                    if(startTime>totTime)
                                                        {
                                                            diffSpice=1440-startTime+totTime;
                                                        }
                                                        else
                                                        {
                                                            diffSpice=totTime-startTime;
                                                        }
                                                        //lag=totTime-Integer.parseInt(result[1][posOutput-1].depTime);
                                                        lag=totTime-silkStartTime;
                                                        if(result[1][posOutput-1].arrTime.contains(plus))
                                                        {
                                                            diffSilk=silkEndTime+1440-silkStartTime;
                                                        }
                                                        else
                                                        diffSilk=silkEndTime-silkStartTime;
                                                        
                                                    result[0][posOutput-1].journeyTime=diffSpice;
                                                    result[1][posOutput-1].journeyTime=diffSilk;
                                                    totalTimeDuration.add(diffSpice+diffSilk+lag);
                                                    this.spiceDates.add(searchDate);
                                                    this.silkDates.add(nextDate);
                                                }
                                            }
                                        }
                                    }
                                }
                                }
                                }
                                

                            }
                        }
                    }
                    
                }
            this.noOfPairs = posOutput;
    }
   
    void SortFlights()
    {
        int i,j;
        String tempSilkDate,tempSpiceDate;
        Flight tempDomFlight,tempIntFlight;
        for(i=0;i<noOfPairs-1;i++)
        {
            for(j=0;j<noOfPairs-i-1;j++)
            {
                //if(this.totalTimeDuration.get(j)>this.totalTimeDuration.get(j+1))
                if((result[0][j].journeyTime+result[1][j].journeyTime)>(result[0][j+1].journeyTime+result[1][j+1].journeyTime))
                {
                    tempDomFlight=result[0][j];
                    tempIntFlight=result[1][j];
                    result[0][j]=result[0][j+1];
                    result[1][j]=result[1][j+1];
                    result[0][j+1]=tempDomFlight;
                    result[1][j+1]=tempIntFlight;
                    tempSilkDate=silkDates.get(j);
                    silkDates.set(j,silkDates.get(j+1));
                    silkDates.set(j+1,tempSilkDate);
                    tempSpiceDate=spiceDates.get(j);
                    spiceDates.set(j,spiceDates.get(j+1));
                    spiceDates.set(j+1,tempSpiceDate);
                    
                }
                    
            }
        }
    }
}
    
