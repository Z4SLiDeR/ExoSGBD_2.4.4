import DAL.DataBaseAddSetup;
import DAL.DataBaseSetup;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://127.0.0.1:5432/";
        String user = "postgres";
        String password = "password"; //TODO Changer MDP
        String dbName = "ue1396";

        // Créer la base de données
        DataBaseSetup.createDatabase(url, user, password, dbName);

        // Connexion à la nouvelle base de données
        url = "jdbc:postgresql://127.0.0.1:5432/" + dbName;

        // Créer les tables dans la nouvelle base de données
        DataBaseSetup.createTables(url, user, password);

        // Insert les données dans les tables de la base de données
        DataBaseAddSetup.createDataTable(url, user, password);
    }
}