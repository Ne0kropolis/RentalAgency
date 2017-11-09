package Jack;

import java.io.*;
public class Customer implements Serializable
{
	private int custNumber;
    private String firstName;
    private String surName;
    private String address;
    private boolean canRent;   //true - can rent a car; false - have a car


    public Customer()    {

    }

    public Customer(int custNumber, String firstName, String surName, String addr, boolean canRent)    {
    	setCustNumber(custNumber);
        setFirstName(firstName);
        setSurName(surName);
        setAddress(addr);
        setCanRent(canRent);
    }

    public void setCustNumber(int custNumber)  {
    	this.custNumber = custNumber;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setSurName(String surName)   {
        this.surName = surName;
    }

    public void setAddress(String addr)    {
        this.address = addr;
    }

	public void setCanRent(boolean canRent)  {
		this.canRent = canRent;
	}

    public int getCustNumber()  {
    	return custNumber;
    }
    public String getFirstName()     {
        return firstName;
    }

    public String getSurName()    {
        return surName;
    }

    public String getAddress()    {
        return address;
    }

	public boolean getCanRent()     {
        return canRent;
    }

    public String toString()      {
        return String.format("%-15d\t%-15s\t%-15s\t%-15s\t%-15s", getCustNumber(), getFirstName(), getSurName(),
                getAddress(), new Boolean(getCanRent()).toString());
    }

    
}