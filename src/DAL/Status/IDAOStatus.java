package DAL.Status;

import BL.Status.Status;

import java.util.ArrayList;

public interface IDAOStatus {
    boolean addStatus(String status);
    boolean updateStatus(int id, String status);
    boolean deleteStatus(int id);
    ArrayList<Status> getStatuses();
    int getIDStatus(String status);
}
