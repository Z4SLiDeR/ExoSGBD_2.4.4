package DAL.Personne;

import BL.Personne.Personne;
import BL.Status.Status;
import java.sql.*;
import java.util.ArrayList;

public class DAOPersonne implements IDAOPersonne {

    private Connection connexion;
    private PreparedStatement insertPersonne;
    private PreparedStatement updatePersonne;
    private PreparedStatement deletePersonne;
    private PreparedStatement getPersonneById;
    private PreparedStatement getAllPersonnes;

    public DAOPersonne(String url, String user, String password) {
        try {
            this.connexion = DriverManager.getConnection(url, user, password);

            // Création de la table Personne si elle n'existe pas
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Personne (" +
                    "id SERIAL PRIMARY KEY, " +
                    "id_status INT REFERENCES Status(id), " +
                    "nom VARCHAR(15) NOT NULL, " +
                    "prenom VARCHAR(15) NOT NULL)";
            try (PreparedStatement ps = connexion.prepareStatement(createTableQuery)) {
                ps.executeUpdate();
            }

            // Préparation des requêtes
            insertPersonne = this.connexion.prepareStatement(
                    "INSERT INTO Personne (id_status, nom, prenom) VALUES (?, ?, ?)");
            updatePersonne = this.connexion.prepareStatement(
                    "UPDATE Personne SET id_status = ?, nom = ?, prenom = ? WHERE id = ?");
            deletePersonne = this.connexion.prepareStatement(
                    "DELETE FROM Personne WHERE id = ?");
            getPersonneById = this.connexion.prepareStatement(
                    "SELECT * FROM Personne WHERE id = ?");
            getAllPersonnes = this.connexion.prepareStatement(
                    "SELECT * FROM Personne");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Personne : " + e.getMessage());
        }
    }

    @Override
    public boolean addPersonne(Personne personne) {
        try {
            insertPersonne.setInt(1, personne.getStatus().getId());
            insertPersonne.setString(2, personne.getNom());
            insertPersonne.setString(3, personne.getPrenom());
            insertPersonne.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la personne : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePersonne(Personne personne) {
        try {
            updatePersonne.setInt(1, personne.getStatus().getId());
            updatePersonne.setString(2, personne.getNom());
            updatePersonne.setString(3, personne.getPrenom());
            updatePersonne.setInt(4, personne.getId());
            updatePersonne.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la personne : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePersonne(int id) {
        try {
            deletePersonne.setInt(1, id);
            deletePersonne.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la personne : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Personne getPersonneById(int id) {
        try {
            getPersonneById.setInt(1, id);
            ResultSet rs = getPersonneById.executeQuery();
            if (rs.next()) {
                Status status = new Status(rs.getInt("id_status"), "");  // Il faut récupérer le vrai statut ici
                return new Personne(rs.getInt("id"), status, rs.getString("nom"), rs.getString("prenom"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la personne : " + e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Personne> getAllPersonnes() {
        ArrayList<Personne> personnes = new ArrayList<>();
        try {
            ResultSet rs = getAllPersonnes.executeQuery();
            while (rs.next()) {
                Status status = new Status(rs.getInt("id_status"), "");  // Il faut récupérer le vrai statut ici
                Personne personne = new Personne(rs.getInt("id"), status, rs.getString("nom"), rs.getString("prenom"));
                personnes.add(personne);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des personnes : " + e.getMessage());
        }
        return personnes;
    }
}
