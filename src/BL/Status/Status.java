package BL.Status;

public class Status {
    private final int id;
    private String status;

    public Status (int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status)
    {
        if(status != null && status.isEmpty())
        {
            this.status = status;
        }
        else
        {
            System.out.println("Erreur : Type Null");
        }
    }
}