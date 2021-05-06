package it.polito.ezshop;

import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.data.EZShopDBManager;
import it.polito.ezshop.view.EZShopGUI;

import java.sql.SQLException;


public class EZShop {

    public static void main(String[] args){
        createTableIfNotExists();

        EZShopInterface ezShop = new it.polito.ezshop.data.EZShop();
        EZShopGUI gui = new EZShopGUI(ezShop);
    }

    public static void createTableIfNotExists(){
        try {
            EZShopDBManager.getInstance().getConnector().execute("CREATE TABLE Users (" +
                                                                    " ID int NOT NULL," +
                                                                    " Username varchar(255) UNIQUE," +
                                                                    " Password varchar(255) NOT NULL," +
                                                                    " Role varchar(255) NOT NULL," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EZShopDBManager.getInstance().getConnector().execute("CREATE TABLE Customers (" +
                                                                    " ID int NOT NULL," +
                                                                    " Name varchar(255) NOT NULL," +
                                                                    " Card varchar(255) NOT NULL," +
                                                                    " Points int NOT NULL," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EZShopDBManager.getInstance().getConnector().execute("CREATE TABLE Products (" +
                                                                    " ID int NOT NULL," +
                                                                    " ProductCode varchar(255) UNIQUE," +
                                                                    " Description varchar(255) NOT NULL," +
                                                                    " Quantity int NOT NULL," +
                                                                    " PricePerUnit double NOT NULL," +
                                                                    " Note varchar(255) NOT NULL," +
                                                                    " Position varchar(255)," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EZShopDBManager.getInstance().getConnector().execute("CREATE TABLE BalanceOperations (" +
                                                                    " ID int NOT NULL," +
                                                                    " Description varchar(255) NOT NULL," +
                                                                    " Amount double NOT NULL," +
                                                                    " Date date NOT NULL," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EZShopDBManager.getInstance().getConnector().execute("CREATE TABLE Orders (" +
                                                                    " ID int NOT NULL," +
                                                                    " ProductCode varchar(255) NOT NULL," +
                                                                    " PricePerUnit double NOT NULL," +
                                                                    " Status varchar(255) NOT NULL," +
                                                                    " Quantity int NOT NULL," +
                                                                    " BalanceID int NOT NULL," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EZShopDBManager.getInstance().getConnector().execute("CREATE TABLE Sales (" +
                                                                    " ID int NOT NULL," +
                                                                    " Price double NOT NULL," +
                                                                    " DiscountRate double NOT NULL," +
                                                                    " PRIMARY KEY(ID)" +
                                                                    ")");

            EZShopDBManager.getInstance().getConnector().execute("CREATE TABLE TicketsEntries (" +
                                                                    " ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                                                                    " SaleID int NOT NULL," +
                                                                    " ProductCode varchar(255) NOT NULL," +
                                                                    " ProductDescription varchar(255) NOT NULL," +
                                                                    " PricePerUnit double NOT NULL," +
                                                                    " Quantity int NOT NULL," +
                                                                    " DiscountRate double NOT NULL," +
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
