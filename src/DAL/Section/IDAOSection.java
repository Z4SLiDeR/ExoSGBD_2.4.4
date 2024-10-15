package DAL.Section;

import BL.Section.*;
import java.util.ArrayList;

public interface IDAOSection {
    
    public ArrayList<Section> getSections();

    public int getIDSection(String nom);

    public boolean updateSection(int id, String nom);

    public boolean deleteSection(int id);

    public boolean addSection(String nom);
}
