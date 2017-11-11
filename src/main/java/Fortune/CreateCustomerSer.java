package Fortune;



import java.io.*;
public class CreateCustomerSer
{
 
public static void main(String args[])  {
    	Customer cust;
    	try
        {
           ObjectOutputStream output = new ObjectOutputStream( new FileOutputStream("customer.ser"));
           output.writeObject(new Customer(100, "Estelle", "Zietsman", "Bellville", false));
           output.writeObject(new Customer(200, "Karin", "Swart", "Welgemoed", true));
           output.writeObject(new Customer(300, "Kruben", "Naidoo", "Sea Point", true));
           output.writeObject(new Customer(400, "Wilhelm", "Rothman", "Manenberg", true));
           output.writeObject(new Customer(500, "Gillian", "Khan", "Bellville", true));
           output.writeObject(new Customer(600, "Denise", "Lakay", "Bishops court", false));
           output.close();
           ObjectInputStream input = new ObjectInputStream(new FileInputStream("customer.ser"));
           while (true) {
          	 cust = (Customer) input.readObject();
           	 System.out.println(cust.toString());
          	}
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
