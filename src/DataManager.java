import java.io.*;
import java.util.*;
public class DataManager 
{
    FlightReservationManager frsManager;
    DataManager(FlightReservationManager frs)
    { 
        this.frsManager = frs; 
    }
    public ArrayList<Flight> readLineSpice() 
    {
        ArrayList<Flight> domestic_flights = new ArrayList<>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("2016.spicejet.csv"));
            for (int i = 1; i <= 5; i++) br.readLine(); // first 5 lines in the file are not ticket information
            String x = br.readLine();
            while (x != null) 
            {
                // format : ORIGIN|DESTINATION|FREQUENCY|FLIGHT NO|DEPARTURE|ARRIVAL|VIA|EFFECTIVE FROM|EFFECTIVE TILL|REMARKS
                StringTokenizer st = new StringTokenizer(x, "|");
                String depCity = (String)st.nextElement();
                String aCity=(String)st.nextElement();
                String days = (String)st.nextElement();
                StringTokenizer daysST = new StringTokenizer(days,", ");
                // parse the string which can be Monday, Tuesday, Daily etc
                ArrayList<String> availableDays = new ArrayList<>();
                while(daysST.hasMoreElements())
                {
                    availableDays.add((String)daysST.nextElement());
                }
                String name = (String)st.nextElement();
                String depTime = (String)st.nextElement();
                String arrTime = (String)st.nextElement();
                String via = (String)st.nextElement();
                String from = (String)st.nextElement();
                String till = (String)st.nextElement();
                Flight flight = new Flight(depCity, aCity, availableDays, name, depTime, arrTime, from, till);
                domestic_flights.add(flight);
                x = br.readLine();
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("FILE NOT FOUND!!!!");
            
        }
        catch(IOException e){}
        return domestic_flights; // returns arraylist of flights
    } 
    public void writeBookedData(String name,String pnrNo,String silkDate,String silkAir,String spiceDate,String spice,int seats)
    {
        File booked = new File("bookedSeats.csv");
        String newLine = name+"|"+pnrNo+"|"+spice+"|"+spiceDate+"|"+silkAir+"|"+silkDate+"|"+seats;
        try
        {
            if(!booked.isFile())
            {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(booked)));
                pw.println("NAME|PNR NO|SPICE JET|DATE|SILK AIR|DATE|RESERVED SEATS");
                pw.println(newLine);
                pw.close();
            }
            else
            {
                BufferedReader br =new BufferedReader(new FileReader("bookedSeats.csv"));
                File tempFile = new File("$bookedSeats.csv");
                PrintWriter pw1 = new PrintWriter(new BufferedWriter(new FileWriter(tempFile)));
                String temp = br.readLine();
                while(temp!=null)
                {
                    pw1.println(temp);
                    temp = br.readLine();
                }
                pw1.println(newLine);
                pw1.close();
                br.close();
                booked.delete();
                tempFile.renameTo(booked);
            }
        }
        catch(FileNotFoundException e){}
        catch(IOException e){}
    }
    public ArrayList<Flight> readLineSilk() 
    {
        //BufferedReader br = new BufferedReader(new FileReader("2016.silkair.csv"));
        ArrayList<Flight> intrFlights = new ArrayList<>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("2016.silkair.csv"));
            for (int i = 1; i <= 3; i++) br.readLine(); // first 3 lines in the file are not ticket information
            String s = br.readLine();
            // format: Sector|Days|Flight|Dep/Arr (Local Time)|Remark --> Sector is same as depCity
            while (s != null) 
            {
                StringTokenizer st = new StringTokenizer(s, "|");
                String depCity = (String)st.nextElement();
                String days = (String)st.nextElement();
                // parse the string which can be Monday, Tuesday, Daily etc
                StringTokenizer daysST = new StringTokenizer(days, ",");
                ArrayList<String> availableDays = new ArrayList<>();
                while (daysST.hasMoreElements()) 
                {
                    availableDays.add((String)daysST.nextElement());
                }
                String name = (String)st.nextElement();
                String time = (String)st.nextElement();
                // time is given in hrs/min order so to parse it we use StringTokenizer again
                StringTokenizer timeST = new StringTokenizer(time,"/");
                String depTime = (String)timeST.nextElement();
                String arrTime = (String)timeST.nextElement();
                String from = "OCT1";
                String till = "NOV13";
                int flag = 0;
                ArrayList<String> rem = new ArrayList<>();
                try 
                {
                    String remark = (String)st.nextElement();
                    StringTokenizer remarkST = new StringTokenizer(remark,", ");
                    while(remarkST.hasMoreElements())
                    {
                        rem.add((String)remarkST.nextElement());
                        flag++;
                    }
                    int j = 0;
                    String remark1 = rem.get(j);
                    if(remark1.substring(0,3).equalsIgnoreCase("Eff")) 
                    {
                        from = rem.get(j+1).substring(0,5);
                    }
                    else if(remark1.substring(0,3).equalsIgnoreCase("Dis")) 
                    {
                        till=rem.get(j+1).substring(0,5);
                    }
                    else if (remark1.substring(0,3).equalsIgnoreCase("Oct") && rem.get(j+1).equals("-")) 
                    {
                        from = remark1.substring(0, 5);
                        till = rem.get(j+2).substring(0, 5);
                    }
                    else if(remark1.substring(0,3).equalsIgnoreCase("Sep") && rem.get(j+1).equals("-"))
                    {
                        till=rem.get(j+2).substring(0, 5);
                    }
                }   
                catch(NoSuchElementException e){}
                catch(StringIndexOutOfBoundsException e){}    
                Flight flight = new Flight(depCity,availableDays,name,depTime,arrTime,from,till,rem); // rem excluding dat
                Flight flight1 = new Flight(depCity,availableDays,name,depTime,arrTime,from,till);
                if (flag > 2) intrFlights.add(flight);
                else intrFlights.add(flight1);
                s = br.readLine();
            }
            br.close();
        }
        catch(FileNotFoundException e){}
        catch(IOException e){}
        return intrFlights;
    }
    public boolean checkSeatsAvailability(String spice,String spDate,String silkAir,String skDate)
    {
        int silkReservedSeats=0;
        int spiceReservedSeats=0;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader("bookedSeats.csv"));
            String x= br.readLine();
            x=br.readLine();
            String spiceJet;
            String spiceDate,silkDate,silk;
            while (x != null) 
            {
                // format: NAME|Ticket NO|SPICE JET|DATE|SILK AIR|DATE|RESERVED SEATS
                StringTokenizer st = new StringTokenizer(x,"|");
                spiceJet=st.nextElement().toString();
                spiceJet=st.nextElement().toString();
                spiceJet=st.nextElement().toString(); // spice flight number
                spiceDate=st.nextElement().toString(); // spice date
                silk=st.nextElement().toString(); // silk flight number
                silkDate=st.nextElement().toString(); // silk date
                int seats = Integer.parseInt(st.nextElement().toString());
                if(spiceJet.equals(spice) && spDate.equals(spiceDate)) spiceReservedSeats+=seats;
                if(silk.equals(silkAir) && skDate.equals(silkDate)) silkReservedSeats+=seats;
                x=br.readLine();
            }
            br.close();
        }
        catch(FileNotFoundException e){}
        catch(IOException e){}
        if((spiceReservedSeats+this.frsManager.searchManager.requiredSeats)<=15 && (silkReservedSeats+this.frsManager.searchManager.requiredSeats)<=15)
        {
            return true;
        }
        else return false;
    } 
}

