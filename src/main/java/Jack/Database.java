package Jack;

import java.sql.*;

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
                    stmnt.executeUpdate("DROP TABLE CUSTOMER");
                } else if (tableName.equalsIgnoreCase("VEHICLE")) {
                    stmnt.executeUpdate("DROP TABLE VEHICLE");
                } else if (tableName.equalsIgnoreCase("RENTAL")) {
                    stmnt.executeUpdate("DROP TABLE RENTAL");
                }
            }

            stmnt.executeUpdate("CREATE TABLE CUSTOMER (custNumber NUMBER(3), firstName VARCHAR(40), surName VARCHAR(40), " +
                    "canRent CHAR(1))");

            stmnt.executeUpdate("CREATE TABLE VEHICLE (vehNumber NUMBER(4), make VARCHAR(40), category VARCHAR(10), " +
                    "rentalPrice FLOAT(6), availableForRent CHAR(1))");

            stmnt.executeUpdate("CREATE TABLE RENTAL (rentalNumber NUMBER(4), dateRental DATE, " +
                    "dateReturned DATE, pricePerDay FLOAT(6), totalRental FLOAT(8), custNumber NUMBER(3), " +
                    "vehNumber NUMBER(4))");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String in_sql) {
        try {
            stmnt.executeUpdate(in_sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String in_sql) {
        try {
            stmnt.executeQuery(in_sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

