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
            int numRows = 0;

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

                    database.executeUpdate("INSERT INTO CUSTOMER VALUES (cust_id_seq.NEXTVAL, '" +
                            cust.getFirstName() + "', '" + cust.getSurName() + "', '" + canRent + "')");
                    numRows++;
                }
                catch (EOFException e) {
                    System.out.println("Number of rows inserted into table CUSTOMER: " + numRows);
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
            int numRows = 0;

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

                    database.executeUpdate("INSERT INTO VEHICLE VALUES (veh_id_seq.NEXTVAL, '" +
                        vehicle.getMake() + "', '" + vehicle.getCategory() + "', '" + String.valueOf(vehicle.getRentalPrice()) +
                        "', '" + available + "')");
                    numRows++;
                }
                catch (EOFException e) {
                    System.out.println("Number of rows inserted into table VEHICLE: " + numRows);
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

    public void readRentalFile() {

        try {

            ObjectInputStream input = new ObjectInputStream(new FileInputStream("rental.ser"));
            int numRows = 0;

            while (true) {
                try {

                    Rental rental = (Rental) input.readObject();
                    String dateRented = rental.getDateRented().replaceAll("/", "-");
                    String dateReturned;
                    if (rental.getDateReturned().equals("NA")) {
                        dateReturned = "NULL, '";
                    }
                    else {
                        dateReturned = "TO_DATE('" + rental.getDateReturned().replaceAll("/", "-") + "', 'yyyy-mm-dd'), '";
                    }

                    database.executeUpdate("INSERT INTO RENTAL (rentalNumber, dateRental, dateReturned, pricePerDay, " +
                            "custNumber, vehNumber) VALUES (rental_id_seq.NEXTVAL, " +
                            "TO_DATE('" + dateRented + "', 'yyyy-mm-dd'), " + dateReturned +
                            String.valueOf(rental.getPricePerDay()) + "', '" + String.valueOf(rental.getCustNumber())
                            + "', '" + String.valueOf(rental.getVehNumber()) + "')");
                    numRows++;
                }
                catch (EOFException e) {
                    System.out.println("Number of rows inserted into table RENTAL: " + numRows);
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
