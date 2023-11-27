import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class testprincipale {

    //verifie qu'il n'existe qu'un seul objet connexion FONCTIONNE PAS
    @Test
    public void test_verifConnexion_ok() throws SQLException {
        Connection connect = DBConnection.getConnection();
        Connection connect2 = DBConnection.getConnection();
        assertEquals(connect2,connect2);
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
