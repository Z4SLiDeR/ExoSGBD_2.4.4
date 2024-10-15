package BL.CoursPersonne;

import BL.Cours.Cours;
import BL.Personne.Personne;

public class CoursPersonne {
    private final Personne personne;
    private final Cours cours;
    private int annee;

    public CoursPersonne(Personne personne, Cours cours, int annee) {
        this.personne = personne;
        this.cours = cours;
        this.annee = annee;
    }

    public Personne getPersonne() {
        return personne;
    }

    public Cours getCours() {
        return cours;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }
}
