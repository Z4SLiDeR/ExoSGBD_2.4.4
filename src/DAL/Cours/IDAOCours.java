package DAL.Cours;

import BL.Cours.Cours;
import java.util.ArrayList;

public interface IDAOCours {
    boolean addCours(String nom, int idSection);
    boolean updateCours(int id, String nom, int idSection);
    boolean deleteCours(int id);
    ArrayList<Cours> getCours();
    int getIDCours(String nom);
}
