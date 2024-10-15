package BL.Personne;

import BL.Status.Status;

public class Personne {
    private final int id;
    private Status status;
    private String nom;
    private String prenom;

    public Personne (int id, Status status, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom)
    {
        if(nom != null && nom.isEmpty())
        {
            this.nom = nom;
        }
        else
        {
            System.out.println("Erreur : Type Null");
        }
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom)
    {
        if(prenom != null && prenom.isEmpty())
        {
            this.prenom = prenom;
        }
        else
        {
            System.out.println("Erreur : Type Null");
        }
    }
}
