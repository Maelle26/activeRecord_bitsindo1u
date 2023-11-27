import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class Personne {

    //attributs
    protected int id;
    protected String nom;
    protected String prenom;

    //constructeur (id par defaut)
    public Personne (String n, String p){
        this.nom = n;
        this.prenom = p;
        this.id = -1;
    }

    //constructeur (id de la table)
    public Personne(String n, String p, int indx){
        this.nom = n;
        this.prenom = p;
        this.id = indx;
    }

    /**
     * Méthode findAll qui retourne l'ensemble des tuples de la table personne
     * sous forme d'objets
     * @return la liste des tuples d'une personne
     */
    public static ArrayList<Personne> findAll() throws SQLException {
        Connection connect = DBConnection.getConnection();
        ArrayList<Personne> liste = new ArrayList<Personne>();
        String SQLPrep = "SELECT * FROM PERSONNE;";
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.execute();
        ResultSet rs = prep.getResultSet();
        while (rs.next()){
            String nom = rs.getString("nom");
            String pren = rs.getString("prenom");
            int id = rs.getInt("id");
            liste.add(new Personne(nom,pren,id));
        }
        return liste;

    }

    /**
     * Méthode findById qui retourne l'objet Personne correspondant au tuple avec l'id
     * passé en paramètre (null si l'objet n'existe pas)
     * @return la personne avec l'id passé en paramètre
     */
    public static Personne findById(int i) throws SQLException {    //A VERIFIER
        Connection connect = DBConnection.getConnection();
        //Personne p = new Personne(null,null);
        String SQLPrep = "SELECT * FROM PERSONNE WHERE id = ?;";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, i);
        prep.execute();
        ResultSet rs = prep.getResultSet();
        rs.next(); //aller a la premiere ligne
        String nom = rs.getString("nom");
        String pren = rs.getString("prenom");
        int id = rs.getInt("id");

        return new Personne(nom,pren,id);

    }

    /**
     * Méthode findByName qui retourne la liste des objets Personne correspondant
     * aux tuples dont le nom est passé en paramètre
     * @return la liste des personne avec le meme nom
     */
    public static ArrayList<Personne> findByName(String n) throws SQLException { //A VERIFIER
        Connection connect = DBConnection.getConnection();
        ArrayList<Personne> liste = new ArrayList<Personne>();
        String SQLPrep = "SELECT * FROM PERSONNE WHERE nom = ?;";
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setString(1, n);
        prep.execute();
        ResultSet rs = prep.getResultSet();
        while (rs.next()){
            String nom = rs.getString("nom");
            String pren = rs.getString("prenom");
            int id = rs.getInt("id");
            liste.add(new Personne(nom,pren,id));
        }
        return liste;

    }
    /**
     * Méthode toString
     */
    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    //GETTERS

    public String getNom(){ //qu'est ce que le type "short" ?
        return this.nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getId() {
        return id;
    }

    /**
     * Méthode equals qui compare deux objets Personne et renvoie vrai si ils sont egaux
     * @param o l'objet avec lequel on compare
     * @return vrai si les 2 objets sont egaux
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return id == personne.id && Objects.equals(nom, personne.nom) && Objects.equals(prenom, personne.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom);
    }

    /**
     * Méthode createTable() qui crée la table personne dans la base de donnes testpersonne
     */
    public static void createTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, "
                + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
        System.out.println("La table Personne est crée !\n");
    }

    /**
     * Méthode deleteTable() qui supprime la table personne dans la base de donnees testpersonne
     */
    public static void deleteTable() throws SQLException {
        Connection connect = DBConnection.getConnection();
        String drop = "DROP TABLE Personne";
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
        System.out.println("La table Personne est supprimée !");
    }

    /**
     * Méthode save() qui ajoute le tuple correspondant à l'objet personne this dans la base
     */
    public void save(){
        //A FAIRE
    }
}

