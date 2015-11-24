package be.ipl.android.moviebuzz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import be.ipl.android.moviebuzz.model.ModelContract.*;

public class GameDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
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
                "django.png"
        ), db);

        insererEpreuve(new Epreuve(
                "Comment se nomme ce film ?",
                new String[]{"Bad Guys", "Bad Cops", "Bad Boys", "Good cops"},
                "Bad Boys",
                Epreuve.NIVEAU_MOYEN,
                "bad-boys-ii.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Voici le lieutenant...",
                new String[]{"Aldo", "Abdo", "Fruit tropicaux", "Fruit de mer"},
                "Aldo",
                Epreuve.NIVEAU_DIFFICILE,
                "ingloriousbastard.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "... et file moi un paquet de chewing gum au ...",
                new String[]{"fruit rouge", "fruit de la passion", "fruit tropicaux", "fruit de mer"},
                "fruit de la passion",
                Epreuve.NIVEAU_DIFFICILE,
                "badboysfruitpassion.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Que veux dire \"ouh ouh hi he ouuuh\"?",
                new String[]{"Jane reste", "Jane aime singes", "Jane aime gorille", "Jane part"},
                "Jane reste",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);

        insererEpreuve(new Epreuve(
                "Que prepare cet homme?",
                new String[]{"Les beignets mortels", "Macaron foudroyant", "Tarte au venin de vipere", "Le pudding a l'arsenic"},
                "Le pudding a l'arsenic",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Que cherche Asterix?",
                new String[]{"Le laisser passer les pompiers", "Le laisser passer les gaulois", "Le laisser passer de Cesar", "Le laisser passer de Cesar a38"},
                "Le laisser passer de Cesar a38",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);

        insererEpreuve(new Epreuve(
                "Qui est ce personnage?",
                new String[]{"Sancho le cigare cubain", "Sancho le cubain", "le mask mexicain", "le mask cubain"},
                "Sancho le cubain",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Quel est la veritable identite de hulk",
                new String[]{"Docteur Freeze", "Docteur Maboule", "Docteur Banner", "le geant vert"},
                "Docteur Banner",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Completez",
                new String[]{"Bomb", "Thor", "Technologie", "Hulk"},
                "Hulk",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est cet homme?",
                new String[]{"Pablo Escobar", "Dieu le pere", "Don Corleone", "Fidel Castro"},
                "Don Corleone",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Pas de bras pas de...",
                new String[]{"calin", "chocolat", "bisous", "bonbon"},
                "chocolat",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Que mangent les Avengers",
                new String[]{"un shawarma", "un macDonald", "un calamar", "un omar"},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Comment cet homme etait-il surnomme?",
                new String[]{"le gladiator", "le matador", "L'espagnol", "le general"},
                "L'espagnol",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "A quel film appartient ce personnage?",
                new String[]{"Troie", "300", "Hercule", "Gladiator"},
                "Troie",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "ce personnage pleure...",
                new String[]{"des bonbons", "des fraises", "du chocolat", "du caramel"},
                "des bonbons",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce personnage?",
                new String[]{"Fifi brain d'acier", "Poil de carotte", "Arthur Lemoine", "Syndrome"},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce personnage?",
                new String[]{"Capitaine Planete", "Capitaine Flem", "Capitaine Flam", "Capitaine de l'espace"},
                "Capitaine Flam",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "A qui appartient ce vaiseau?",
                new String[]{"Luke Skywalker", "Han Solo", "Chewbacca", "Dark Vador"},
                "Han Solo",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "A qui appartient cette epee?",
                new String[]{"Ichogo Kurosaki", "Sasuke Uchiua", "Zabuza Momochi", "Kisame Hoshigaki"},
                "Kisame Hoshigaki",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Que veut dire \"mugiwara?\"",
                new String[]{"roi des pirates", "capitaine", "ile au tresor", "chapeau de paille"},
                "chapeau de paille",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Ces personnages sont des ...",
                new String[]{"Avengers", "Des pirates", "Des comics", "Des Mavels"},
                "Des pirates",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "de que film est tire cette image?",
                new String[]{"The Artist", "OSS 117", "OSS 117", "007 golden eye"},
                "The Artist",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Cafard à l'entrée...",
                new String[]{"et bouilli a la sortie", "et bouilli a la fin", "et bouilli au final", "et bouilli comme principal"},
                "et bouilli a la sortie",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "comment s'appel ce chien?",
                new String[]{"Frank", "Carlin", "Agent K", "Scrapidoo"},
                "Frank",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce splendide canard?",
                new String[]{"Black swan", "donald duck", "daffy duck", "flagada jones"},
                "donald duck",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce splendide canard?",
                new String[]{"Black swan", "donald duck", "daffy duck", "flagada jones"},
                "flagada jones",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Quel est la phrase culte de Emmett Brown",
                new String[]{"non de dieu", "non d'un tonnerre", "par les dieux", "non de zeus"},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                "retournomdezeus"
        ), db);
        insererEpreuve(new Epreuve(
                "A qui appartient cette planche?",
                new String[]{"Marty McFly", "Anakime Skywalker", "Alladin dans le futur", "Tony hawk"},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                "hoverboardretour.png"
        ), db);
        //kick ass
        insererEpreuve(new Epreuve(
                "Comment sappelle ce personnage?",
                new String[]{"Bertolini", "Jim Carrey", "Syndrome", "Kick ass"},
                "Bertolini",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Jim carrey dans...",
                new String[]{"Ace Ventura", "Je t'aime Philipe Morris", "Bruce tout puissant", "Menteur menteur"},
                "Menteur menteur",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "A quel film appartient cette image?",
                new String[]{"case depart", "twenty years of slave", "les evades", "Django"},
                "case depart",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Comment surnomme-t-on le Lieutenant Marion?",
                new String[]{"cobra", "rocky", "rombo", "terminator"},
                "cobra",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce personnage?",
                new String[]{"Mega man", "juge dreed", "robot boy", "optimus pryme"},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Que demande cet homme?",
                new String[]{"ou est ce qu'il a appris a negocier?", "ou est ce qu'il a appris a parler?", "ou est ce qu'il a appris a chanter?", "ou est ce qu'il a appris a se battre?"},
                "ou est ce qu'il a appris a negocier?",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "A quel film appartient cet scene?",
                new String[]{"demolition man", "expendable", "evasion", "mafia love"},
                "demolition man",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Que fait-il?",
                new String[]{"il prend du papier toilette", "il releve son courrier", "il recoit des ordres", "il envoi un courrier"},
                "il prend du papier toilette",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est cet homme?",
                new String[]{"le passager 57", "Blade", "john shaft", "50cent"},
                "le passager 57",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Je suis ...",
                new String[]{"un flic a la maternelle", "terminator", "hercule a new york", "le sherif"},
                "un flic a la maternelle",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "J'ai un serpent...",
                new String[]{"dans ma botte", "devant moi", "a mes trousses", "dans mon chapeau"},
                "dans ma botte",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qu'est-ce que woody a dans sa botte?",
                new String[]{"un caillou", "un cactus", "une souris", "un serpent"},
                "un serpent",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Que fabrique le mask?",
                new String[]{"un chien", "une girafe", "un arme", "un masque"},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Que vient il de voir?",
                new String[]{"sa voisine", "son visage", "une joli fille", "une souris"},
                "sa voisine",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Qui joue le role du grinch?",
                new String[]{"jim carrey", "eddy murphy", "adam sandler", "cris toker"},
                "jim carrey",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "A qui appartient ce chapeau?",
                new String[]{"indiana jones", "flagada jones", "django", "lucjy luck"},
                "indiana jones",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Bonjour, je suis bmax, votre assistant medical...",
                new String[]{"automatise", "automatique", "de premiers soins", "d'urgence"},
                "automatise",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "comment est surnomme max?",
                new String[]{"poche de sang", "le prisionner", "le deserteur", "mad max"},
                "poche de sang",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "comment s'appel cette espionne?",
                new String[]{"carteapuce", "antivirus", "oeil de lynx", "jyvoispasplus"},
                "carteapuce",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "",
                new String[]{"", "", "", ""},
                "",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);


    }
}
