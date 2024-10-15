package DAL.CoursPersonne;

import BL.Cours.Cours;
import BL.Personne.Personne;
import BL.CoursPersonne.CoursPersonne;
import java.sql.*;
import java.util.ArrayList;

public class DAOCoursPersonne implements IDAOCoursPersonne {

    private Connection connexion;
    private PreparedStatement insertCoursPersonne;
    private PreparedStatement deleteCoursPersonne;
    private PreparedStatement getAllCoursPersonne;

    public DAOCoursPersonne(String url, String user, String password) {
        try {
            this.connexion = DriverManager.getConnection(url, user, password);

            // Création de la table Cours_Personne si elle n'existe pas
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Cours_Personne (" +
                    "id_personne INT REFERENCES Personne(id), " +
                    "id_cours INT REFERENCES Cours(id), " +
                    "annee INT NOT NULL, " +
                    "PRIMARY KEY (id_personne, id_cours))";
            try (PreparedStatement ps = connexion.prepareStatement(createTableQuery)) {
                ps.executeUpdate();
            }

            // Préparation des requêtes
            insertCoursPersonne = this.connexion.prepareStatement(
                    "INSERT INTO Cours_Personne (id_personne, id_cours, annee) VALUES (?, ?, ?)");
            deleteCoursPersonne = this.connexion.prepareStatement(
                    "DELETE FROM Cours_Personne WHERE id_personne = ? AND id_cours = ?");
            getAllCoursPersonne = this.connexion.prepareStatement(
                    "SELECT * FROM Cours_Personne");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Cours_Personne : " + e.getMessage());
        }
    }

    @Override
    public boolean addCoursPersonne(CoursPersonne cp) {
        try {
            insertCoursPersonne.setInt(1, cp.getPersonne().getId());
            insertCoursPersonne.setInt(2, cp.getCours().getId());
            insertCoursPersonne.setInt(3, cp.getAnnee());
            insertCoursPersonne.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du cours pour la personne : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteCoursPersonne(int idPersonne, int idCours) {
        try {
            deleteCoursPersonne.setInt(1, idPersonne);
            deleteCoursPersonne.setInt(2, idCours);
            deleteCoursPersonne.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du cours pour la personne : " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<CoursPersonne> getAllCoursPersonne() {
        ArrayList<CoursPersonne> coursPersonnes = new ArrayList<>();
        try {
            ResultSet rs = getAllCoursPersonne.executeQuery();
            while (rs.next()) {
                Personne personne = new Personne(rs.getInt("id_personne"), null, "", ""); // Récupérer la vraie personne
                Cours cours = new Cours(rs.getInt("id_cours"), null, "");  // Récupérer le vrai cours
                CoursPersonne cp = new CoursPersonne(personne, cours, rs.getInt("annee"));
                coursPersonnes.add(cp);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des cours pour les personnes : " + e.getMessage());
        }
        return coursPersonnes;
    }
}
