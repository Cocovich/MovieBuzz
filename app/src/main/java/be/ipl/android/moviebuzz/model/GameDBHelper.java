package be.ipl.android.moviebuzz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import be.ipl.android.moviebuzz.model.ModelContract.*;

public class GameDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Game.db";

    private static final String SQL_CREATE_TABLE =
        "CREATE TABLE " + GameDBEntry.TABLE_NAME  + " (" +
                GameDBEntry._ID                       + " INTEGER PRIMARY KEY," +
                GameDBEntry.COLUMN_NAME_QUESTION      + " TEXT," +
                GameDBEntry.COLUMN_NAME_PROPOSITIONS  + " TEXT," +
                GameDBEntry.COLUMN_NAME_ANSWER        + " TEXT," +
                GameDBEntry.COLUMN_NAME_DIFFICULTY    + " INT," +
                GameDBEntry.COLUMN_NAME_IMAGE         + " TEXT" +
                " )";
    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + GameDBEntry.TABLE_NAME;

    public GameDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        peuplement(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private void insererEpreuve(Epreuve epreuve, SQLiteDatabase db) {
        ContentValues valeurs = new ContentValues();
        valeurs.put(GameDBEntry.COLUMN_NAME_QUESTION, epreuve.getQuestion());
        valeurs.put(GameDBEntry.COLUMN_NAME_PROPOSITIONS, Util.join(epreuve.getPropositions(), Epreuve.PROPS_DELIMITER));
        valeurs.put(GameDBEntry.COLUMN_NAME_ANSWER, epreuve.getReponse());
        valeurs.put(GameDBEntry.COLUMN_NAME_DIFFICULTY, epreuve.getDifficulte());
        valeurs.put(GameDBEntry.COLUMN_NAME_IMAGE, epreuve.getCheminImage());
        db.insert(GameDBEntry.TABLE_NAME, null, valeurs);
    }

    private void peuplement(SQLiteDatabase db) {

        insererEpreuve(new Epreuve(
                "Comment se nomme ce personnage ?",
                new String[]{"Dingo", "Djingo", "Django", "Bingo"},
                "Django",
                Epreuve.NIVEAU_MOYEN,
                "django.jpg"
        ), db);

    }
}
