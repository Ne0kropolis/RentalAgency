package Fortune;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private static Socket socket;
    private static ArrayList<String> resultSet = new ArrayList<String>();
    
    public static void init() {
        try {
            socket = new Socket("localhost", 3434);
            String text = "";
            Scanner scan = new Scanner(System.in);
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void communicate(String in_text) {

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
                    request = in_text;
                    //request = "UCINSERT INTO CUSTOMER VALUES (cust_id_seq.NEXTVAL, 'Jack', 'Forde', '1')";
                }

                out.writeObject(request);
                out.flush();
                if (request.charAt(0) == 'Q') {
                    ArrayList<String> results = (ArrayList<String>) in.readObject();
                    resultSet = results;
                    for (int i=0; i<results.size(); i++) {
                        System.out.println(results.get(i) + "\n");
                        String test = results.get(i).substring(0,2);
                        System.out.println(test);
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

    public ArrayList<String> getResultSet() {
        return (this.resultSet);
    }
    
}
