package be.ipl.android.moviebuzz.model;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Epreuve implements Comparable<Epreuve> {
    @IntDef({NIVEAU_FACILE, NIVEAU_MOYEN, NIVEAU_DIFFICILE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DifficultyLevel {
    }

    public static final int NIVEAU_FACILE = 1;
    public static final int NIVEAU_MOYEN = 2;
    public static final int NIVEAU_DIFFICILE = 3;

    private String question;
    private Propositions propositions;
    private String reponse;
    @DifficultyLevel
    private int difficulte;
    private String cheminImage;

    private int duration; // temps de réponse (en millisecondes)
    private int points; // points gagnés
    private boolean isTrue; // la réponse donnée par le joueur est-elle correcte ?

    public Epreuve(String question, String[] propositions, String reponse,
                   @DifficultyLevel int difficulte, String cheminImage) {
        this.question = question;
        setPropositions(propositions);
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

    public Set<String> getPropositions() {
        return propositions;
    }

    public void setPropositions(String[] propositions) {
        this.propositions = new Propositions();
        Collections.addAll(this.propositions, propositions);
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

    public int getDuration() {
        return duration;
    }

    public int getPoints() {
        return points;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public boolean check(@NonNull String choice, int duration, float remainingTime) {
        this.duration = duration;
        this.isTrue = reponse.equals(choice);
        if (isTrue)
            computePoints(remainingTime);
        return isTrue;
    }

    private int computePoints(float remainingTime) {
        int base = difficulte * 5;
        float points = base + difficulte * 3 * remainingTime / 1000;
        this.points = Math.round(points);
        return this.points;
    }

    @Override
    public int compareTo(@NonNull Epreuve another) {
        return question.compareTo(another.getQuestion());
    }

    public class Propositions extends HashSet<String> {
        public static final String DELIMITER = ";";
    }
}
