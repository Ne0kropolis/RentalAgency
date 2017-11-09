package Jack;


import java.util.Scanner;

public class Driver {

    private void run() {

        Database database = new Database();
        FileReader fileReader = new FileReader();
        database.init();

        Scanner scan = new Scanner(System.in);
        System.out.println("Type 'y' if you would like to reload the database, this will return the database to its original state: ");
        String ans = scan.next();

        if (ans.equalsIgnoreCase("y")) {
            database.rebuild();
            fileReader.passDatabaseReference(database);
            fileReader.readCustomerFile();
            fileReader.readVehicleFile();
        }
    }

    public static void main(String[] args) {
        new Driver().run();
    }
}
