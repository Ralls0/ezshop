package it.polito.ezshop.acceptanceTests;

import it.polito.ezshop.data.EZUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEZUser {

    EZUser user;
    @Before
    public void setUp() {
        user = new EZUser(0, "Test Username", "Test Password", "Test Role");
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void setId() {
        Integer id = 1;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void setUsername() {
        String username = "Mario Rossi";

        user.setUsername(username);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void setPassword() {
        String password = "1234";

        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    public void setRole() {
        String role = "Cashier";

        user.setRole(role);
        assertEquals(role, user.getRole());
    }
}