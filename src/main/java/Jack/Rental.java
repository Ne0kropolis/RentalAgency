package Jack;

import java.io.*;
import java.util.*;

public class Rental  implements Serializable
 {
    private int rentalNumber;
    private String dateRented;
    private String dateReturned;
    private double pricePerDay;
    private double totalRental;
    private int custNumber;
    private int vehNumber;

    public Rental() {
    }

    public Rental (int  rentalNumber, String dateRented, double pricePerDay, int custNumber , int vehNumber) {
        this.rentalNumber = rentalNumber;
        this.dateRented = dateRented;
        this.dateReturned = "NA";
        this.pricePerDay = pricePerDay;
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
        //determineTotalRental();
    }

     public Rental (int  rentalNumber, String dateRented, String dateReturned, double pricePerDay, double totalRental,
                    int custNumber , int vehNumber) {
        this.rentalNumber = rentalNumber;
        this.dateRented = dateRented;
        this.pricePerDay = pricePerDay;
        setDateReturned(dateReturned);
        this.custNumber = custNumber;
        this.vehNumber = vehNumber;
        
    }
    public int getRentalNumber()
    {
        return this.rentalNumber;        
    }
    public String getDateRented()
    {
        return this.dateRented;        
    }
    public String getDateReturned()
    {
        return this.dateReturned;        
    }
    public double getPricePerDay()
    {
        return this.pricePerDay;        
    }
    public void setRentalNumber(int rn)
    {
        this.rentalNumber = rn;        
    }
    public void setDateRented(String rentD)
    {
        this.dateRented = rentD;
    }
    public void setPricePerDay(double pricePerDay)
    {
        this.pricePerDay = pricePerDay;
    }
    public void setDateReturned(String ret)
    {
        this.dateReturned = ret;
        determineTotalRental();
    }
     public void setCustNumber(int cn)
    {
        this.custNumber = cn;        
    }
     public void setVehNumber(int vn)
    {
        this.vehNumber = vn;        
    }

     public int getCustNumber() {
         return  this.custNumber;
     }

     public int getVehNumber() {
         return this.vehNumber;
     }

     public void determineTotalRental()
    {
        totalRental = dateDifference(dateRented, dateReturned) * pricePerDay;
        
    }
    private int dateDifference(String dateRented, String dateReturned) {
        int yyyy, mm, dd;
        StringTokenizer token;
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        
        token = new StringTokenizer(dateRented, "/");
        yyyy = Integer.parseInt(token.nextToken());
        mm = Integer.parseInt(token.nextToken());
        dd = Integer.parseInt(token.nextToken());
        cal1.set(yyyy, mm, dd); 
        if (!dateReturned.equalsIgnoreCase("NA")){
            token = new StringTokenizer(dateReturned, "/");
            yyyy = Integer.parseInt(token.nextToken());
            mm = Integer.parseInt(token.nextToken());
            dd = Integer.parseInt(token.nextToken());
            cal2.set(yyyy, mm, dd);
            return (daysBetween(cal1.getTime(),cal2.getTime()));
        }
        else
            return (0);
    }
    public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24)+1);
 }
 
    public int calcNumberOfDays()
    {
        return dateDifference(dateRented, dateReturned);
    }

    public String toString() {
        return "" + rentalNumber + "  " + dateRented + " " + dateReturned + " " + pricePerDay + " " +
            totalRental + " " + custNumber + " " + vehNumber +"\n" + "No of Days:" + calcNumberOfDays();
    }

   
}