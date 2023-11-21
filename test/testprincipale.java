import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class testprincipale {

    //verifie qu'il n'existe qu'un seul objet  connexion
    @Test
    public void test_verifConnexion_ok() throws SQLException {
        Connection c1 = DBConnection.getConnection();
        Connection c2 = DBConnection.getConnection();
        assertEquals(c1,c2);
    }

    //verifie que le changement de base fonctionne
    @Test
    public void test_setDB () throws SQLException {
        DBConnection.setNomDB("testpersonne");
        assertEquals("testpersonne",DBConnection.dbName);
        DBConnection.setNomDB("null");
        assertEquals("null",DBConnection.dbName);

    }
}
