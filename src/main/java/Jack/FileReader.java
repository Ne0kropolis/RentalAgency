package Jack;


import java.io.*;

public class FileReader {

    private Database database;

    public void passDatabaseReference(Database in_database) {
        this.database = in_database;
    }

    public void readCustomerFile() {

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

                    database.executeUpdate("INSERT INTO CUSTOMER VALUES ('" + String.valueOf(cust.getCustNumber()) +
                        "', '" + cust.getFirstName() + "', '" + cust.getSurName() + "', '" + canRent + "')");
                }
                catch (EOFException e) {
                    input.close();
                    e.printStackTrace();
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

    public void readVehicleFile() {

        try {

            ObjectInputStream input = new ObjectInputStream(new FileInputStream("vehicle.ser"));

            while (true) {
                try {

                    Vehicle vehicle = (Vehicle) input.readObject();
                    String available;

                    if(vehicle.isAvailableForRent()) {
                        available = "1";
                    }
                    else {
                        available = "0";
                    }

                    database.executeUpdate("INSERT INTO VEHICLE VALUES ('" + String.valueOf(vehicle.getVehNumber()) + "', '" +
                        vehicle.getMake() + "', '" + vehicle.getCategory() + "', '" + String.valueOf(vehicle.getRentalPrice()) +
                        "', '" + available + "')");

                }
                catch (EOFException e) {
                    input.close();
                    e.printStackTrace();
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
