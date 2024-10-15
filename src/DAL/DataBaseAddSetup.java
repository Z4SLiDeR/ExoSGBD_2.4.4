package DAL;

import java.sql.*;

public class DataBaseAddSetup {

    public static void createDataTable(String url, String user, String password) {
        // Vérifier si la base de données a été créée
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connexion réussie à la base de données !");

                // Insérer les données dans la table Section
                DataBaseAddSetup.insertSections(connection);

                // Insérer les données dans la table Status
                DataBaseAddSetup.insertStatuses(connection);

                // Insérer les données dans la table Cours
                DataBaseAddSetup.insertCours(connection);

                // Insérer les données dans la table Personne
                DataBaseAddSetup.insertPersonnes(connection);

                // Insérer les données dans la table CoursPersonne
                DataBaseAddSetup.insertCoursPersonne(connection);

            } else {
                System.out.println("Échec de la connexion à la base de données.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification de la base de données : " + e.getMessage());
        }
    }

    // Méthode pour insérer des données dans la table Section
    public static void insertSections(Connection connection) throws SQLException {
        String query = "INSERT INTO Section (nom) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "Informatique de gestion");
            ps.executeUpdate();
            ps.setString(1, "Droit");
            ps.executeUpdate();
            System.out.println("Sections insérées avec succès.");
        }
    }

    // Méthode pour insérer des données dans la table Status
    public static void insertStatuses(Connection connection) throws SQLException {
        String query = "INSERT INTO Status (status) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "Etudiant");
            ps.executeUpdate();
            ps.setString(1, "Charge de cours");
            ps.executeUpdate();
            ps.setString(1, "Employé administratif");
            ps.executeUpdate();
            System.out.println("Statuts insérés avec succès.");
        }
    }

    // Méthode pour insérer des données dans la table Cours
    public static void insertCours(Connection connection) throws SQLException {
        // Récupérer les IDs des sections
        int idInformatique = getIDSection(connection, "Informatique de gestion");
        int idDroit = getIDSection(connection, "Droit");

        String query = "INSERT INTO Cours (nom, id_section) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Insertion des cours pour la section "Informatique de gestion"
            ps.setString(1, "Base des réseaux");
            ps.setInt(2, idInformatique);
            ps.executeUpdate();

            ps.setString(1, "Systèmes d’exploitation");
            ps.setInt(2, idInformatique);
            ps.executeUpdate();

            // Insertion des cours pour la section "Droit"
            ps.setString(1, "Droit civil");
            ps.setInt(2, idDroit);
            ps.executeUpdate();

            ps.setString(1, "Droit commercial");
            ps.setInt(2, idDroit);
            ps.executeUpdate();

            System.out.println("Cours insérés avec succès.");
        }
    }

    // Méthode pour insérer des données dans la table Personne
    public static void insertPersonnes(Connection connection) throws SQLException {
        // Récupérer les IDs des statuts
        int idChargeCours = getIDStatus(connection, "Charge de cours");
        int idEmploye = getIDStatus(connection, "Employé administratif");
        int idEtudiant = getIDStatus(connection, "Etudiant");

        String query = "INSERT INTO Personne (id_status, nom, prenom) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Insertion des personnes
            ps.setInt(1, idChargeCours);
            ps.setString(2, "Poulet");
            ps.setString(3, "Gilles");
            ps.executeUpdate();

            ps.setInt(1, idChargeCours);
            ps.setString(2, "Godissart");
            ps.setString(3, "Emmanuel");
            ps.executeUpdate();

            ps.setInt(1, idEmploye);
            ps.setString(2, "Lai");
            ps.setString(3, "Valeria");
            ps.executeUpdate();

            ps.setInt(1, idEmploye);
            ps.setString(2, "Mairesse");
            ps.setString(3, "David");
            ps.executeUpdate();

            ps.setInt(1, idEtudiant);
            ps.setString(2, "Durant");
            ps.setString(3, "Richard");
            ps.executeUpdate();

            ps.setInt(1, idEtudiant);
            ps.setString(2, "Ortiz");
            ps.setString(3, "Valerie");
            ps.executeUpdate();

            System.out.println("Personnes insérées avec succès.");
        }
    }

    // Méthode pour insérer des données dans la table CoursPersonne
    public static void insertCoursPersonne(Connection connection) throws SQLException {
        // Récupérer les IDs des personnes
        int idPoulet = getIDPersonne(connection, "Poulet", "Gilles");
        int idGodissart = getIDPersonne(connection, "Godissart", "Emmanuel");
        int idDurant = getIDPersonne(connection, "Durant", "Richard");

        // Récupérer les IDs des cours
        int idSystemeExploitation = getIDCours(connection, "Systèmes d’exploitation");
        int idBaseReseaux = getIDCours(connection, "Base des réseaux");

        String query = "INSERT INTO Cours_Personne (id_personne, id_cours, annee) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            // Insertion des relations Cours-Personne
            ps.setInt(1, idPoulet);
            ps.setInt(2, idSystemeExploitation);
            ps.setInt(3, 2023); // Année
            ps.executeUpdate();

            ps.setInt(1, idGodissart);
            ps.setInt(2, idBaseReseaux);
            ps.setInt(3, 2023);
            ps.executeUpdate();

            ps.setInt(1, idDurant);
            ps.setInt(2, idSystemeExploitation);
            ps.setInt(3, 2023);
            ps.executeUpdate();

            ps.setInt(1, idDurant);
            ps.setInt(2, idBaseReseaux);
            ps.setInt(3, 2023);
            ps.executeUpdate();

            System.out.println("Cours-Personne insérés avec succès.");
        }
    }

    // Méthode pour obtenir l'ID d'une section
    public static int getIDSection(Connection connection, String sectionName) throws SQLException {
        String query = "SELECT id FROM Section WHERE nom = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, sectionName);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Section non trouvée : " + sectionName);
                }
            }
        }
    }

    // Méthode pour obtenir l'ID d'un statut
    public static int getIDStatus(Connection connection, String statusName) throws SQLException {
        String query = "SELECT id FROM Status WHERE status = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, statusName);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Statut non trouvé : " + statusName);
                }
            }
        }
    }

    // Méthode pour obtenir l'ID d'une personne
    public static int getIDPersonne(Connection connection, String nom, String prenom) throws SQLException {
        String query = "SELECT id FROM Personne WHERE nom = ? AND prenom = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nom);
            ps.setString(2, prenom);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Personne non trouvée : " + nom + " " + prenom);
                }
            }
        }
    }

    // Méthode pour obtenir l'ID d'un cours
    public static int getIDCours(Connection connection, String coursName) throws SQLException {
        String query = "SELECT id FROM Cours WHERE nom = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, coursName);
            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Cours non trouvé : " + coursName);
                }
            }
        }
    }
}
