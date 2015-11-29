package be.ipl.android.moviebuzz.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        Cursor cursor = sqLiteDatabase.query(ModelContract.GameDBEntry.TABLE_NAME, null, null,  null, null, null, "RANDOM()", ""+jeu.getMaxEpreuves());


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
    }
}
