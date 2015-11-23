package be.ipl.android.moviebuzz.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public static Epreuve getEpreuveFromCursor(Cursor cursor) {

        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String question = cursor.getString(1);
            @Epreuve.DifficultyLevel
            int difficulte = cursor.getInt(2);
            String[] propositions = cursor.getString(3).split(Epreuve.PROPS_DELIMITER);
            String reponse = cursor.getString(4);
            String image = cursor.getString(5);

            return new Epreuve(question, propositions, reponse, difficulte, image);
        }

        return null;
    }
}