package DAL.Status;

import BL.Status.Status;
import java.sql.*;
import java.util.ArrayList;

public class DAOStatus implements IDAOStatus {

    Connection connexion;
    PreparedStatement insertStatus;
    PreparedStatement updateStatus;
    PreparedStatement deleteStatus;
    PreparedStatement getIDStatus;
    PreparedStatement getStatuses;

    public DAOStatus(String url, String user, String password) {
        try {
            this.connexion = DriverManager.getConnection(url, user, password);

            // Création de la table si elle n'existe pas
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Status (id SERIAL PRIMARY KEY, status VARCHAR(20))";
            try (PreparedStatement ps = connexion.prepareStatement(createTableQuery)) {
                ps.executeUpdate();
            }

            // Préparation des requêtes
            insertStatus = this.connexion.prepareStatement("INSERT INTO Status (status) VALUES (?)");
            updateStatus = this.connexion.prepareStatement("UPDATE Status SET status = ? WHERE id = ?");
            deleteStatus = this.connexion.prepareStatement("DELETE FROM Status WHERE id = ?");
            getIDStatus = this.connexion.prepareStatement("SELECT id FROM Status WHERE status = ?");
            getStatuses = this.connexion.prepareStatement("SELECT id, status FROM Status");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addStatus(String status) {
        try {
            insertStatus.setString(1, status);
            insertStatus.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateStatus(int id, String status) {
        try {
            updateStatus.setString(1, status);
            updateStatus.setInt(2, id);
            updateStatus.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteStatus(int id) {
        try {
            deleteStatus.setInt(1, id);
            deleteStatus.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Status> getStatuses() {
        ArrayList<Status> listeStatus = new ArrayList<>();
        try {
            ResultSet set = getStatuses.executeQuery();
            while (set.next()) {
                Status status = new Status(set.getInt(1), set.getString(2));
                listeStatus.add(status);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listeStatus;
    }

    @Override
    public int getIDStatus(String status) {
        int id = -1;
        try {
            getIDStatus.setString(1, status);
            ResultSet set = getIDStatus.executeQuery();
            if (set.next()) {
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }
}
