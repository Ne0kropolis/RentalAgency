package Jack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private Socket socket;

    private void init() {
        try {
            socket = new Socket("localhost", 3434);
            String text = "";
            Scanner scan = new Scanner(System.in);
            while (!text.equalsIgnoreCase("CLOSE")) {
                System.out.println("SEND REQUEST?");
                text = scan.next();
                communicate(text);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void communicate(String in_text) {

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String request;

            try {

                if (in_text.equalsIgnoreCase("CLOSE")) {
                    request = in_text;
                }
                else {
                    //request = "QRSELECT * FROM RENTAL";
                    request = "URINSERT INTO RENTAL (rentalNumber, dateRental, pricePerDay, custNumber, vehNumber) " +
                            "VALUES (rental_id_seq.NEXTVAL, TO_DATE('2017-11-11', 'yyyy-mm-dd'), 450, '200', '1006')" +
                            "#UPDATE CUSTOMER SET canRent = '0' WHERE custNumber = '200'#UPDATE VEHICLE SET availableForRent = '0' " +
                            "WHERE vehNumber = '1006'";
                    //request = "UCINSERT INTO CUSTOMER VALUES (cust_id_seq.NEXTVAL, 'Jack', 'Forde', '1')";
                }

                out.writeObject(request);
                out.flush();
                if (request.charAt(0) == 'Q') {
                    ArrayList<String> results = (ArrayList<String>) in.readObject();
                    for (int i=0; i<results.size(); i++) {
                        System.out.println(results.get(i) + "\n");
                    }
                }
                else {
                    String result = (String) in.readObject();
                    System.out.println(result);
                    if (result.equalsIgnoreCase("CLOSE")) {
                        System.out.println("CLOSING CONNECTION...");
                        out.close();
                        in.close();
                        socket.close();
                    }
                }
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().init();
    }
}
