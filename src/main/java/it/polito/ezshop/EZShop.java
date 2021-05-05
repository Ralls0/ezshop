package it.polito.ezshop;

import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.data.EzShopDBManager;
import it.polito.ezshop.view.EZShopGUI;

import java.sql.ResultSet;
import java.sql.SQLException;


public class EZShop {

    public static void main(String[] args){
        createTableIfNotExists();

        EZShopInterface ezShop = new it.polito.ezshop.data.EZShop();
        EZShopGUI gui = new EZShopGUI(ezShop);
    }

    public static void createTableIfNotExists(){
        try {
            EzShopDBManager.getInstance().getConnector().execute("CREATE TABLE Users (" +
                                                                    " ID int NOT NULL," +
                                                                    " Username varchar(255) UNIQUE," +
                                                                    " Password varchar(255) NOT NULL," +
                                                                    " Role varchar(255) NOT NULL," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EzShopDBManager.getInstance().getConnector().execute("CREATE TABLE Customers (" +
                                                                    " ID int NOT NULL," +
                                                                    " Name varchar(255) NOT NULL," +
                                                                    " Card varchar(255) NOT NULL," +
                                                                    " Points int NOT NULL," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EzShopDBManager.getInstance().getConnector().execute("CREATE TABLE Products (" +
                                                                    " ID int NOT NULL," +
                                                                    " ProductCode varchar(255) UNIQUE," +
                                                                    " Description varchar(255) NOT NULL," +
                                                                    " Quantity int NOT NULL," +
                                                                    " PricePerUnit double NOT NULL," +
                                                                    " Note varchar(255) NOT NULL," +
                                                                    " Position varchar(255)," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EzShopDBManager.getInstance().getConnector().execute("CREATE TABLE BalanceOperations (" +
                                                                    " ID int NOT NULL," +
                                                                    " Description varchar(255) NOT NULL," +
                                                                    " Amount double NOT NULL," +
                                                                    " Date date NOT NULL," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {              //      X0Y32 IS THE ERROR WHEN TABLE ALREADY EXISTS
                System.out.println("Tables in DB already exists, skipping creation...");
            } else {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
