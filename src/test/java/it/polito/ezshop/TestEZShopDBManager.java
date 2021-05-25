package it.polito.ezshop;

import it.polito.ezshop.data.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class TestEZShopDBManager {

    EZShopDBManager instanceDB;

    @Before
    public void setUp() {
        try {
            instanceDB = EZShopDBManager.getInstance();
            instanceDB.createTableIfNotExists();
            instanceDB.resetDB();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {
            instanceDB.resetDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  USER QUERY
    @Test
    public void testGetNextUserID() {
        try {
            instanceDB.resetDB();
            int nextID = instanceDB.getNextUserID();
            instanceDB.saveUser(new EZUser(nextID,"testUsername","password", "Administrator"));
            int newNextID = instanceDB.getNextUserID();

            assertNotSame(nextID,newNextID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadAllUsers() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextUserID();
            EZUser u1 = new EZUser(nextID,"testUsername1","password1", "Administrator");
            instanceDB.saveUser(u1);

            nextID = instanceDB.getNextUserID();
            EZUser u2 = new EZUser(nextID,"testUsername2","password2", "Administrator");
            instanceDB.saveUser(u2);

            List<User> users = instanceDB.loadAllUsers();
            boolean error = false;
            if (users.size() != 2)
                error = true;

            if (!users.contains(u1) || !users.contains(u2))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadUserFromID() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextUserID();
            EZUser u1 = new EZUser(nextID,"testUsername","password", "Administrator");
            instanceDB.saveUser(u1);

            User user = instanceDB.loadUser(nextID);
            boolean error = false;
            if (!user.equals(u1))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadUserFromUsernameAndPass() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextUserID();
            EZUser u1 = new EZUser(nextID,"testUsername","password", "Administrator");
            instanceDB.saveUser(u1);

            User user = instanceDB.loadUser("testUsername", "password");
            boolean error = false;
            if (!user.equals(u1))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserExistsFromID() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextUserID();

            if (instanceDB.userExists(nextID))
                error = true;

            EZUser u1 = new EZUser(nextID,"testUsername","password", "Administrator");
            instanceDB.saveUser(u1);

            if (!instanceDB.userExists(nextID))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserExistsFromUsername() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextUserID();

            if (instanceDB.userExists("testUsername"))
                error = true;

            EZUser u1 = new EZUser(nextID,"testUsername","password", "Administrator");
            instanceDB.saveUser(u1);

            if (!instanceDB.userExists("testUsername"))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateUserRights() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextUserID();
            EZUser u1 = new EZUser(nextID,"testUsername","password", "Administrator");
            instanceDB.saveUser(u1);

            instanceDB.updateUserRights(nextID, "Cashier");
            User userDB = instanceDB.loadUser(nextID);

            assertEquals(userDB.getRole(), "Cashier");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteUser() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextUserID();
            EZUser u1 = new EZUser(nextID,"testUsername","password", "Administrator");
            instanceDB.saveUser(u1);

            if (!instanceDB.userExists(nextID))
                error = true;

            instanceDB.deleteUser(nextID);

            if (instanceDB.userExists(nextID))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  CUSTOMER QUERY

    @Test
    public void testGetNextCustomerID() {
        try {
            instanceDB.resetDB();
            int nextID = instanceDB.getNextCustomerID();
            instanceDB.saveCustomer(new EZCustomer(nextID,"testName","testCard", 0));
            int newNextID = instanceDB.getNextCustomerID();

            assertNotSame(nextID,newNextID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadAllCustomers() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextCustomerID();
            EZCustomer c1 = new EZCustomer(nextID,"testName1","testCard1", 0);
            instanceDB.saveCustomer(c1);

            nextID = instanceDB.getNextCustomerID();
            EZCustomer c2 = new EZCustomer(nextID,"testName2","testCard2", 0);
            instanceDB.saveCustomer(c2);

            List<Customer> customers = instanceDB.loadAllCustomers();
            boolean error = false;
            if (customers.size() != 2)
                error = true;

            if (!customers.contains(c1) || !customers.contains(c2))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadCustomerFromID() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextCustomerID();
            EZCustomer c = new EZCustomer(nextID,"testName","testCard", 0);
            instanceDB.saveCustomer(c);

            Customer customer = instanceDB.loadCustomer(nextID);
            boolean error = false;
            if (!customer.equals(c))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadCustomerFromCard() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextCustomerID();
            EZCustomer c = new EZCustomer(nextID,"testName","testCard", 0);
            instanceDB.saveCustomer(c);

            Customer customer = instanceDB.loadCustomer("testCard");
            boolean error = false;
            if (!customer.equals(c))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCustomerExistsFromID() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextCustomerID();

            if (instanceDB.customerExists(nextID))
                error = true;

            EZCustomer c = new EZCustomer(nextID,"testName","testCard", 0);
            instanceDB.saveCustomer(c);

            if (!instanceDB.customerExists(nextID))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCustomerExistsFromName() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextCustomerID();

            if (instanceDB.customerExists("testName"))
                error = true;

            EZCustomer c = new EZCustomer(nextID,"testName","testCard", 0);
            instanceDB.saveCustomer(c);

            if (!instanceDB.customerExists("testName"))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCustomerExistsFromCard() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextCustomerID();

            if (instanceDB.customerExistsFromCard("testCard"))
                error = true;

            EZCustomer c = new EZCustomer(nextID,"testName","testCard", 0);
            instanceDB.saveCustomer(c);

            if (!instanceDB.customerExistsFromCard("testCard"))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateCustomer() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextCustomerID();
            EZCustomer c = new EZCustomer(nextID,"testName","testCard", 0);
            instanceDB.saveCustomer(c);

            instanceDB.updateCustomer(nextID, "newName", "newCard",1);
            Customer customerDB = instanceDB.loadCustomer(nextID);

            assertEquals(customerDB.getCustomerName(), "newName");
            assertEquals(customerDB.getCustomerCard(), "newCard");
            assertEquals( (long) customerDB.getPoints(),1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteCustomer() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextCustomerID();
            EZCustomer c = new EZCustomer(nextID,"testName","testCard", 0);
            instanceDB.saveCustomer(c);

            if (!instanceDB.customerExists(nextID))
                error = true;

            instanceDB.deleteCustomer(nextID);

            if (instanceDB.customerExists(nextID))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  CUSTOMER QUERY

    @Test
    public void testGetNextOrderID() {
        try {
            instanceDB.resetDB();
            int nextID = instanceDB.getNextOrderID();
            instanceDB.saveOrder(new EZOrder(nextID, -1, 1, "productCode", "ISSUED", 1d));
            int newNextID = instanceDB.getNextOrderID();

            assertNotSame(nextID,newNextID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadAllOrders() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextOrderID();
            EZOrder o1 = new EZOrder(nextID, -1, 1, "productCode1", "ISSUED", 1d);
            instanceDB.saveOrder(o1);

            nextID = instanceDB.getNextOrderID();
            EZOrder o2 = new EZOrder(nextID, -2, 2, "productCode2", "ISSUED", 2d);
            instanceDB.saveOrder(o2);

            List<Order> orders = instanceDB.loadAllOrders();
            boolean error = false;
            if (orders.size() != 2)
                error = true;

            if (!orders.contains(o1) || !orders.contains(o2))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadOrderFromID() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextOrderID();
            EZOrder o = new EZOrder(nextID, -1, 1, "productCode", "ISSUED", 1d);
            instanceDB.saveOrder(o);

            Order order = instanceDB.loadOrder(nextID);
            boolean error = false;
            if (!order.equals(o))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateOrder() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextOrderID();
            EZOrder o = new EZOrder(nextID, -1, 1, "productCode", "ISSUED", 1d);
            instanceDB.saveOrder(o);

            o.setBalanceId(-2);
            o.setStatus("PAYED");

            instanceDB.updateOrder(o);
            Order orderDB = instanceDB.loadOrder(nextID);

            assertEquals((int) orderDB.getBalanceId(), -2);
            assertEquals(orderDB.getStatus(),"PAYED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  PRODUCT QUERY

    @Test
    public void testGetNextProductID() {
        try {
            instanceDB.resetDB();
            int nextID = instanceDB.getNextProductID();
            instanceDB.saveProduct(new EZProductType(nextID,1,"productCode","productDescription","productNote","productLocation",1d));
            int newNextID = instanceDB.getNextProductID();

            assertNotSame(nextID,newNextID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadAllProducts() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextProductID();
            EZProductType p1 = new EZProductType(nextID,1,"productCode1","productDescription1","productNote1","productLocation1",1d);
            instanceDB.saveProduct(p1);

            nextID = instanceDB.getNextProductID();
            EZProductType p2 = new EZProductType(nextID,2,"productCode2","productDescription2","productNote2","productLocation2",2d);
            instanceDB.saveProduct(p2);

            List<ProductType> products = instanceDB.loadAllProducts();
            boolean error = false;
            if (products.size() != 2)
                error = true;

            if (!products.contains(p1) || !products.contains(p2))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadProductFromID() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextProductID();
            EZProductType p = new EZProductType(nextID,1,"productCode","productDescription","productNote","productLocation",1d);
            instanceDB.saveProduct(p);

            ProductType product = instanceDB.loadProduct(nextID);
            boolean error = false;
            if (!product.equals(p))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadProductFromBarCode() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextProductID();
            EZProductType p = new EZProductType(nextID,1,"productCode","productDescription","productNote","productLocation",1d);
            instanceDB.saveProduct(p);

            ProductType product = instanceDB.loadProductByBarCode("productCode");
            boolean error = false;
            if (!product.equals(p))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProductExistsFromID() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextProductID();

            if (instanceDB.productExists(nextID))
                error = true;

            EZProductType p = new EZProductType(nextID,1,"productCode","productDescription","productNote","productLocation",1d);
            instanceDB.saveProduct(p);

            if (!instanceDB.productExists(nextID))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProductExistsFromBarCode() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextProductID();

            if (instanceDB.productExists("productCode"))
                error = true;

            EZProductType p = new EZProductType(nextID,1,"productCode","productDescription","productNote","productLocation",1d);
            instanceDB.saveProduct(p);

            if (!instanceDB.productExists("productCode"))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProductExistsFromLocation() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextProductID();

            if (instanceDB.productExistsFromLocation("productLocation"))
                error = true;

            EZProductType p = new EZProductType(nextID,1,"productCode","productDescription","productNote","productLocation",1d);
            instanceDB.saveProduct(p);

            if (!instanceDB.productExistsFromLocation("productLocation"))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateProduct() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextProductID();
            EZProductType p = new EZProductType(nextID,1,"productCode","productDescription","productNote","productLocation",1d);
            instanceDB.saveProduct(p);

            p.setBarCode("newCode");
            p.setProductDescription("newDescription");
            p.setNote("newNote");
            p.setLocation("newLocation");
            p.setQuantity(2);
            p.setPricePerUnit(2d);

            instanceDB.updateProduct(p);
            ProductType productDB = instanceDB.loadProduct(nextID);

            assertEquals(productDB.getBarCode(), "newCode");
            assertEquals(productDB.getProductDescription(), "newDescription");
            assertEquals(productDB.getNote(), "newNote");
            assertEquals(productDB.getLocation(), "newLocation");
            assertEquals((int) productDB.getQuantity(), 2);
            assertEquals(productDB.getPricePerUnit(), 2d, 0.001);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteProduct() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextProductID();
            EZProductType p = new EZProductType(nextID,1,"productCode","productDescription","productNote","productLocation",1d);
            instanceDB.saveProduct(p);

            if (!instanceDB.productExists(nextID))
                error = true;

            instanceDB.deleteProduct(nextID);

            if (instanceDB.productExists(nextID))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  SALE QUERY

    @Test
    public void testGetNextSaleID() {
        try {
            instanceDB.resetDB();
            int nextID = instanceDB.getNextSaleID();
            instanceDB.saveSale(new EZSaleTransaction(nextID));
            int newNextID = instanceDB.getNextSaleID();

            assertNotSame(nextID,newNextID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadAllSales() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextSaleID();
            EZSaleTransaction s1 = new EZSaleTransaction(nextID);
            instanceDB.saveSale(s1);

            nextID = instanceDB.getNextSaleID();
            EZSaleTransaction s2 = new EZSaleTransaction(nextID);
            instanceDB.saveSale(s2);

            List<EZSaleTransaction> sales = instanceDB.loadAllSales();
            boolean error = false;
            if (sales.size() != 2)
                error = true;

            if (!sales.contains(s1) || !sales.contains(s2))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadSaleFromID() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextSaleID();
            EZSaleTransaction s = new EZSaleTransaction(nextID);
            instanceDB.saveSale(s);

            EZSaleTransaction sale = instanceDB.loadSale(nextID);
            boolean error = false;
            if (!sale.equals(s))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateSale() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextSaleID();
            EZSaleTransaction s = new EZSaleTransaction(nextID);
            instanceDB.saveSale(s);

            EZTicketEntry t1 = new EZTicketEntry("productCode1","productDescription1",1,1d,0d);
            EZTicketEntry t2 = new EZTicketEntry("productCode2","productDescription2",2,2d,0.1);
            s.addProductToSale(t1.getBarCode(),t1.getProductDescription(),t1.getPricePerUnit(),t1.getDiscountRate(),t1.getAmount());
            s.addProductToSale(t2.getBarCode(),t2.getProductDescription(),t2.getPricePerUnit(),t2.getDiscountRate(),t2.getAmount());

            s.setDiscountRate(0.1);
            s.setStatus("payed");

            instanceDB.updateSale(s);
            EZSaleTransaction saleDB = instanceDB.loadSale(nextID);

            assertEquals(saleDB.getDiscountRate(), 0.1, 0.001);
            assertEquals(saleDB.getStatus(), "payed");

            boolean error = false;
            if (saleDB.getEntries().size() != 2)
                error = true;

            if (!saleDB.getEntries().contains(t1) || !saleDB.getEntries().contains(t2))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteSale() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextSaleID();
            EZSaleTransaction s = new EZSaleTransaction(nextID);
            instanceDB.saveSale(s);

            if (instanceDB.loadSale(nextID) == null)
                error = true;

            instanceDB.deleteSale(nextID);

            if (instanceDB.loadSale(nextID) != null)
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  BALANCE QUERY

    @Test
    public void testGetNextBalanceOperationID() {
        try {
            instanceDB.resetDB();
            int nextID = instanceDB.getNextBalanceOperationID();
            instanceDB.saveBalanceOperation(new EZBalanceOperation(nextID, LocalDate.now(), 1d, "CREDIT"));
            int newNextID = instanceDB.getNextBalanceOperationID();

            assertNotSame(nextID,newNextID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadAllBalanceOperations() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextBalanceOperationID();
            EZBalanceOperation b1 = new EZBalanceOperation(nextID, LocalDate.now(), 1d, "CREDIT");
            instanceDB.saveBalanceOperation(b1);

            nextID = instanceDB.getNextBalanceOperationID();
            EZBalanceOperation b2 = new EZBalanceOperation(nextID, LocalDate.now(), 5d, "DEBIT");
            instanceDB.saveBalanceOperation(b2);

            List<BalanceOperation> ops = instanceDB.loadAllBalanceOperations();
            boolean error = false;
            if (ops.size() != 2)
                error = true;

            if (!ops.contains(b1) || !ops.contains(b2))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //  SALE QUERY

    @Test
    public void testGetNextReturnID() {
        try {
            instanceDB.resetDB();
            int nextID = instanceDB.getNextReturnID();
            instanceDB.saveReturn(new EZReturnTransaction(-1, nextID));
            int newNextID = instanceDB.getNextReturnID();

            assertNotSame(nextID,newNextID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadReturnFromID() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextReturnID();
            EZReturnTransaction r = new EZReturnTransaction(-1, nextID);
            instanceDB.saveReturn(r);

            EZReturnTransaction returnTransaction = instanceDB.loadReturn(nextID);
            boolean error = false;
            if (!returnTransaction.equals(r))
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateReturnStatus() {
        try {
            instanceDB.resetDB();

            int nextID = instanceDB.getNextReturnID();
            EZReturnTransaction r = new EZReturnTransaction(-1, nextID);
            instanceDB.saveReturn(r);

            instanceDB.updateReturnStatus(nextID, "payed");

            EZReturnTransaction returnDB = instanceDB.loadReturn(nextID);

            assertEquals(returnDB.getStatus(), "payed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteReturn() {
        try {
            instanceDB.resetDB();
            boolean error = false;

            int nextID = instanceDB.getNextReturnID();
            EZReturnTransaction r = new EZReturnTransaction(-1, nextID);
            instanceDB.saveReturn(r);

            if (instanceDB.loadReturn(nextID) == null)
                error = true;

            instanceDB.deleteReturnTransaction(nextID);

            if (instanceDB.loadReturn(nextID) != null)
                error = true;

            assertFalse(error);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
