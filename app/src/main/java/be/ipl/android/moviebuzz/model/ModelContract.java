package be.ipl.android.moviebuzz.model;

import android.provider.BaseColumns;

public class ModelContract {

    public ModelContract() {};

    // Contract for the DB
    public static abstract class GameDBEntry implements BaseColumns {
        public static final String QUESTIONS_TABLE_NAME = "questions";
        public static final String QUESTIONS_COLUMN_NAME_QUESTION = "question";
        public static final String QUESTIONS_COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String QUESTIONS_COLUMN_NAME_PROPOSITIONS = "propositions";
        public static final String QUESTIONS_COLUMN_NAME_ANSWER = "answer";
        public static final String QUESTIONS_COLUMN_NAME_IMAGE = "image";

        public static final String GAMES_TABLE_NAME = "games";
        public static final String GAMES_COLUMN_NAME_PLAYER = "player";
        public static final String GAMES_COLUMN_NAME_POINTS = "points";
        public static final String GAMES_COLUMN_NAME_DURATION = "duration";
        public static final String GAMES_COLUMN_NAME_ANSWERS_COUNT = "nombre_reponses";
        public static final String GAMES_COLUMN_NAME_TRUE_ANSWERS_COUNT = "nombre_bonnes_reponses";
        public static final String GAMES_COLUMN_NAME_FALSE_ANSWERS_COUNT = "nombre_mauvaises_reponses";
        public static final String GAMES_COLUMN_NAME_DATE = "date";
    }


}
