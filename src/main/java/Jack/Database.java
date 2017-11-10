package Jack;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection con;
    private Statement stmnt;
    ResultSet rs;

    public void init() {

        try {
            Class.forName("oracle.jdbc.OracleDriver") ;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:jack/pass@localhost:1521:xe");
            stmnt = con.createStatement();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rebuild() {

        try {
            DatabaseMetaData md = con.getMetaData();
            rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                String tableName = rs.getString(3);
                if (tableName.equalsIgnoreCase("CUSTOMER")) {
                    stmnt.executeUpdate("DROP TABLE CUSTOMER CASCADE CONSTRAINTS");
                }
                else if (tableName.equalsIgnoreCase("VEHICLE")) {
                    stmnt.executeUpdate("DROP TABLE VEHICLE CASCADE CONSTRAINTS");
                }
                else if (tableName.equalsIgnoreCase("RENTAL")) {
                    stmnt.executeUpdate("DROP TABLE RENTAL CASCADE CONSTRAINTS");
                }
            }
            rs = md.getTables(con.getCatalog(), null, "%", new String[] { "SEQUENCE" });
            while (rs.next()) {
                String seqName = rs.getString(3);
                if (seqName.equalsIgnoreCase("cust_id_seq")) {
                    stmnt.executeUpdate("DROP SEQUENCE cust_id_seq");
                }
                else if (seqName.equalsIgnoreCase("veh_id_seq")) {
                    stmnt.executeUpdate("DROP SEQUENCE veh_id_seq");
                }
                else if (seqName.equalsIgnoreCase("rental_id_seq")) {
                    stmnt.executeUpdate("DROP SEQUENCE rental_id_seq");
                }
            }

            /**CREATE TABLES**/
            stmnt.executeUpdate("CREATE TABLE CUSTOMER (custNumber NUMBER(3), firstName VARCHAR(40), surName VARCHAR(40), " +
                    "canRent CHAR(1))");

            stmnt.executeUpdate("CREATE TABLE VEHICLE (vehNumber NUMBER(4), make VARCHAR(40), category VARCHAR(10), " +
                    "rentalPrice FLOAT(6), availableForRent CHAR(1))");

            stmnt.executeUpdate("CREATE TABLE RENTAL (rentalNumber NUMBER(4), dateRental DATE, " +
                    "dateReturned DATE, pricePerDay FLOAT(6), totalRental AS (pricePerDay * ((dateReturned - dateRental) + 1)), " +
                    "custNumber NUMBER(3), vehNumber NUMBER(4))");

            /**CREATE TABLE CONSTRAINTS**/
            stmnt.executeUpdate("ALTER TABLE CUSTOMER ADD CONSTRAINT CUSTOMER_PK PRIMARY KEY (custNumber)");
            stmnt.executeUpdate("ALTER TABLE CUSTOMER MODIFY (custNumber NOT NULL ENABLE)");
            stmnt.executeUpdate("ALTER TABLE CUSTOMER MODIFY (firstName NOT NULL ENABLE)");
            stmnt.executeUpdate("ALTER TABLE CUSTOMER MODIFY (surName NOT NULL ENABLE)");
            stmnt.executeUpdate("ALTER TABLE CUSTOMER MODIFY (canRent NOT NULL ENABLE)");

            stmnt.executeUpdate("ALTER TABLE VEHICLE ADD CONSTRAINT VEHICLE_PK PRIMARY KEY (vehNumber)");
            stmnt.executeUpdate("ALTER TABLE VEHICLE MODIFY (vehNumber NOT NULL ENABLE)");
            stmnt.executeUpdate("ALTER TABLE VEHICLE MODIFY (make NOT NULL ENABLE)");
            stmnt.executeUpdate("ALTER TABLE VEHICLE MODIFY (category NOT NULL ENABLE)");
            stmnt.executeUpdate("ALTER TABLE VEHICLE MODIFY (rentalPrice NOT NULL ENABLE)");
            stmnt.executeUpdate("ALTER TABLE VEHICLE MODIFY (availableForRent NOT NULL ENABLE)");

            stmnt.executeUpdate("ALTER TABLE RENTAL ADD CONSTRAINT RENTAL_PK PRIMARY KEY (rentalNumber)");
            stmnt.executeUpdate("ALTER TABLE RENTAL MODIFY (rentalNumber NOT NULL ENABLE)");
            stmnt.executeUpdate("ALTER TABLE RENTAL MODIFY (dateRental NOT NULL ENABLE)");

            /**CREATE REFERENCE CONSTRAINTS**/
            stmnt.executeUpdate("ALTER TABLE RENTAL ADD CONSTRAINT RENTAL_FK1 FOREIGN KEY (custNumber) " +
                    "REFERENCES CUSTOMER (custNumber) ON DELETE CASCADE ENABLE");
            stmnt.executeUpdate("ALTER TABLE RENTAL ADD CONSTRAINT RENTAL_FK2 FOREIGN KEY (vehNumber) " +
                    "REFERENCES VEHICLE (vehNumber) ON DELETE CASCADE ENABLE");

            /**CREATE SEQUENCES**/
            stmnt.executeUpdate("CREATE SEQUENCE cust_id_seq INCREMENT BY 100 START WITH 100 MAXVALUE 999999 NOCYCLE");
            stmnt.executeUpdate("CREATE SEQUENCE veh_id_seq INCREMENT BY 1 START WITH 1001 MAXVALUE 999999 NOCYCLE");
            stmnt.executeUpdate("CREATE SEQUENCE rental_id_seq INCREMENT BY 1 START WITH 1 MAXVALUE 999999 NOCYCLE");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String executeUpdate(String in_sql) {
        try {

            stmnt.executeUpdate(in_sql);
            return ("Update Successful");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> executeQuery(char in_table, String in_sql) {
        try {

            ArrayList<String> results = new ArrayList<String>();
            ResultSet rs = stmnt.executeQuery(in_sql);

            while (rs.next()) {

                if (in_table == 'C') {

                    int custNum = rs.getInt("custNumber");
                    String fName = rs.getString("firstName");
                    String lName = rs.getString("surName");
                    String canRent;
                    if (rs.getString("canRent").equals("1")) {
                        canRent = "Yes";
                    }
                    else {
                        canRent = "No";
                    }

                    results.add(String.valueOf(custNum) + ";" + fName + ";" + lName + ";" + canRent);
                }
                else if (in_table == 'V') {

                    int vehNum = rs.getInt("vehNumber");
                    String make = rs.getString("make");
                    String cat = rs.getString("category");
                    float price = rs.getFloat("rentalPrice");
                    String available;
                    if (rs.getString("availableForRent").equals("1")) {
                        available = "Yes";
                    }
                    else {
                        available = "No";
                    }

                    results.add(String.valueOf(vehNum) + ";" + make + ";" + cat + ";" + String.valueOf(price) + ";" + available);
                }
                else if (in_table == 'R') {

                    int rentNum = rs.getInt("rentalNumber");
                    java.sql.Date rentDate = rs.getDate("dateRental");
                    java.sql.Date returnDate = rs.getDate("dateReturned");
                    float priceDay = rs.getFloat("pricePerDay");
                    float totalRental = rs.getFloat("totalRental");
                    int custNum = rs.getInt("custNumber");
                    int vehNumber = rs.getInt("vehNumber");

                    results.add(String.valueOf(rentNum) + ";" + rentDate.toString() + ";" + returnDate.toString() + ";" +
                        String.valueOf(priceDay) + ";" + String.valueOf(totalRental) + ";" + String.valueOf(custNum) + ";" +
                        String.valueOf(vehNumber));
                }
            }
            return results;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

