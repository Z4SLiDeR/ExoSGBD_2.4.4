package DAL.CoursPersonne;

import BL.CoursPersonne.CoursPersonne;
import java.util.ArrayList;

public interface IDAOCoursPersonne {
    boolean addCoursPersonne(CoursPersonne cp);
    boolean deleteCoursPersonne(int idPersonne, int idCours);
    ArrayList<CoursPersonne> getAllCoursPersonne();
}
