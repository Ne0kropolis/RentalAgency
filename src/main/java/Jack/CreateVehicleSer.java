package Jack;

import java.io.*;
public class CreateVehicleSer
{
 public static void main(String args[])  {
        Vehicle veh;
        try
        {
           ObjectOutputStream output = new ObjectOutputStream( new FileOutputStream("vehicle.ser"));
           output.writeObject(new Vehicle(1001, "BMW 750i", 1,   false));
           output.writeObject(new Vehicle(1002, "Audi Q7", 2,   true));
           output.writeObject(new Vehicle(1003, "Merecedes E350", 1,   true));
           output.writeObject(new Vehicle(1004, "Audi A7", 1,   false));
           output.writeObject(new Vehicle(1005, "Mercedes GLE", 2,   true));
           output.writeObject(new Vehicle(1006, "Volvo XC70", 2,   true));
           output.writeObject(new Vehicle(1007, "Merecedes S500", 1,   true));
           output.writeObject(new Vehicle(1008, "BMW X5", 2,   true));
           output.writeObject(new Vehicle(1009, "AUDI A8", 1,   false));
           output.writeObject(new Vehicle(1010, "BMW X3", 2,   true));
           output.close();
           ObjectInputStream input = new ObjectInputStream(new FileInputStream("vehicle.ser"));
           while (true) {
             veh = (Vehicle) input.readObject();
             System.out.println(veh.toString());
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
