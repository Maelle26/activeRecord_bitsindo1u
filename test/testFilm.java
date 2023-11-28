import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class testFilm {

    @BeforeEach
    public void creerDonnes () throws SQLException {
        // lien vers la base de test
        Personne.createTable();
        Film.createTable();

        // creation des personnes
        Personne spielberg = new Personne("Spielberg", "Steven");
        spielberg.save(); // id 1
        Personne scott = new Personne("Scott", "Ridley");
        scott.save(); // id 2
        Personne kubrick = new Personne("Kubrick", "Stanley");
        kubrick.save(); // id 3
        Personne fincher = new Personne("Fincher", "David");
        fincher.save(); // id 4

        // creation des films
        new Film("Arche perdue", spielberg).save(); // 1
        new Film("Alien", scott).save(); // 2
        new Film("Temple Maudit", spielberg).save(); // 3
        new Film("Blade Runner", scott).save(); // 4
        new Film("Alien3", fincher).save(); // 5
        new Film("Fight Club", fincher).save(); // 6
        new Film("Orange Mecanique", kubrick).save(); // 7
    }

    @Test
    /**
     * test constructeur de film
     */
    public void testConstructFilm() {
        Personne spielberg = new Personne("Spielberg", "Steven");
        Film f = new Film("derniere croisade", spielberg);
        assertEquals(f.getId(), -1);
    }

    @Test
    /**
     * test de recherche par id
     * @throws SQLException
     */
    public void testfindById() throws SQLException {
        Film f = Film.findById(1);
        assertEquals("Arche perdue", f.getTitre());
        assertEquals("Spielberg", f.getRealisateur().getNom());
    }

    @Test
    /**
     * deuxieme test de recherche par id
     * @throws SQLException
     */
    public void testfindByIdBis() throws SQLException {
        Film f = Film.findById(5);
        assertEquals("Alien3", f.getTitre());
        assertEquals("Fincher", f.getRealisateur().getNom());
    }

    @Test
    /**
     * deuxieme test de recherche id inexistant
     * @throws SQLException
     */
    public void testfindByIdInexistant() throws SQLException {
        Film f = Film.findById(8);
        Film res = null;
        assertEquals(res, f);
    }

    @AfterEach
    public void supprimerDonnees() throws SQLException {

        //suppression des realisateur
        Personne spielberg = new Personne("Spielberg", "Steven");
        spielberg.delete();
        Personne scott = new Personne("Scott", "Ridley");
        scott.delete();
        Personne kubrick = new Personne("Kubrick", "Stanley");
        kubrick.delete();
        Personne fincher = new Personne("Fincher", "David");
        fincher.delete();

        //suppression des films de la table
        new Film("Arche perdue", spielberg).delete(); // 1
        new Film("Alien", scott).delete(); // 2
        new Film("Temple Maudit", spielberg).delete(); // 3
        new Film("Blade Runner", scott).delete(); // 4
        new Film("Alien3", fincher).delete(); // 5
        new Film("Fight Club", fincher).delete(); // 6
        new Film("Orange Mecanique", kubrick).delete(); // 7

        //suppresion de la table
        Film.deleteTable();
    }

}
