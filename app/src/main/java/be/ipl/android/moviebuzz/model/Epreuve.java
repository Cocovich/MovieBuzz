package be.ipl.android.moviebuzz.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;

public class Epreuve {
    @IntDef({NIVEAU_FACILE, NIVEAU_MOYEN, NIVEAU_DIFFICILE, NIVEAU_EXPERT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DifficultyLevel {
    }

    public static final int NIVEAU_FACILE = 1;
    public static final int NIVEAU_MOYEN = 2;
    public static final int NIVEAU_DIFFICILE = 3;
    public static final int NIVEAU_EXPERT = 4;

    public static final String PROPS_DELIMITER = ";";

    private String question;
    private String[] propositions;
    private String reponse;
    @DifficultyLevel
    private int difficulte;
    private String cheminImage;

    public Epreuve(String question, String[] propositions, String reponse,
                   @DifficultyLevel int difficulte, String cheminImage) {
        this.question = question;
        this.propositions = propositions;
        this.reponse = reponse;
        this.difficulte = difficulte;
        this.cheminImage = cheminImage;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getPropositions() {
        return propositions;
    }

    public void setPropositions(String[] propositions) {
        this.propositions = propositions;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    @DifficultyLevel
    public int getDifficulte() {
        return difficulte;
    }

    public void setDifficulte(@DifficultyLevel int difficulte) {
        this.difficulte = difficulte;
    }

    public String getCheminImage() {
        return cheminImage;
    }

    public void setCheminImage(String cheminImage) {
        this.cheminImage = cheminImage;
    }
}
