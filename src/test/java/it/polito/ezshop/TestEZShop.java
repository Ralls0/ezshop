package it.polito.ezshop;

import it.polito.ezshop.data.*;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestEZShop {

    EZShopInterface ezShop;

    @Before
    public void setUp() {
        ezShop = new it.polito.ezshop.data.EZShop();
        ezShop.reset();
    }

    @After
    public void tearDown() {
        ezShop.reset();
        ezShop = null;
    }

    @Test
    public void testCreateUser() {

        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.createUser("", "password", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.createUser(null, "password", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.createUser("Giovanni", "", "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.createUser("Giovanni", null, "Administrator");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidRoleException.class, () -> {
            ezShop.createUser("Giovanni", "password", "Cashier");
        });

        // ------------------------------------------------------------- //

        Integer id = -1;
        try {
            id = ezShop.createUser("Giovanni", "password", "Administrator");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(id == 1);

        // ------------------------------------------------------------- //

        try {
            id = ezShop.createUser("Giovanni", "password", "Administrator");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(id == -1);

    }

    @Test
    public void testDeleteUser() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteUser(1);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.deleteUser(-1);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.deleteUser(null);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.logout();
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteUser(0);
        });

        // ------------------------------------------------------------- //

        boolean deleted = false;
        try {
            ezShop.logout();
            ezShop.login("Giovanni", "password");
            deleted = ezShop.deleteUser(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertTrue(deleted);

    }

    @Test
    public void testGetAllUsers() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getAllUsers();
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
            ezShop.logout();
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getAllUsers();
        });

        // ------------------------------------------------------------- //

        List<User> users = null;

        try {
            ezShop.logout();
            ezShop.login("Giovanni", "password");
            users = ezShop.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(users);

    }

    @Test
    public void testGetUser() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.getUser(-1);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.getUser(null);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getUser(2);
        });

        // ------------------------------------------------------------- //

        User user = null;
        try {
            ezShop.logout();
            ezShop.login("Giovanni", "password");
            user = ezShop.getUser(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(user);

    }

    @Test
    public void testUpdateUserRights() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.updateUserRights(-1, "ShopManager");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidUserIdException.class, () -> {
            ezShop.updateUserRights(null, "ShopManager");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidRoleException.class, () -> {
            ezShop.updateUserRights(1, null);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidRoleException.class, () -> {
            ezShop.updateUserRights(1, "Ruolo");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updateUserRights(2, null);
        });

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updateUserRights(2, "ShopManager");
        });

        // ------------------------------------------------------------- //

        boolean modified = false;
        try {
            ezShop.login("Giovanni", "password");
            modified = ezShop.updateUserRights(2, "ShopManager");
            assertTrue(modified);
            modified = false;
            modified = ezShop.updateUserRights(3, "ShopManager");
            assertFalse(modified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLogin() {
        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.login("", "password");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidUsernameException.class, () -> {
            ezShop.login(null, "password");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.login("Giovanni", "");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidPasswordException.class, () -> {
            ezShop.createUser("Giovanni", null, "Administrator");
        });

        // ------------------------------------------------------------- //

        User user = null;

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            user = ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(user);

    }

    @Test
    public void testLogout() {
        boolean logged_out = false;
        logged_out = ezShop.logout();
        assertTrue(logged_out);
    }

    @Test
    public void createProductType() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidPricePerUnitException.class, () -> {
            ezShop.createProductType("descrizione", "3000000000083", -9.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductDescriptionException.class, () -> {
            ezShop.createProductType("", "3000000000083", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductDescriptionException.class, () -> {
            ezShop.createProductType(null, "3000000000083", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.createProductType("descrizione", "", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.createProductType("descrizione", null, 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.createProductType("descrizione", "3000000000087", 10.5, "nota");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
        });

        // ------------------------------------------------------------- //

        Integer productId = -1;
        try {
            ezShop.login("Giovanni", "password");
            productId = ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
            assertTrue(productId > 0);
            productId = ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
            assertEquals(-1, (int) productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateProduct() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidPricePerUnitException.class, () -> {
            ezShop.updateProduct(1, "descrizione", "3000000000083", -9.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductDescriptionException.class, () -> {
            ezShop.updateProduct(1, "", "3000000000083", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductDescriptionException.class, () -> {
            ezShop.updateProduct(1, null, "3000000000083", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.updateProduct(1, "descrizione", "", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.updateProduct(1, "descrizione", null, 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.updateProduct(1, "descrizione", "3000000000087", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductIdException.class, () -> {
            ezShop.updateProduct(null, "descrizione", "3000000000083", 10.5, "nota");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductIdException.class, () -> {
            ezShop.updateProduct(-2, "descrizione", "3000000000083", 10.5, "nota");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updateProduct(1, "descrizione", "3000000000083", 10.5, "nota");
        });

        // ------------------------------------------------------------- //

        boolean updated = false;
        try {
            ezShop.login("Giovanni", "password");
            ezShop.createProductType("descrizione", "3000000000083", 10, "nota");
            updated = ezShop.updateProduct(1, "new descrizione", "3000000000076", 10, "new nota");
            assertTrue(updated);
            updated = ezShop.updateProduct(1, "new descrizione", "3000000000076", 10, "new nota");
            assertTrue(updated);
            updated = ezShop.updateProduct(2, "new descrizione", "3000000000076", 10, "new nota");
            assertFalse(updated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteProductType() {
        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidProductIdException.class, () -> {
            ezShop.deleteProductType(-1);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductIdException.class, () -> {
            ezShop.deleteProductType(null);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteProductType(1);
        });

        // ------------------------------------------------------------- //

        boolean deleted = false;

        try {
            ezShop.login("Giovanni", "password");
            ezShop.createProductType("descrizione", "3000000000083", 10, "nota");
            deleted = ezShop.deleteProductType(1);
            assertTrue(deleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllProductTypes() {
        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getAllProductTypes();
        });

        // ------------------------------------------------------------- //

        List<ProductType> products = null;

        try {
            ezShop.login("Giovanni", "password");
            products = ezShop.getAllProductTypes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(products);

    }

    @Test
    public void getProductTypeByBarCode() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.getProductTypeByBarCode("");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.getProductTypeByBarCode(null);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.getProductTypeByBarCode("3000000000087");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getProductTypeByBarCode("3000000000083");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getProductTypeByBarCode("3000000000083");
        });

        // ------------------------------------------------------------- //

        ProductType product = null;
        try {
            ezShop.login("Giovanni", "password");
            ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
            product = ezShop.getProductTypeByBarCode("3000000000083");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(product);

    }

    @Test
    public void getProductTypesByDescription() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getProductTypesByDescription("descrizione");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getProductTypesByDescription("descrizione");
        });
        // ------------------------------------------------------------- //

        List<ProductType> products = null;

        try {
            ezShop.login("Giovanni", "password");
            ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
            ezShop.createProductType("descrizione 2", "3000000000076", 10, "nota 2");
            products = ezShop.getProductTypesByDescription("descrizione");
            assertFalse(products.isEmpty());
            products = ezShop.getProductTypesByDescription("descrizione 2");
            assertFalse(products.isEmpty());
            products = ezShop.getProductTypesByDescription("descrizione 3");
            assertTrue(products.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateQuantity() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updateQuantity(1, 10);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updateQuantity(1, 10);
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidProductIdException.class, () -> {
            ezShop.updateQuantity(null, 10);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductIdException.class, () -> {
            ezShop.updateQuantity(-10, 10);
        });

        // ------------------------------------------------------------- //

        boolean updated = false;

        try {
            ezShop.login("Giovanni", "password");
            ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
            updated = ezShop.updateQuantity(3, 10);
            assertFalse(updated);
            ezShop.updatePosition(1, "1-a-1");
            updated = ezShop.updateQuantity(1, 10);
            assertTrue(updated);
            updated = ezShop.updateQuantity(1, -1);
            assertTrue(updated);
            updated = ezShop.updateQuantity(1, -15);
            assertFalse(updated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePosition() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
            ezShop.login("Anna", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updatePosition(1, "1-a-1");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.updatePosition(1, "1-a-1");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidProductIdException.class, () -> {
            ezShop.updatePosition(null, "1-a-1");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidProductIdException.class, () -> {
            ezShop.updatePosition(-10, "1-a-1");
        });

        // ------------------------------------------------------------- //

        assertThrows(it.polito.ezshop.exceptions.InvalidLocationException.class, () -> {
            ezShop.updatePosition(1, "aaa");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidLocationException.class, () -> {
            ezShop.updatePosition(1, "aaa-b-c");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidLocationException.class, () -> {
            ezShop.updatePosition(1, "1-aaa-3c");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidLocationException.class, () -> {
            ezShop.updatePosition(1, "1ccc-ab2-3123");
        });

        // ------------------------------------------------------------- //

        boolean updated = false;
        try {
            ezShop.login("Giovanni", "password");
            ezShop.createProductType("descrizione", "3000000000083", 10.5, "nota");
            ezShop.createProductType("descrizione 2", "3000000000076", 15, "nota 2");
            updated = ezShop.updatePosition(10, "1-a-1");
            assertFalse(updated);
            updated = ezShop.updatePosition(1, "1-a-1");
            assertTrue(updated);
            updated = ezShop.updatePosition(2, "1-a-1");
            assertFalse(updated);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void defineCustomer() {
        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
            ezShop.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.defineCustomer("Marco");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerNameException.class, () -> {
            ezShop.defineCustomer(null);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerNameException.class, () -> {
            ezShop.defineCustomer("");
        });

        // ------------------------------------------------------------- //

        Integer customerId = -1;
        try {
            ezShop.login("Giovanni", "password");
            customerId = ezShop.defineCustomer("Vincenzo");
            assertTrue(customerId > 0);
            customerId = ezShop.defineCustomer("Giulio");
            assertTrue(customerId > 0);
            customerId = ezShop.defineCustomer("Vincenzo");
            assertEquals(-1, (int) customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void modifyCustomer() {

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.createUser("Anna", "password", "Cashier");
            ezShop.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.modifyCustomer(1, "Carlo", "1111111111");
        });

        // ------------------------------------------------------------- //

        try {
            ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerNameException.class, () -> {
            ezShop.modifyCustomer(1, null, "1111111111");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerNameException.class, () -> {
            ezShop.modifyCustomer(1, "", "1111111111");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerIdException.class, () -> {
            ezShop.modifyCustomer(null, "Carlo", "1111111111");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerIdException.class, () -> {
            ezShop.modifyCustomer(-10, "Carlo", "1111111111");
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerCardException.class, () -> {
            ezShop.modifyCustomer(1, "Carlo", "11111111");
        });

        // ------------------------------------------------------------- //

        boolean modified = false;
        try {
            ezShop.login("Giovanni", "password");
            ezShop.defineCustomer("Vincenzo");
            modified = ezShop.modifyCustomer(1, "Marco", "2222222222");
            assertTrue(modified);
            modified = ezShop.modifyCustomer(5, "Marco", "2222222222");
            assertFalse(modified);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCustomer() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteCustomer(1);
        });

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerIdException.class, () -> {
            ezShop.deleteCustomer(null);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerIdException.class, () -> {
            ezShop.deleteCustomer(-10);
        });

        // ------------------------------------------------------------- //

        boolean deleted = false;
        try {
            ezShop.login("Giovanni", "password");
            ezShop.defineCustomer("Vincenzo");
            deleted = ezShop.deleteCustomer(5);
            assertFalse(deleted);
            deleted = ezShop.deleteCustomer(1);
            assertTrue(deleted);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCustomer() {

        assertThrows(UnauthorizedException.class, () -> {
            ezShop.getCustomer(1);
        });

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(InvalidCustomerIdException.class, () -> {
            ezShop.getCustomer(null);
        });

        assertThrows(InvalidCustomerIdException.class, () -> {
            ezShop.getCustomer(-10);
        });

        // ------------------------------------------------------------- //

        Customer customer = null;
        try {
            ezShop.login("Giovanni", "password");
            ezShop.defineCustomer("Vincenzo");
            customer = ezShop.getCustomer(1);
            assertNotNull(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllCustomers() {
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getAllCustomers();
        });

        List<Customer> customers = null;

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.defineCustomer("Vincenzo");
            ezShop.defineCustomer("Carlo");
            customers = ezShop.getAllCustomers();
            assertNotNull(customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createCard() {
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.createCard();
        });

        String cardNumber = "";
        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            cardNumber = ezShop.createCard();
            assertTrue(cardNumber.length() == 10 && cardNumber.matches("([0-9]{10}|^$)"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void attachCardToCustomer() {
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.attachCardToCustomer("1111111111", 1);
        });

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerIdException.class, () -> {
            ezShop.attachCardToCustomer("1111111111", null);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerIdException.class, () -> {
            ezShop.attachCardToCustomer("1111111111", -10);
        });

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerCardException.class, () -> {
            ezShop.attachCardToCustomer("11111111", 1);

        });

        // ------------------------------------------------------------- //

        boolean cardAttached = false;

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.defineCustomer("Carlo");
            ezShop.defineCustomer("Giulio");
            cardAttached = ezShop.attachCardToCustomer("2222222222", 1);
            assertTrue(cardAttached);
            cardAttached = ezShop.attachCardToCustomer("2222222222", 1);
            assertFalse(cardAttached);
            cardAttached = ezShop.attachCardToCustomer("2222222222", 2);
            assertFalse(cardAttached);
            cardAttached = ezShop.attachCardToCustomer("3333333333", 5);
            assertFalse(cardAttached);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void modifyPointsOnCard() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.modifyPointsOnCard("1111111111", 1);
        });

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThrows(it.polito.ezshop.exceptions.InvalidCustomerCardException.class, () -> {
            ezShop.modifyPointsOnCard("11111111", 1);

        });

        // ------------------------------------------------------------- //

        boolean pointsUpdated = false;

        try {
            ezShop.createUser("Giovanni", "password", "Administrator");
            ezShop.login("Giovanni", "password");
            ezShop.defineCustomer("Carlo");
            ezShop.defineCustomer("Giulio");
            ezShop.attachCardToCustomer("2222222222", 1);
            pointsUpdated = ezShop.modifyPointsOnCard("2222222222", 10);
            assertTrue(pointsUpdated);
            pointsUpdated = ezShop.modifyPointsOnCard("2222222223", 10);
            assertFalse(pointsUpdated);
            pointsUpdated = ezShop.modifyPointsOnCard("2222222222", -5);
            assertTrue(pointsUpdated);
            pointsUpdated = ezShop.modifyPointsOnCard("2222222222", -10);
            assertFalse(pointsUpdated);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testStartSaleTransaction() {

        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.startSaleTransaction();
        });

        try {
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer id = -1;
        try {
            id = ezShop.startSaleTransaction();
        } catch (UnauthorizedException e) {
            e.printStackTrace();
        }
        assertTrue("StartSaleTransaction mismatch", id >= 0);
    }

    @Test
    public void testAddProductToSale() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.addProductToSale(tempId, productCode, 12);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.addProductToSale(null, productCode, 12);
        });

        // test InvalidProductCodeException
        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.addProductToSale(tempId, null, 12);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.addProductToSale(10, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("AddProductToSale mismatch", !resultOp);

        // test transactionId valid
        resultOp = false;
        try {
            resultOp = ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("AddProductToSale mismatch", resultOp);
    }

    @Test
    public void testDeleteProductFromSale() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        try {
            ezShop.addProductToSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteProductFromSale(tempId, productCode, 12);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.deleteProductFromSale(null, productCode, 12);
        });

        // test InvalidProductCodeException
        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.deleteProductFromSale(tempId, null, 12);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.deleteProductFromSale(10, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("DeleteProduct mismatch", !resultOp);

        resultOp = false;
        try {
            resultOp = ezShop.deleteProductFromSale(tempId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("DeleteProduct mismatch", resultOp);
    }

    @Test
    public void testApplyDiscountRateToProduct() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            // Add product to sale
            ezShop.addProductToSale(transactionId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.applyDiscountRateToProduct(tempId, productCode, 0.1);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.applyDiscountRateToProduct(null, productCode, 0.1);
        });

        // test InvalidProductCodeException
        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.applyDiscountRateToProduct(tempId, null, 0.1);
        });

        // test InvalidDiscountRateException
        assertThrows(it.polito.ezshop.exceptions.InvalidDiscountRateException.class, () -> {
            ezShop.applyDiscountRateToProduct(tempId, productCode, -1.0);
        });

        // test InvalidDiscountRateException
        assertThrows(it.polito.ezshop.exceptions.InvalidDiscountRateException.class, () -> {
            ezShop.applyDiscountRateToProduct(tempId, productCode, 1.0);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.applyDiscountRateToProduct(10, productCode, 0.1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("applyDiscountRateToProduct mismatch", !resultOp);

        resultOp = false;
        try {
            resultOp = ezShop.applyDiscountRateToProduct(tempId, productCode, 0.1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("applyDiscountRateToProduct mismatch", resultOp);
    }

    @Test
    public void testapplyDiscountRateToSale() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            // Add product to sale
            ezShop.addProductToSale(transactionId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.applyDiscountRateToSale(tempId, 0.1);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.applyDiscountRateToSale(null, 0.1);
        });

        // test InvalidDiscountRateException
        assertThrows(it.polito.ezshop.exceptions.InvalidDiscountRateException.class, () -> {
            ezShop.applyDiscountRateToSale(tempId, -1.0);
        });

        // test InvalidDiscountRateException
        assertThrows(it.polito.ezshop.exceptions.InvalidDiscountRateException.class, () -> {
            ezShop.applyDiscountRateToSale(tempId, 1.0);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.applyDiscountRateToSale(10, 0.1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("applyDiscountRateToSale mismatch", !resultOp);

        resultOp = false;
        try {
            resultOp = ezShop.applyDiscountRateToSale(tempId, 0.2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("applyDiscountRateToSale mismatch", resultOp);
    }

    @Test
    public void testComputePointsForSale() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            // Add product to sale
            ezShop.addProductToSale(transactionId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.computePointsForSale(tempId);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.computePointsForSale(null);
        });

        // test transactionId not valid
        int resultOp = 1;
        try {
            resultOp = ezShop.computePointsForSale(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(-1, resultOp);

        resultOp = -1;
        try {
            resultOp = ezShop.computePointsForSale(tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(14, resultOp);
    }

    @Test
    public void testEndSaleTransaction() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            // Add product to sale
            ezShop.addProductToSale(transactionId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.endSaleTransaction(tempId);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.endSaleTransaction(null);
        });

        // test transactionId valid
        boolean resultOp = false;
        try {
            resultOp = ezShop.endSaleTransaction(tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("endSaleTransaction mismatch", resultOp);

    }

    @Test
    public void testDeleteSaleTransaction() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            // Add product to sale
            ezShop.addProductToSale(transactionId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteSaleTransaction(tempId);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.deleteSaleTransaction(null);
        });

        // test transactionId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.deleteSaleTransaction(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("DeleteSaleTransaction mismatch", !resultOp);

        resultOp = false;
        try {
            resultOp = ezShop.deleteSaleTransaction(tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("DeleteSaleTransaction mismatch", resultOp);

    }

    @Test
    public void testGetSaleTransactio() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            // Add product to sale
            ezShop.addProductToSale(transactionId, productCode, 12);
            // End Sale transaction
            ezShop.endSaleTransaction(transactionId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.getSaleTransaction(tempId);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.getSaleTransaction(null);
        });

        // test transactionId not valid
        SaleTransaction resultOp = null;
        try {
            resultOp = ezShop.getSaleTransaction(Integer.valueOf(10));
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("getSaleTransaction mismatch", resultOp == null);

        try {
            resultOp = ezShop.getSaleTransaction(tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("getSaleTransaction mismatch", resultOp != null);
    }

    @Test
    public void testStartReturnTransaction() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;

        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            ezShop.addProductToSale(transactionId, productCode, 12);
            ezShop.endSaleTransaction(transactionId);
            // Pay transaction
            ezShop.receiveCashPayment(transactionId, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.startReturnTransaction(tempId);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.startReturnTransaction(null);
        });

        // test transactionId valid
        Integer resultOp = -1;
        try {
            resultOp = ezShop.startReturnTransaction(tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("<> mismatch", resultOp != -1);
    }

    @Test
    public void testReturnProduct() {

        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;
        Integer returnId = -1;
        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            ezShop.addProductToSale(transactionId, productCode, 12);
            ezShop.endSaleTransaction(transactionId);
            // Pay transaction
            ezShop.receiveCashPayment(transactionId, 1000);
            // Start Return product
            returnId = ezShop.startReturnTransaction(transactionId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Integer tempId = transactionId;
        final Integer tempRId = returnId;

        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.returnProduct(tempRId, productCode, 12);
        });

        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.returnProduct(null, productCode, 12);
        });

        // test InvalidProductCodeException
        assertThrows(it.polito.ezshop.exceptions.InvalidProductCodeException.class, () -> {
            ezShop.returnProduct(tempRId, null, 12);
        });

        // test InvalidQuantityException
        assertThrows(it.polito.ezshop.exceptions.InvalidQuantityException.class, () -> {
            ezShop.returnProduct(tempRId, productCode, -1);
        });

        // test returnId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.returnProduct(Integer.valueOf(10), productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("returnProduct mismatch", !resultOp);
        try {
            resultOp = ezShop.returnProduct(Integer.valueOf(10), productCode, 15);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("returnProduct mismatch", !resultOp);
        resultOp = false;
        try {
            resultOp = ezShop.returnProduct(tempRId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("returnProduct mismatch", resultOp);
    }

    @Test
    public void testEndReturnTransaction() {
        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;
        Integer returnId = -1;
        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            ezShop.addProductToSale(transactionId, productCode, 12);
            ezShop.endSaleTransaction(transactionId);
            // Pay transaction
            ezShop.receiveCashPayment(transactionId, 1000);
            // Start Return product
            returnId = ezShop.startReturnTransaction(transactionId);
            // Add product to return
            ezShop.returnProduct(returnId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        final Integer tempRId = returnId;
    
        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.endReturnTransaction(tempRId, true);
        });
    
        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    
        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.endReturnTransaction(null, true);
        });

        // test returnId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.endReturnTransaction(10, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("endReturnTransaction mismatch", !resultOp);
        resultOp = false;
        try {
            resultOp = ezShop.endReturnTransaction(tempRId, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("endReturnTransaction mismatch", resultOp);
        Integer quantity = -1;
        try {
            quantity = ezShop.getProductTypeByBarCode(productCode).getQuantity();
            // Start Return product
            returnId = ezShop.startReturnTransaction(transactionId);
            // Add product to return
            ezShop.returnProduct(returnId, productCode, 12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(Integer.valueOf(8), quantity);
        resultOp = false;
        try {
            resultOp = ezShop.endReturnTransaction(returnId, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("endReturnTransaction mismatch", resultOp);
        quantity = -1;
        try {
            quantity = ezShop.getProductTypeByBarCode(productCode).getQuantity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(Integer.valueOf(20), quantity);
    }

    @Test
    public void testDeleteReturnTransaction() {
        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;
        Integer returnId = -1;
        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            ezShop.addProductToSale(transactionId, productCode, 12);
            ezShop.endSaleTransaction(transactionId);
            // Pay transaction
            ezShop.receiveCashPayment(transactionId, 1000);
            // Start Return product
            returnId = ezShop.startReturnTransaction(transactionId);
            // Add product to return
            ezShop.returnProduct(returnId, productCode, 12);
            // End return
            ezShop.endReturnTransaction(returnId, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        final Integer tempRId = returnId;
    
        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.deleteReturnTransaction(tempRId);
        });
    
        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    
        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.deleteReturnTransaction(null);
        });
    
        // test returnId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.deleteReturnTransaction(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("deleteReturnTransaction mismatch", !resultOp);
        resultOp = false;
        try {
            resultOp = ezShop.deleteReturnTransaction(tempRId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("deleteReturnTransaction mismatch", resultOp);

        Integer quantity = -1;
        try {
            quantity = ezShop.getProductTypeByBarCode(productCode).getQuantity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(Integer.valueOf(8), quantity);
    }

    @Test
    public void testReceiveCashPayment() {
        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;
        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            ezShop.addProductToSale(transactionId, productCode, 12);
            ezShop.endSaleTransaction(transactionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        final Integer tempId = transactionId;
    
        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.receiveCashPayment(tempId, 200);
        });
    
        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    
        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.receiveCashPayment(null, 200.0);
        });

        // test InvalidPaymentException
        assertThrows(it.polito.ezshop.exceptions.InvalidPaymentException.class, () -> {
            ezShop.receiveCashPayment(tempId, 0.0);
        });
    
        // test returnId not valid
        double resultOp = -1.0;
        try {
            resultOp = ezShop.receiveCashPayment(10, 200.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(-1.0, resultOp, 0.01);
        resultOp = -1.0;
        try {
            resultOp = ezShop.receiveCashPayment(tempId, 200.0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(56.0, resultOp, 0.01);
    }

    @Test
    public void testReceiveCreditCardPayment() {
        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;
        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            ezShop.addProductToSale(transactionId, productCode, 12);
            ezShop.endSaleTransaction(transactionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        final Integer tempId = transactionId;
    
        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.receiveCreditCardPayment(tempId, "4030270951989685");
        });
    
        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    
        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.receiveCreditCardPayment(null, "4030270951989685");
        });

        // test InvalidCreditCardException
        assertThrows(it.polito.ezshop.exceptions.InvalidCreditCardException.class, () -> {
            ezShop.receiveCreditCardPayment(tempId, "");
        });
    
        // test returnId not valid
        boolean resultOp = true;
        try {
            resultOp = ezShop.receiveCreditCardPayment(10, "4030270951989685");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("receiveCreditCardPayment not valid mismatch", !resultOp);
        resultOp = false;
        try {
            resultOp = ezShop.receiveCreditCardPayment(tempId, "4030270951989685");;
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue("receiveCreditCardPayment valid mismatch", resultOp);
    }

    @Test
    public void testReturnCashPayment() {
        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;
        Integer returnId = -1;
        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            ezShop.addProductToSale(transactionId, productCode, 12);
            ezShop.endSaleTransaction(transactionId);
            // Pay transaction
            ezShop.receiveCashPayment(transactionId, 1000);
            // Start Return product
            returnId = ezShop.startReturnTransaction(transactionId);
            // Add product to return
            ezShop.returnProduct(returnId, productCode, 12);
            // End return
            ezShop.endReturnTransaction(returnId, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        final Integer tempRId = returnId;
    
        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.returnCashPayment(tempRId);
        });
    
        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    
        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.returnCashPayment(null);
        });
    
        // test returnId not valid
        double resultOp = 0.0;
        try {
            resultOp = ezShop.returnCashPayment(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(-1.0, resultOp, 0.01);
        resultOp = -1.0;
        try {
            resultOp = ezShop.returnCashPayment(tempRId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(144.0, resultOp, 0.01);
    }

    @Test
    public void testReturnCreditCardPayment() {
        String productCode = "3000000000076";
        Integer productId = -1;
        Integer transactionId = -1;
        Integer returnId = -1;
        try {
            // start saleTransaction
            ezShop.createUser("Marco", "CppSpaccaMaNoiUsiamoJava", "Administrator");
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
            // Create Product
            productId = ezShop.createProductType("Product test", productCode, 12.0, "None");
            ezShop.updatePosition(productId, "0-a-0");
            ezShop.updateQuantity(productId, 20);
            // Create SaleTransaction
            transactionId = ezShop.startSaleTransaction();
            ezShop.addProductToSale(transactionId, productCode, 12);
            ezShop.endSaleTransaction(transactionId);
            // Pay transaction
            ezShop.receiveCashPayment(transactionId, 1000);
            // Start Return product
            returnId = ezShop.startReturnTransaction(transactionId);
            // Add product to return
            ezShop.returnProduct(returnId, productCode, 12);
            // End return
            ezShop.endReturnTransaction(returnId, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        final Integer tempRId = returnId;
    
        // test UnauthorizedException
        ezShop.logout();
        assertThrows(it.polito.ezshop.exceptions.UnauthorizedException.class, () -> {
            ezShop.returnCreditCardPayment(tempRId, "4030270951989685");
        });
    
        try {
            ezShop.login("Marco", "CppSpaccaMaNoiUsiamoJava");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    
        // test InvalidTransactionIdException
        assertThrows(it.polito.ezshop.exceptions.InvalidTransactionIdException.class, () -> {
            ezShop.returnCreditCardPayment(null, "4030270951989685");
        });
    
        // test InvalidCreditCardException
        assertThrows(it.polito.ezshop.exceptions.InvalidCreditCardException.class, () -> {
            ezShop.returnCreditCardPayment(tempRId, "");
        });
    
        // test returnId not valid
        double resultOp = 0.0;
        try {
            resultOp = ezShop.returnCreditCardPayment(10, "4030270951989685");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(-1.0, resultOp, 0.01);
        resultOp = -1.0;
        try {
            resultOp = ezShop.returnCreditCardPayment(tempRId, "4030270951989685");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(144.0, resultOp, 0.01);
    }

    private void createw(String username, String passw, String role) {
        try {
            ezShop.createUser(username, passw, role);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private void loginw(String username, String passw) {
        try {
            ezShop.login(username, passw);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testIssueOrder() {
        String empty = "";
        String nullCode = null;
        String shortCode = "12345";
        String longCode = "123456890123456";
        String alnumCode = "1234567ab0123";
        String invalidCode = "300000000007";
        String validCode = "300000000001";

        Integer negativeQuantity = -10;
        Integer quantity = 10;
        Integer zeroQuantity = 0;
        Integer result;

        Double positivePPU = 420.69;
        Double negativePPU = -positivePPU;
        Double zeroPPU = 0.0;

        Order order;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        ezShop.logout();

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, quantity, positivePPU));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, quantity, positivePPU));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        /* Product Code */
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(shortCode, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(longCode, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(invalidCode, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(alnumCode, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(empty, quantity, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(nullCode, quantity, positivePPU));

        /* Quantity */
        assertThrows(InvalidQuantityException.class, () -> ezShop.issueOrder(validCode, negativeQuantity, positivePPU));
        assertThrows(InvalidQuantityException.class, () -> ezShop.issueOrder(validCode, zeroQuantity, positivePPU));

        /* Price per Unit */
        assertThrows(InvalidPricePerUnitException.class, () -> ezShop.issueOrder(validCode, quantity, negativePPU));
        assertThrows(InvalidPricePerUnitException.class, () -> ezShop.issueOrder(validCode, quantity, zeroPPU));

        /* Function Body */

        try {
            result = ezShop.issueOrder(validCode, quantity, positivePPU);
            assertTrue(result == -1); // Product does not exists

            ezShop.createProductType("Descr", validCode, positivePPU, "Integration Test");
            result = ezShop.issueOrder(validCode, quantity, positivePPU);
            assertTrue(result > 0);

            order = EZShopDBManager.getInstance().loadOrder(result);
            assertTrue(order != null);

            assertTrue(order.getOrderId() > 0);
            assertEquals(positivePPU, order.getPricePerUnit(), 0.001);
            assertEquals(quantity.intValue(), order.getQuantity());
            assertTrue(order.getProductCode().equals(validCode));
            assertTrue(order.getStatus().equals("ISSUED"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testPayOrderFor() {
        String empty = "";
        String nullCode = null;
        String shortCode = "12345";
        String longCode = "123456890123456";
        String alnumCode = "1234567ab0123";
        String invalidCode = "300000000007";
        String validCode = "300000000001";

        Integer minusOne = -1;
        Integer one = 1;
        Integer zero = 0;
        Integer result;

        Double positivePPU = 420.69;
        Double negativePPU = -positivePPU;
        Double zeroPPU = 0.0;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        ezShop.logout();

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, one, positivePPU));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, one, positivePPU));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        /* Product Code */
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(shortCode, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(longCode, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(invalidCode, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(alnumCode, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(empty, one, positivePPU));
        assertThrows(InvalidProductCodeException.class, () -> ezShop.issueOrder(nullCode, one, positivePPU));

        /* Quantity */
        assertThrows(InvalidQuantityException.class, () -> ezShop.issueOrder(validCode, minusOne, positivePPU));
        assertThrows(InvalidQuantityException.class, () -> ezShop.issueOrder(validCode, zero, positivePPU));

        /* Price per Unit */
        assertThrows(InvalidPricePerUnitException.class, () -> ezShop.issueOrder(validCode, one, negativePPU));
        assertThrows(InvalidPricePerUnitException.class, () -> ezShop.issueOrder(validCode, one, zeroPPU));

        try {
            result = ezShop.payOrderFor(validCode, one, positivePPU);
            assertTrue(result == -1); // Product does not exist
            
            ezShop.recordBalanceUpdate(3 * one * positivePPU);
            ezShop.createProductType("Descr", validCode, positivePPU, "Integration Test");
            result = ezShop.payOrderFor(validCode, one, positivePPU);
            assertTrue(result > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testPayOrder() {

        Integer negativeOrderID = -10;
        Integer validOrderID;
        Integer one = 1;
        Integer zero = 0;

        String validCode = "300000000001";

        Double positivePPU = 420.69;
        Double balanceToAdd = Double.MAX_VALUE;
        Double remainder;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        ezShop.logout();

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, one, positivePPU));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, one, positivePPU));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        try {
            ezShop.createProductType("Test Integration", validCode, positivePPU, "Test");
            validOrderID = ezShop.issueOrder(validCode, one, positivePPU);
            ezShop.recordBalanceUpdate(balanceToAdd);

            assertThrows(InvalidOrderIdException.class, () -> ezShop.payOrder(negativeOrderID));
            assertThrows(InvalidOrderIdException.class, () -> ezShop.payOrder(zero));

            
            assertTrue(ezShop.payOrder(validOrderID));
            
            remainder = balanceToAdd - one * positivePPU;
            assertEquals(remainder, ezShop.computeBalance(), 0.01);

            assertThrows(InvalidOrderIdException.class, () -> ezShop.payOrder(null));
            ezShop.logout();
            assertThrows(UnauthorizedException.class, () -> ezShop.payOrder(1));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testRecordOrderArrival() {
        Integer orderID;
        Integer negativeOrderID = -1;
        Integer zeroOrderID = 0;
        Integer quantity = 1;
        Integer productID;

        String emptyLocation = "";
        String nullLocation = null;
        String invalidLocation = "6-6-6";
        String validLocation = "0-A-0";
        String validCode = "300000000001";

        Double positivePPU = 420.69;
        Double balanceToAdd = Double.MAX_VALUE;

        ProductType product;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        ezShop.logout();

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, quantity, positivePPU));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.issueOrder(validCode, quantity, positivePPU));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        try {
            productID = ezShop.createProductType("Test Integration", validCode, positivePPU, "Test");
            orderID = ezShop.issueOrder(validCode, quantity, positivePPU);
            ezShop.recordBalanceUpdate(balanceToAdd);

            assertThrows(InvalidOrderIdException.class, () -> ezShop.recordOrderArrival(negativeOrderID));
            assertThrows(InvalidOrderIdException.class, () -> ezShop.recordOrderArrival(zeroOrderID));

            product = EZShopDBManager.getInstance().loadProduct(productID);

            product.setLocation(emptyLocation);
            EZShopDBManager.getInstance().updateProduct(product);
            assertThrows(InvalidLocationException.class, () -> ezShop.recordOrderArrival(orderID));

            product.setLocation(nullLocation);
            EZShopDBManager.getInstance().updateProduct(product);
            assertThrows(InvalidLocationException.class, () -> ezShop.recordOrderArrival(orderID));

            product.setLocation(invalidLocation);
            EZShopDBManager.getInstance().updateProduct(product);
            assertThrows(InvalidLocationException.class, () -> ezShop.recordOrderArrival(orderID));

            product.setLocation(validLocation);
            EZShopDBManager.getInstance().updateProduct(product);

            assertTrue(ezShop.recordOrderArrival(orderID) == false);
            ezShop.payOrder(orderID);
            assertTrue(ezShop.recordOrderArrival(orderID));
            product = EZShopDBManager.getInstance().loadProduct(productID);
            assertEquals(product.getQuantity(), quantity);

            assertThrows(InvalidOrderIdException.class, () -> ezShop.recordOrderArrival(null));
            ezShop.logout();
            assertThrows(UnauthorizedException.class, () -> ezShop.recordOrderArrival(1));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testRecordBUAndCB() {

        Double positiveBalance = 100.0;
        Double negativeBalance = -positiveBalance;
        Double expectedBalance = 0.0;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        assertTrue(ezShop.logout());

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.recordBalanceUpdate(positiveBalance));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.recordBalanceUpdate(positiveBalance));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        try {
            assertTrue(ezShop.recordBalanceUpdate(negativeBalance) == false);
            assertEquals(expectedBalance.doubleValue(), ezShop.computeBalance(), 0.001);

            assertTrue(ezShop.recordBalanceUpdate(positiveBalance));
            assertTrue(ezShop.recordBalanceUpdate(negativeBalance));
            assertEquals(expectedBalance.doubleValue(), ezShop.computeBalance(), 0.001);

            assertTrue(ezShop.recordBalanceUpdate(positiveBalance));
            assertTrue(ezShop.recordBalanceUpdate(positiveBalance));
            assertTrue(ezShop.recordBalanceUpdate(positiveBalance));
            expectedBalance = 3 * positiveBalance;
            assertEquals(expectedBalance.doubleValue(), ezShop.computeBalance(), 0.001);
            expectedBalance += negativeBalance;
            assertTrue(ezShop.recordBalanceUpdate(negativeBalance));
            assertEquals(expectedBalance.doubleValue(), ezShop.computeBalance(), 0.001);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private boolean isDebit(BalanceOperation bo) {
        return bo.getType().equals("DEBIT");
    }

    private boolean isCredit(BalanceOperation bo) {
        return bo.getType().equals("CREDIT");
    }

    @Test
    public void testGetDebitsCreditsAndCB() {
        /*
         * Premessa: le API non hanno un modo per impostare esplicitamente la data,
         * quindi non so come eseguire i test sui range
         */

        Double positiveBalance = 100.0;
        Double negativeBalance = -positiveBalance;
        Double expectedBalance = 0.0;

        Long debits;
        Long credits;

        List<BalanceOperation> debitsAndCredits = null;

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");
        createw("Cashier", "Password", "Cashier");
        assertTrue(ezShop.logout());

        /* User */
        assertThrows(UnauthorizedException.class, () -> ezShop.getCreditsAndDebits(null, null));
        loginw("Cashier", "Password");

        assertThrows(UnauthorizedException.class, () -> ezShop.getCreditsAndDebits(null, null));

        assertTrue(ezShop.logout());
        loginw("ShopManager", "Password");

        try {
            ezShop.recordBalanceUpdate(negativeBalance);
            debitsAndCredits = ezShop.getCreditsAndDebits(null, null);
            assertEquals(0, debitsAndCredits.size());
            assertEquals(expectedBalance, ezShop.computeBalance(), 0.001);

            ezShop.recordBalanceUpdate(positiveBalance);
            ezShop.recordBalanceUpdate(positiveBalance);
            ezShop.recordBalanceUpdate(positiveBalance);
            ezShop.recordBalanceUpdate(negativeBalance);
            expectedBalance = 3 * positiveBalance + negativeBalance;
            debitsAndCredits = ezShop.getCreditsAndDebits(null, null);
            debits = debitsAndCredits.stream().filter(this::isDebit).count();
            credits = debitsAndCredits.stream().filter(this::isCredit).count();

            assertEquals(4, debitsAndCredits.size());
            assertEquals(expectedBalance, ezShop.computeBalance(), 0.001);
            assertEquals(3, credits.longValue());
            assertEquals(1, debits.longValue());

            ezShop.recordBalanceUpdate(positiveBalance);
            ezShop.recordBalanceUpdate(negativeBalance);
            debitsAndCredits = ezShop.getCreditsAndDebits(null, null);
            debits = debitsAndCredits.stream().filter(this::isDebit).count();
            credits = debitsAndCredits.stream().filter(this::isCredit).count();

            assertEquals(6, debitsAndCredits.size());
            assertEquals(expectedBalance, ezShop.computeBalance(), 0.001);
            assertEquals(4, credits.longValue());
            assertEquals(2, debits.longValue());

            assertThrows(InvalidOrderIdException.class, () -> ezShop.payOrder(null));
            ezShop.logout();
            assertThrows(UnauthorizedException.class, () -> ezShop.payOrder(1));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testValidBarCode() {
        String[] validBarCodes = { "3000000000083", "3000000000076", "3000000580073", "8500000580073", "8928498284264",
                "29293947954740", "92898971949586", "5945749748286", "28957949539596" };
        String[] invalidBarCodes = { "11515175855455758", "4841784284188", "82529148548268", "8488485581161",
                "516164156169", "18748959596150" };
        Boolean res;

        res = Arrays.stream(validBarCodes).allMatch(bc -> EZShop.validBarCode(bc));
        assertTrue(res);
        res = Arrays.stream(invalidBarCodes).noneMatch(bc -> EZShop.validBarCode(bc));
        assertTrue(res);
    }

    @Test
    public void testBODates(){
        Double fakeAmount = 100.0;
        LocalDate now, then;
        List<BalanceOperation> bos = null;
        Long length;

        now = LocalDate.of(1990, 01, 01);
        then = LocalDate.of(2030, 01, 01);

        createw("ShopManager", "Password", "ShopManager");
        loginw("ShopManager", "Password");

        try {
            ezShop.recordBalanceUpdate(fakeAmount);
            ezShop.recordBalanceUpdate(fakeAmount);
            ezShop.recordBalanceUpdate(fakeAmount);
            bos = ezShop.getCreditsAndDebits(then, now);
        } catch (Exception e){
            System.out.println(e.getClass());
            e.printStackTrace();
            fail();
        }

        length = bos.stream().count();
        assertEquals(length.longValue(), 3);
    }
}
