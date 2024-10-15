package BL.Cours;

import BL.Section.Section;

public class Cours {
    private final int id;
    private Section section;
    private String nom;

    public Cours (int id, Section section, String nom) {
        this.id = id;
        this.nom = nom;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
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
}
