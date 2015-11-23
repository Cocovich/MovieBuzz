package be.ipl.android.moviebuzz.model;

import android.provider.BaseColumns;

public class ModelContract {

    public ModelContract() {};

    // Contract for the DB
    public static abstract class GameDBEntry implements BaseColumns {
        public static final String TABLE_NAME = "games";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_PROPOSITIONS = "propositions";
        public static final String COLUMN_NAME_ANSWER = "answer";
        public static final String COLUMN_NAME_IMAGE = "image";
    }


}
