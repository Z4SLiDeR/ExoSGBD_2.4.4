package DAL.Cours;

import BL.Cours.Cours;
import BL.Section.Section;
import java.sql.*;
import java.util.ArrayList;

public class DAOCours implements IDAOCours {

    Connection connexion;
    PreparedStatement insertCours;
    PreparedStatement updateCours;
    PreparedStatement deleteCours;
    PreparedStatement getIDCours;
    PreparedStatement getCours;

    public DAOCours(String url, String user, String password) {
        try {
            this.connexion = DriverManager.getConnection(url, user, password);

            // Création de la table si elle n'existe pas
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Cours (id SERIAL PRIMARY KEY, nom VARCHAR(30), id_section INT REFERENCES Section(id))";
            try (PreparedStatement ps = connexion.prepareStatement(createTableQuery)) {
                ps.executeUpdate();
            }

            // Préparation des requêtes
            insertCours = this.connexion.prepareStatement("INSERT INTO Cours (nom, id_section) VALUES (?, ?)");
            updateCours = this.connexion.prepareStatement("UPDATE Cours SET nom = ?, id_section = ? WHERE id = ?");
            deleteCours = this.connexion.prepareStatement("DELETE FROM Cours WHERE id = ?");
            getIDCours = this.connexion.prepareStatement("SELECT id FROM Cours WHERE nom = ?");
            getCours = this.connexion.prepareStatement("SELECT id, nom, id_section FROM Cours");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addCours(String nom, int idSection) {
        try {
            insertCours.setString(1, nom);
            insertCours.setInt(2, idSection);
            insertCours.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateCours(int id, String nom, int idSection) {
        try {
            updateCours.setString(1, nom);
            updateCours.setInt(2, idSection);
            updateCours.setInt(3, id);
            updateCours.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCours(int id) {
        try {
            deleteCours.setInt(1, id);
            deleteCours.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Cours> getCours() {
        ArrayList<Cours> listeCours = new ArrayList<>();
        try {
            ResultSet set = getCours.executeQuery();
            while (set.next()) {
                Cours cours = new Cours(set.getInt(1), new Section(set.getInt(3), ""), set.getString(2)); // Associer l'ID de la section
                listeCours.add(cours);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeCours;
    }

    @Override
    public int getIDCours(String nom) {
        int id = -1;
        try {
            getIDCours.setString(1, nom);
            ResultSet set = getIDCours.executeQuery();
            if (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
