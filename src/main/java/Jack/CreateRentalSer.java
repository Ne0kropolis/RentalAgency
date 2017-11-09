package Jack;

/**
 * Write a description of class CreateRentalSer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.*;
public class CreateRentalSer
{
  public static void main(String args[])  {
    	Rental rental;
    	try
        {
           ObjectOutputStream output = new ObjectOutputStream( new FileOutputStream("rental.ser"));
           output.writeObject(new Rental(1, "2015/09/01", "2015/09/02", 450.00, 900.00, 100, 1001));
           output.writeObject(new Rental(2, "2015/08/01", "2015/08/04", 500.00, 2000.00, 200, 1005));
           output.writeObject(new Rental(3, "2015/08/22", "2015/08/30", 500.00, 4500.00, 100, 1007));
           output.writeObject(new Rental(4, "2015/07/14", "2015/07/16", 450.00, 1350.00, 100, 1001));
           output.writeObject(new Rental(5, "2015/10/01", 450.00, 600, 1004));
           output.writeObject(new Rental(6, "2015/10/01",  450.00,  100, 1001));


           output.close();
           ObjectInputStream input = new ObjectInputStream(new FileInputStream("rental.ser"));
           while (true) {
          	 rental = (Rental) input.readObject();
           	 System.out.println(rental.toString());
          	}
          	//dateDifference("2015/10/01", "2015/10/01");
       }
       catch(EOFException fnfe )
        {
            return;        
        }       
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        catch(ClassNotFoundException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }

    }
}
