package it.polito.ezshop.data;

import java.sql.SQLException;

public class EzShopDBManager {

    private static EzShopDBManager instance;
    private DBConnector db;

    private EzShopDBManager() throws SQLException, ClassNotFoundException {
        this.db = DBConnector.getInstance();
    }

    public static EzShopDBManager getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new EzShopDBManager();
        }
        return instance;
    }

    public DBConnector getConnector() {
        return db;
    }

}
