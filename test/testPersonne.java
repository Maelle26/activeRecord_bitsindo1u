import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class testPersonne {

    @BeforeEach
    public void creerDonnes() throws SQLException {
        //creation de la table
        Personne.createTable();

        //Ajouts des peronnes
        new Personne("Spielberg", "Steven",1);
        new Personne("Scott", "Ridley",2);
        new Personne("Kubrick", "Stanley",3);
        new Personne("Spielberg", "George",4);

    }
    //teste quand la liste est correcte
    @Test
    public void test_findAll_OK() throws SQLException {
        // Liste correcte
        ArrayList<Personne> listeCorrecte = new ArrayList<>();
        listeCorrecte.add(new Personne("Spielberg", "Steven", 1));
        listeCorrecte.add(new Personne("Scott", "Ridley", 2));
        listeCorrecte.add(new Personne("Kubrick", "Stanley", 3));
        listeCorrecte.add(new Personne("Fincher", "David", 4));

        // Liste obtenue
        ArrayList<Personne> listeTestee = Personne.findAll();

        // Vérification des tailles de listes
        assertEquals(listeCorrecte.size(), listeTestee.size());

        // Vérification du contenu des listes
        for (int i = 0; i < listeCorrecte.size(); i++) {
            Personne personneCorrecte = listeCorrecte.get(i);
            Personne personneTestee = listeTestee.get(i);
            assertEquals(personneCorrecte.getId(), personneTestee.getId());
            assertEquals(personneCorrecte.getNom(), personneTestee.getNom());
            assertEquals(personneCorrecte.getPrenom(), personneTestee.getPrenom());
        }
    }

    //teste quand l'id existe
    @Test
    public void test_findById_OK() throws SQLException {
        // Liste obtenue
        Personne personneAttendue = new Personne("Kubrick","Stanley",3);

        Personne personneTrouvee = Personne.findById(3);

        // Vérification
        assertTrue(personneAttendue.equals(personneTrouvee));
    }

    //teste quand le nom existe
    @Test
    public void test_findByName_OK() throws SQLException {

        //liste attendue
        ArrayList<Personne> listeCorrecte = new ArrayList<>();
        listeCorrecte.add(new Personne("Kubrick", "Ridley", 2));

        // Liste obtenue
        ArrayList<Personne> listeTestee = Personne.findByName("Kubrick");

        // Vérification des tailles de listes
        assertEquals(listeCorrecte.size(), listeTestee.size());

        // Vérification du contenu des listes
        for (int i = 0; i < listeCorrecte.size(); i++) {
            Personne personneCorrecte = listeCorrecte.get(i);
            Personne personneTestee = listeTestee.get(i);
            assertEquals(personneCorrecte.getNom(), personneTestee.getNom());
        }
    }

    //teste si 2 noms existent
    @Test
    public void testFindByName_2_Existe() throws SQLException {
        ArrayList<Personne> p = Personne.findByName("Spielberg");

        Assertions.assertEquals(2, p.size(),"deux reponses dans personne");
        Personne pers = p.get(0);
        Assertions.assertEquals(pers.getNom(), "Spielberg");
        pers = p.get(1);
        Assertions.assertEquals(pers.getNom(), "Spielberg");
    }

    //teste quand le nom n'existe pas
    @Test
    public void testFindByNameNon() throws SQLException {
        ArrayList<Personne> p = Personne.findByName("ee");
        Assertions.assertEquals(0, p.size(),"pas de reponse");
    }

}
