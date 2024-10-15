package DAL.Section;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import BL.Section.*;

public class DAOSection implements IDAOSection {

    Connection connexion;
    PreparedStatement insertSection;
    PreparedStatement updateSection;
    PreparedStatement deleteSection;
    PreparedStatement getIDSection;
    PreparedStatement getSections;

    public DAOSection(String url, String user, String password) {
        try {
            // Connexion à la base de données
            this.connexion = DriverManager.getConnection(url, user, password);

            // Création de la table si elle n'existe pas
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Section (id SERIAL PRIMARY KEY, nom VARCHAR(30))";
            try (PreparedStatement ps = connexion.prepareStatement(createTableQuery)) {
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // Préparation des requêtes
            insertSection = this.connexion.prepareStatement("INSERT INTO Section (nom) VALUES (?)");
            updateSection = this.connexion.prepareStatement("UPDATE Section SET nom = ? WHERE id = ?");
            deleteSection = this.connexion.prepareStatement("DELETE FROM Section WHERE id = ?");
            getIDSection = this.connexion.prepareStatement("SELECT id FROM Section WHERE nom = ?");
            getSections = this.connexion.prepareStatement("SELECT id, nom FROM Section");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void finalize() {
        if (this.connexion != null) {
            try {
                this.connexion.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Ajout d'une section
    @Override
    public boolean addSection(String nom) {
        try {
            insertSection.setString(1, nom);  // Insérer le nom dans la requête préparée
            insertSection.executeUpdate();    // Exécuter la requête
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    // Mise à jour d'une section
    @Override
    public boolean updateSection(int id, String nom) {
        try {
            updateSection.setString(1, nom); // Mettre à jour le nom
            updateSection.setInt(2, id);     // Spécifier l'id de la section à mettre à jour
            updateSection.executeUpdate();   // Exécuter la requête
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    // Suppression d'une section
    @Override
    public boolean deleteSection(int id) {
        try {
            deleteSection.setInt(1, id);     // Spécifier l'id de la section à supprimer
            deleteSection.executeUpdate();   // Exécuter la requête
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    // Obtenir l'ID d'une section par son nom
    @Override
    public int getIDSection(String nom) {
        int id = -1;
        try {
            getIDSection.setString(1, nom);  // Spécifier le nom de la section
            ResultSet set = getIDSection.executeQuery();  // Exécuter la requête
            if (set.next()) {
                id = set.getInt(1);  // Récupérer l'ID si la section existe
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    // Récupérer toutes les sections
    @Override
    public ArrayList<Section> getSections() {
        ArrayList<Section> listeSection = new ArrayList<>();
        try {
            ResultSet set = getSections.executeQuery();  // Exécuter la requête
            while (set.next()) {
                Section section = new Section(set.getInt(1), set.getString(2));
                listeSection.add(section);  // Ajouter chaque section à la liste
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeSection;
    }
}