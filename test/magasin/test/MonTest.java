/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magasin.test;

import java.awt.AWTEventMulticaster;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import magasin.entity.Categorie;
import magasin.entity.Client;
import magasin.entity.Commande;
import magasin.entity.Produit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author tom
 */
public class MonTest {

    @Before
    public void avant() {
        // Vide toutes les tables
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("DELETE FROM Produit p");
        query.executeUpdate();

        em.createQuery("DELETE FROM Categorie p").executeUpdate();
        em.createQuery("DELETE FROM Commande c").executeUpdate();//en cas de dépendance, vider d'abord la/les table(s) qui a/ont une clé étrangère
        em.createQuery("DELETE FROM Client c").executeUpdate();

        // Ajoutes des données en spécifiant les IDs que l'on va récup ds les tests unitaires
        // Persister en bases certaines données
        Client c1 = new Client();
        c1.setId(1L);
        c1.setNom("Fifi");
        em.persist(c1);

        Client c2 = new Client();
        c2.setId(2L);
        c2.setNom("Riri");
        em.persist(c2);

        Client c3 = new Client();
        c3.setId(3L);
        c3.setNom("Loulou");
        em.persist(c3);

        Commande com1 = new Commande();
        com1.setId(1L);
        com1.setPrixTotal((double) 1000);
        com1.setClient(c1);
        c1.getCommandes().add(com1);
        em.persist(com1);

        Commande com2 = new Commande();
        com2.setId(2L);
        com2.setPrixTotal((double) 5);
        com2.setClient(c3);
        c3.getCommandes().add(com2);
        em.persist(com2);

        Commande com3 = new Commande();
        com3.setId(3L);
        com3.setPrixTotal((double) 2);
        com3.setClient(c3);
        c3.getCommandes().add(com3);
        em.persist(com3);

        em.getTransaction().commit();
    }

//    @Test
//    public void verifieQueCatId1EstBasket() {
//        
//        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        
//        Categorie cat = em.find(Categorie.class, 1L);
//        
//        if( cat.getNom().equals("Basket")==false ){
//            Assert.fail("CA MARCHE PAS MON GARS!");
//        }
//    }
//
//    @Test
//    public void testListeProdCategorie() {
//
//        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//
//        Categorie cat = em.find(Categorie.class, 1L);
//        for (Produit p : cat.getProduits()) {
//
//            System.out.println(p);
//        }
//    }
//
//    @Test
//    public void testCreateDB() {
//
//    }
    @Test
    public void megaCheckDeLaMort() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Client loulou = em.find(Client.class, 3L);
        Client riri = em.find(Client.class, 2L);
        Commande comLoulou = em.find(Commande.class, 3L);
        Commande comPasRiri = em.find(Commande.class, 2L);

        int nbCom = 0;
        for (Commande com : loulou.getCommandes()) {
            nbCom++;
        }

        System.out.println("Nb de commandes de Loulou : " + nbCom);
        if (comLoulou.getClient().equals(loulou) == true) {

            System.out.println("c'est la commande de Loulou");
        }

        if (comPasRiri.getClient().equals(riri) == false) {

            System.out.println("ce n'est pas la commande de Riri");
        }
//        Assert.assertEquals(comLoulou.getClient(), loulou);//doit OK
//        Assert.assertEquals(comPasRiri.getClient(), riri);//doit KO
    }
}
