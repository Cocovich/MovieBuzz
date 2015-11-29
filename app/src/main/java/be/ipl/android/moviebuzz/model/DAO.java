package be.ipl.android.moviebuzz.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO {

    private GameDBHelper gameDBHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DAO(Context context) {
        gameDBHelper = new GameDBHelper(context);
    }

    public void open() {
        try {
            sqLiteDatabase = gameDBHelper.getWritableDatabase();
        } catch (Exception e) {
            sqLiteDatabase = gameDBHelper.getReadableDatabase();
        }
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public void fillGame(Jeu jeu) {
        String limit = (jeu.getMaxEpreuves() > 0) ? String.valueOf(jeu.getMaxEpreuves()) : null;
        Cursor cursor = sqLiteDatabase.query(ModelContract.GameDBEntry.QUESTIONS_TABLE_NAME, null, null,  null, null, null, "RANDOM()", limit);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String question = cursor.getString(1);
            String[] propositions = Util.split(cursor.getString(2));
            //desordonner les propositions
            List<String> temp= Arrays.asList(propositions);
            Collections.shuffle(temp);
            propositions= (String[]) temp.toArray();
            String reponse = cursor.getString(3);
            @Epreuve.DifficultyLevel
            int difficulte = cursor.getInt(4);
            String image = cursor.getString(5);

            jeu.addEpreuve(new Epreuve(question, propositions, reponse, difficulte, image));
        }

        cursor.close();
    }

    public void saveStats(Jeu jeu) {
        gameDBHelper.sauverJeu(jeu, sqLiteDatabase);
    }

    public Map<String, Integer> getStats(String player) {

        Cursor cursor = sqLiteDatabase.query(
                ModelContract.GameDBEntry.GAMES_TABLE_NAME,
                new String[]{
                        "COUNT("+ModelContract.GameDBEntry._ID+")",
                        "SUM("+ModelContract.GameDBEntry.GAMES_COLUMN_NAME_POINTS+")",
                        "SUM("+ModelContract.GameDBEntry.GAMES_COLUMN_NAME_DURATION+")",
                        "SUM("+ModelContract.GameDBEntry.GAMES_COLUMN_NAME_ANSWERS_COUNT+")",
                        "SUM("+ModelContract.GameDBEntry.GAMES_COLUMN_NAME_TRUE_ANSWERS_COUNT+")",
                        "SUM("+ModelContract.GameDBEntry.GAMES_COLUMN_NAME_FALSE_ANSWERS_COUNT+")",
                },
                ModelContract.GameDBEntry.GAMES_COLUMN_NAME_PLAYER+" = ?",
                new String[]{player},
                null, null, null);

        if (!cursor.moveToFirst())
            return null;

        Map<String, Integer> stats = new HashMap<>(6);
        stats.put("Jeux", cursor.getInt(0));
        stats.put("Points", cursor.getInt(1));
        stats.put("Durée", cursor.getInt(2));
        stats.put("Réponses", cursor.getInt(3));
        stats.put("Bonnes réponses", cursor.getInt(4));
        stats.put("Mauvaises réponses", cursor.getInt(5));

        cursor.close();

        return stats;
    }

    public Map<String, Integer> getLastGameStats(String player) {

        Cursor cursor = sqLiteDatabase.query(
                ModelContract.GameDBEntry.GAMES_TABLE_NAME,
                new String[]{
                        "SUM("+ModelContract.GameDBEntry.GAMES_COLUMN_NAME_POINTS+")",
                        "SUM("+ModelContract.GameDBEntry.GAMES_COLUMN_NAME_DURATION+")",
                        "SUM("+ModelContract.GameDBEntry.GAMES_COLUMN_NAME_ANSWERS_COUNT+")",
                },
                ModelContract.GameDBEntry.GAMES_COLUMN_NAME_PLAYER+" = ?",
                new String[]{player},
                null, null,
                ModelContract.GameDBEntry.GAMES_COLUMN_NAME_DATE+" DESC",
                "1");

        if (!cursor.moveToFirst())
            return null;

        Map<String, Integer> stats = new HashMap<>(3);
        stats.put("Points", cursor.getInt(0));
        stats.put("Durée", cursor.getInt(1));
        stats.put("Réponses", cursor.getInt(2));

        cursor.close();

        return stats;
    }
}
