package DAL.Personne;

import BL.Personne.Personne;
import java.util.ArrayList;

public interface IDAOPersonne {
    boolean addPersonne(Personne personne);
    boolean updatePersonne(Personne personne);
    boolean deletePersonne(int id);
    Personne getPersonneById(int id);
    ArrayList<Personne> getAllPersonnes();
}
