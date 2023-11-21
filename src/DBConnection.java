import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    //attributs
    private static Connection connect;
    protected String userName;
    protected String password;
    protected String serverName;
    protected String portNumber;
    protected static String dbName;
    protected String tableName;

    //constructeur
    private DBConnection() throws SQLException {
        this.dbName = "testpersonne";
        this.password = "";
        this.serverName = "localhost";
        this.userName = "root";
        String portNumber = "3306";
        String tableName = "personne";

        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        String urlDB = "jdbc:mysql://" + serverName + ":";
        urlDB += portNumber + "/" + dbName;
        connect = DriverManager.getConnection(urlDB, connectionProps);

    }
    /**
     * Méthode getConnection() permettant de se connecter à la base de données
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connect == null)
            new DBConnection();
        return connect;
    }

    /**
     * Méthode setNomDB(String) permettant de changer le nom de la base demandée
     * @param nameDb nom de la base de donnée à laquelle on veut se connecter
     */
    public static synchronized void setNomDB (String nameDb) throws SQLException {
        if (nameDb != null && dbName != nameDb){
            dbName = nameDb;
            connect = null;
        }
    }

}
