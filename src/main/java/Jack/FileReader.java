package Jack;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileReader {

    public void readFiles(Database in_database) {

        try {

            ObjectInputStream input = new ObjectInputStream(new FileInputStream("customer.ser"));

            while (true) {
                try {

                    Customer cust = (Customer) input.readObject();
                    String canRent;

                    if(cust.getCanRent()) {
                        canRent = "1";
                    }
                    else {
                        canRent = "0";
                    }

                    in_database.executeUpdate("INSERT INTO CUSTOMER VALUES ('" + String.valueOf(cust.getCustNumber()) +
                        "', '" + cust.getFirstName() + "', '" + cust.getSurName() + "', '" + canRent + "')");
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
