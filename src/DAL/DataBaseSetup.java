package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseSetup {

    // Méthode pour créer la base de données
    public static void createDatabase(String url, String user, String password, String dbName) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(sql);
            System.out.println("Base de données " + dbName + " créée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la base de données : " + e.getMessage());
        }
    }

    // Méthode pour créer les tables dans la base de données
    public static void createTables(String url, String user, String password) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            String createStatusTable = "CREATE TABLE IF NOT EXISTS Status (" +
                    "id SERIAL PRIMARY KEY, " +
                    "status VARCHAR(40) NOT NULL)";
            try (PreparedStatement ps = conn.prepareStatement(createStatusTable)) {
                ps.executeUpdate();
                System.out.println("Table 'Status' créée avec succès.");
            }

            String createSectionTable = "CREATE TABLE IF NOT EXISTS Section (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nom VARCHAR(30) NOT NULL)";
            try (PreparedStatement ps = conn.prepareStatement(createSectionTable)) {
                ps.executeUpdate();
                System.out.println("Table 'Section' créée avec succès.");
            }

            String createPersonneTable = "CREATE TABLE IF NOT EXISTS Personne (" +
                    "id SERIAL PRIMARY KEY, " +
                    "id_status INT REFERENCES Status(id), " +
                    "nom VARCHAR(15), " +
                    "prenom VARCHAR(15))";
            try (PreparedStatement ps = conn.prepareStatement(createPersonneTable)) {
                ps.executeUpdate();
                System.out.println("Table 'Personne' créée avec succès.");
            }

            String createCoursTable = "CREATE TABLE IF NOT EXISTS Cours (" +
                    "id SERIAL PRIMARY KEY, " +
                    "id_section INT REFERENCES Section(id), " +
                    "nom VARCHAR(30) NOT NULL)";
            try (PreparedStatement ps = conn.prepareStatement(createCoursTable)) {
                ps.executeUpdate();
                System.out.println("Table 'Cours' créée avec succès.");
            }

            String createCoursPersonneTable = "CREATE TABLE IF NOT EXISTS Cours_Personne (" +
                    "id_personne INT REFERENCES Personne(id), " +
                    "id_cours INT REFERENCES Cours(id), " +
                    "annee INT, " +
                    "PRIMARY KEY (id_personne, id_cours))";
            try (PreparedStatement ps = conn.prepareStatement(createCoursPersonneTable)) {
                ps.executeUpdate();
                System.out.println("Table 'Cours_Personne' créée avec succès.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la création des tables : " + e.getMessage());
        }
    }
}
