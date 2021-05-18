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
        Integer id1 = 1;
        Integer id2 = -1;
        Integer id3 = 0;
        user.setId(id1);
        assertEquals(id1, user.getId());
        user.setId(id2);
        assertEquals(id1, user.getId());
        user.setId(id3);
        assertEquals(id1, user.getId());
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