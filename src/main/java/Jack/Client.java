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
            while (!text.equalsIgnoreCase("NO")) {
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

                if (in_text.equalsIgnoreCase("NO")) {
                    request = in_text;
                }
                else {
                    request = "QRSELECT * FROM RENTAL";
                    //request = "UINSERT INTO CUSTOMER VALUES (cust_id_seq.NEXTVAL, 'Jack', 'Forde', 1)";
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
                    if (result.equalsIgnoreCase("NO")) {
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
