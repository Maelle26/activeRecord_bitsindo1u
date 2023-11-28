import java.sql.*;
import java.util.ArrayList;

public class Film {

    //attributs
    protected String titre; //titre du film
    protected int id; //id du film
    protected static int id_real; //id du réalisteur

    //constructeur 1
    public Film (String title, Personne pReal){
        this.id = -1;
        this.titre = title;
        pReal = new Personne (pReal.nom, pReal.prenom, id_real);
    }

    //constructeur 2
    Film(String title, int id_rea, int id_movie){
        this.titre= title;
        this.id_real = id_rea;
        this.id = id_movie;
    }

    /**
     * Méthode findById qui retourne l'objet Film correpondant au tuple avec l'id passé en param
     */
    public static Film findById(int i) throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM FILM WHERE id = ?;";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, i);
        ResultSet rs = prep.executeQuery();

        if (rs.next()) {
            String titre = rs.getString("titre");
            int id_real = rs.getInt("id_rea");
            int id_film = rs.getInt("id");
            return new Film(titre, id_real, id_film);
        } else {
            // Quand il n'y a pas de resultat
            return null;
        }
    }


    /**
     * Méthode getRealisatuer() qui retourne l'objet Personne correspondant au réalisateur du film
     */
    public static Personne getRealisateur() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "SELECT * FROM FILM F JOIN PERSONNE P ON F.id_rea = P.id WHERE id_rea = ?;";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, Film.id_real);
        prep.execute();
        ResultSet rs = prep.getResultSet();
        rs.next(); //aller a la premiere ligne
        String nom = rs.getString("nom");
        String pren = rs.getString("prenom");

        return new Personne(nom, pren,id_real);
    }

    /**
     * Méthode createTable qui crée al table Film
     */
    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String createString = "CREATE TABLE `Film` (\n" +
                "  `id` int(11) NOT NULL,\n" +
                "  `titre` varchar(40) NOT NULL,\n" +
                "  `id_rea` int(11) DEFAULT NULL\n" +
                ")";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
        System.out.println("La table Film est crée !\n");
    }

    /**
     * Méthode deleteTable() qui supprime la table Film
     */
    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Film";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
        System.out.println("La table Film est supprimée !");
    }

    //GETTER

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public static int getId_real() {
        return id_real;
    }

    //Méthode toString
    @Override
    public String toString() {
        return "Film{" +
                "titre='" + titre + '\'' +
                ", id=" + id +
                ", id réalisateur=" + id_real +
                '}';
    }

    /**
     * Méthode saveNew() pour ajouter un film dans la table
     */
    private void saveNew() throws SQLException, RealisateurAbsentException {
        if (id_real == -1) {
            throw new RealisateurAbsentException("Le réalisateur est absent pour ce film");
        }
        Connection connect = DBConnection.getConnection();
        String SQLPrep = "INSERT INTO Film (titre,id_real) VALUES (?,?);";
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setInt(1, id_real);
        prep.setString(2, titre);
        prep.executeUpdate();

        // recuperation de la derniere ligne ajoutee (auto increment)
        // recupere le nouvel id
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            this.id = rs.getInt(1);
        }
    }

    /**
     * Méthode update() qui met à jour le tuple existant
     */
    public void update() throws SQLException, RealisateurAbsentException{
        if (id_real == -1) {
            throw new RealisateurAbsentException("Le réalisateur est absent pour ce film");
        }
        Connection connect = DBConnection.getConnection();
        String SQLprep = "update Film set titre=?, id_rea=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        Statement stmt = connect.createStatement();
        prep = connect.prepareStatement(SQLprep);
        prep.setString(1, titre);
        prep.setInt(2, id_real);
        prep.setInt(3,id);
        prep.execute();
        System.out.println("Le film a été mis à jour.");
    }

    /**
     * Méthode delete qui supprime la personne sur laquelle la méthode est appelée
     */
    public void delete() throws SQLException {
        Connection connect = DBConnection.getConnection();
        PreparedStatement prep = connect.prepareStatement("DELETE FROM Film WHERE id=?");
        prep.setInt(1, id);
        //on remet l'id à -1 car la personne n'existe plus
        id = -1;
        prep.execute();
        System.out.println(" Le film "+ titre + " "+ "a été supprimé avec succès.");
    }

    /**
     * Méthode findByRealisateur(Personne p) qui retourne l'ensemble des films réalisé par la personne
     * passée en param
     */
    public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException {
        Connection connect = DBConnection.getConnection();
        ArrayList<Film> liste = new ArrayList<Film>();
        String SQLPrep = "SELECT * FROM FILM F JOIN PERSONNE P ON F.id_rea = P.id WHERE F.id_rea = ?;";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, p.id);
        prep.execute();
        ResultSet rs = prep.getResultSet();
        while (rs.next()){
            int id = rs.getInt("id");
            String title = rs.getString("titre");
            int id_re = rs.getInt("id_rea");
            liste.add(new Film(title,p));
        }
        return liste;
    }
}
