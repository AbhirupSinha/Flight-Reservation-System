import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class Flight {
    String depCity;
    String arrCity;
    ArrayList<String> daysOfWeek;
    ArrayList<String> remarks;
    String flightNum;
    String depTime;
    String arrTime;
    String effFrom;
    String effTill;
    int journeyTime;
    
    Flight(String d,String a,ArrayList<String> dw,String fn,String dt,String at,String from,String till){
        depCity = d;
        arrCity = a;
        daysOfWeek = dw;
        flightNum = fn;
        depTime = dt;
        arrTime = at;
        effFrom=from;
        effTill=till;
    }
    Flight(String d,ArrayList<String> dw,String fn,String dt,String at){
        depCity = d;
        daysOfWeek = dw;
        flightNum = fn;
        depTime = dt;
        arrTime = at;
    }

    Flight(String d, ArrayList<String> dw, String fn, String dt, String at, String from, String till) {
        depCity = d;
        daysOfWeek = dw;
        flightNum = fn;
        depTime = dt;
        arrTime = at;
        effFrom=from;
        effTill=till;
    }
      Flight(String d, ArrayList<String> dw, String fn, String dt, String at, String from, String till,ArrayList<String> rem) {
        depCity = d;
        daysOfWeek = dw;
        flightNum = fn;
        depTime = dt;
        arrTime = at;
        effFrom=from;
        effTill=till;
        remarks=rem;
    }
}

