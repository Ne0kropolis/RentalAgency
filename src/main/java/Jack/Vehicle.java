package Jack;

import java.io.*;
public class Vehicle implements Serializable
{
    private int vehNumber;
    private String make;
    private String category;
    private double rentalPrice;
    private boolean availableForRent;

    public Vehicle()    {
    }

    public Vehicle(int vehNumber, String make, int category, boolean availableForRent)
    {
        setVehNumber(vehNumber);
        setMake(make);
        setCategory(category);
        setAvailableForRent(availableForRent);
        setRentalPrice(rentalPrice);
    }

    public void setVehNumber(int vehNumber) {
        this.vehNumber = vehNumber;
    }

    public void setMake(String make)    {
        this.make = make;
    }
    public void setRentalPrice(double rentalPrice)     {
        this.rentalPrice = rentalPrice;
    }

    public void setCategory(int sCategory)    {
        switch(sCategory)
        {
            case 1:
                category = "Sedan";
                rentalPrice = 450;
                break;
            case 2:
                category = "SUV";
                rentalPrice = 500;
                break;
        }
    }

    public void setAvailableForRent(boolean setAvailableForRent)
    {
        this.availableForRent = setAvailableForRent;
    }

    public int getVehNumber () {
        return vehNumber;
    }

    public String getMake()
    {
        return make;
    }

    public String getCategory()
    {
        return category;
    }
    public double getRentalPrice()
    {
        return rentalPrice;
    }

    //checks if the vehicle is available
    public boolean isAvailableForRent()
    {
        return availableForRent;
    }

    public String toString()
    {
        return String.format("%-10d\t%-20s\t%-10s\t%.2f\t\t%-6s",
                getVehNumber(), getMake(),getCategory(), getRentalPrice(), new Boolean(isAvailableForRent()).toString());
    }


}