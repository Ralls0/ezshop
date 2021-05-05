package it.polito.ezshop;

import it.polito.ezshop.data.EZShopInterface;
import it.polito.ezshop.data.EzShopDBManager;
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
            EzShopDBManager.getInstance().getConnector().execute("CREATE TABLE TEST (" +
                    " Name varchar(50) NOT NULL," +
                    " PRIMARY KEY(Name)" +
                    ")");

            EzShopDBManager.getInstance().getConnector().execute("INSERT INTO TEST" +
                    " VALUES('aaa')");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
