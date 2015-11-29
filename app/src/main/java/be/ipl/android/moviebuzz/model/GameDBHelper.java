package be.ipl.android.moviebuzz.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import be.ipl.android.moviebuzz.model.ModelContract.GameDBEntry;

public class GameDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "Game.db";

    private static final String SQL_CREATE_QUESTIONS_TABLE =
        "CREATE TABLE " + GameDBEntry.QUESTIONS_TABLE_NAME + " (" +
                GameDBEntry._ID                       + " INTEGER PRIMARY KEY," +
                GameDBEntry.QUESTIONS_COLUMN_NAME_QUESTION + " TEXT," +
                GameDBEntry.QUESTIONS_COLUMN_NAME_PROPOSITIONS + " TEXT," +
                GameDBEntry.QUESTIONS_COLUMN_NAME_ANSWER + " TEXT," +
                GameDBEntry.QUESTIONS_COLUMN_NAME_DIFFICULTY + " INT," +
                GameDBEntry.QUESTIONS_COLUMN_NAME_IMAGE + " TEXT" +
                " )";
    public static final String SQL_DELETE_QUESTIONS_TABLE = "DROP TABLE IF EXISTS " + GameDBEntry.QUESTIONS_TABLE_NAME;

    private static final String SQL_CREATE_GAMES_TABLE =
        "CREATE TABLE " + GameDBEntry.GAMES_TABLE_NAME + " (" +
                GameDBEntry._ID                       + " INTEGER PRIMARY KEY," +
                GameDBEntry.GAMES_COLUMN_NAME_PLAYER + " TEXT," +
                GameDBEntry.GAMES_COLUMN_NAME_POINTS + " INT," +
                GameDBEntry.GAMES_COLUMN_NAME_DURATION + " INT," +
                GameDBEntry.GAMES_COLUMN_NAME_ANSWERS_COUNT + " INT," +
                GameDBEntry.GAMES_COLUMN_NAME_TRUE_ANSWERS_COUNT + " INT," +
                GameDBEntry.GAMES_COLUMN_NAME_FALSE_ANSWERS_COUNT + " INT," +
                GameDBEntry.GAMES_COLUMN_NAME_DATE + " TEXT" +
                " )";
    public static final String SQL_DELETE_GAMES_TABLE = "DROP TABLE IF EXISTS " + GameDBEntry.GAMES_TABLE_NAME;

    public GameDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_GAMES_TABLE);
        peuplement(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_QUESTIONS_TABLE);
        db.execSQL(SQL_DELETE_GAMES_TABLE);
        onCreate(db);
    }

    private void insererEpreuve(Epreuve epreuve, SQLiteDatabase db) {
        ContentValues valeurs = new ContentValues();
        valeurs.put(GameDBEntry.QUESTIONS_COLUMN_NAME_QUESTION, epreuve.getQuestion());
        valeurs.put(GameDBEntry.QUESTIONS_COLUMN_NAME_PROPOSITIONS, Util.join(epreuve.getPropositions()));
        valeurs.put(GameDBEntry.QUESTIONS_COLUMN_NAME_ANSWER, epreuve.getReponse());
        valeurs.put(GameDBEntry.QUESTIONS_COLUMN_NAME_DIFFICULTY, epreuve.getDifficulte());
        valeurs.put(GameDBEntry.QUESTIONS_COLUMN_NAME_IMAGE, epreuve.getCheminImage());
        db.insert(GameDBEntry.QUESTIONS_TABLE_NAME, null, valeurs);
    }

    public void sauverJeu(Jeu jeu, SQLiteDatabase db) {
        ContentValues valeurs = new ContentValues();
        valeurs.put(GameDBEntry.GAMES_COLUMN_NAME_PLAYER, jeu.getPlayer());
        valeurs.put(GameDBEntry.GAMES_COLUMN_NAME_POINTS, jeu.getPoints());
        valeurs.put(GameDBEntry.GAMES_COLUMN_NAME_DURATION, jeu.getGameTime());
        valeurs.put(GameDBEntry.GAMES_COLUMN_NAME_ANSWERS_COUNT, jeu.getNbReponses());
        valeurs.put(GameDBEntry.GAMES_COLUMN_NAME_TRUE_ANSWERS_COUNT, jeu.getNbBonnesReponses());
        valeurs.put(GameDBEntry.GAMES_COLUMN_NAME_FALSE_ANSWERS_COUNT, jeu.getNbMauvaisesReponses());
        valeurs.put(GameDBEntry.GAMES_COLUMN_NAME_DATE, "now");
        db.insert(GameDBEntry.GAMES_TABLE_NAME, null, valeurs);
    }

    private void peuplement(SQLiteDatabase db) {

        insererEpreuve(new Epreuve(
                "Comment se nomme ce personnage ?",
                new String[]{"Dingo", "Djingo", "Django", "Bingo"},
                "Django",
                Epreuve.NIVEAU_MOYEN,
                "djangoo.png"
        ), db);

        insererEpreuve(new Epreuve(
                "Comment se nomme ce film ?",
                new String[]{"Bad Guys", "Bad Cops", "Bad Boys", "Bad Bitches"},
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
                "bad-boys-flingues.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Que veux dire \"ouh ouh hi he ouuuh\"?",
                new String[]{"Jane reste", "Jane aime singes", "Jane aime gorille", "Jane part"},
                "Jane reste",
                Epreuve.NIVEAU_DIFFICILE,
                "tarzan-ouhouhhi.jpg"
        ), db);

        insererEpreuve(new Epreuve(
                "Que préparent ces personnages?",
                new String[]{"Les beignets mortels", "Les macarons foudroyants", "La tarte au venin de vipère", "Le pudding a l'arsenic"},
                "Le pudding a l'arsenic",
                Epreuve.NIVEAU_DIFFICILE,
                "pudding-arsenic.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Que cherche Astérix?",
                new String[]{"Le laissez-passer les pompiers", "Le laissez-passer les gaulois", "Le laissez-passer au port", "Le laissez-passer A38"},
                "Le laissez-passer A38",
                Epreuve.NIVEAU_DIFFICILE,
                "asterix-a38.jpg"
        ), db);

        insererEpreuve(new Epreuve(
                "Qui est ce personnage?",
                new String[]{"Sancho le cigare cubain", "Sancho le cubain", "le mask mexicain", "le mask cubain"},
                "Sancho le cubain",
                Epreuve.NIVEAU_DIFFICILE,
                "mask-cubain.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Quel est la véritable identité de Hulk",
                new String[]{"Docteur Freeze", "Docteur Maboule", "Docteur Banner", "Le géant vert"},
                "Docteur Banner",
                Epreuve.NIVEAU_DIFFICILE,
                "hulk.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Completez",
                new String[]{"Bomb", "Thor", "Avengers", "Hulk"},
                "Hulk",
                Epreuve.NIVEAU_DIFFICILE,
                "onahulk.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est cet homme?",
                new String[]{"Pablo Escobar", "Dieu le père", "Don Corléone", "Fidel Castro"},
                "Don Corléone",
                Epreuve.NIVEAU_DIFFICILE,
                "parrain-don-corleone.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Pas de bras pas de...",
                new String[]{"câlin", "chocolat", "bisous", "bonbon"},
                "chocolat",
                Epreuve.NIVEAU_DIFFICILE,
                "pas-chocolat.png"
        ), db);
        insererEpreuve(new Epreuve(
                "Que mangent les Avengers",
                new String[]{"un shawarma", "un MacDonald", "un calamar", "un omar"},
                "un shawarma",
                Epreuve.NIVEAU_DIFFICILE,
                "shawarma.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Comment cet homme était-il surnommé?",
                new String[]{"le gladiator", "le matador", "l'espagnol", "le général"},
                "l'espagnol",
                Epreuve.NIVEAU_DIFFICILE,
                "gladiator-espagnol.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "A quel film appartient ce personnage?",
                new String[]{"Troie", "300", "Hercule", "Gladiator"},
                "Troie",
                Epreuve.NIVEAU_DIFFICILE,
                "troie_film.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Ce personnage pleure...",
                new String[]{"des bonbons", "des fraises", "du chocolat", "du caramel"},
                "des bonbons",
                Epreuve.NIVEAU_DIFFICILE,
                "vice-versa-bing-bong.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce personnage?",
                new String[]{"Fifi brain d'acier", "Poil de carotte", "Arthur Lemoine", "Syndrome"},
                "Syndrome",
                Epreuve.NIVEAU_DIFFICILE,
                "syndrome.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce personnage?",
                new String[]{"Capitaine Planete", "Capitaine Flem", "Capitaine Flam", "Capitaine de l'espace"},
                "Capitaine Flam",
                Epreuve.NIVEAU_DIFFICILE,
                "capitaine-flam.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "À qui appartient ce vaiseau?",
                new String[]{"Luke Skywalker", "Han Solo", "Chewbacca", "Dark Vador"},
                "Han Solo",
                Epreuve.NIVEAU_DIFFICILE,
                "han-solo-vaiseau.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "A qui appartient cette épée?",
                new String[]{"Ichogo Kurosaki", "Sasuke Uchiua", "Zabuza Momochi", "Kisame Hoshigaki"},
                "Kisame Hoshigaki",
                Epreuve.NIVEAU_DIFFICILE,
                "Samehada1.png"
        ), db);
        insererEpreuve(new Epreuve(
                "Que veut dire \"mugiwara?\"",
                new String[]{"roi des pirates", "capitaine", "île au tresor", "chapeau de paille"},
                "chapeau de paille",
                Epreuve.NIVEAU_DIFFICILE,
                "mugiwara.png"
        ), db);
        insererEpreuve(new Epreuve(
                "Ces personnages sont des ...",
                new String[]{"Avengers", "Des pirates", "Des comics", "Des Mavels"},
                "Des pirates",
                Epreuve.NIVEAU_DIFFICILE,
                "onepieceavengers.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "De quel film est tirée cette image?",
                new String[]{"The Artist", "OSS 117", "OSS 107", "007 golden eye"},
                "The Artist",
                Epreuve.NIVEAU_DIFFICILE,
                "theartist.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Cafard à l'entrée...",
                new String[]{"et bouillie à la sortie", "et bouillie à la fin", "et bouillie au final", "et bouillie comme principale"},
                "et bouillie à la sortie",
                Epreuve.NIVEAU_DIFFICILE,
                "mib-cafard.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Comment s'appelle ce chien?",
                new String[]{"Frank", "Carlin", "Agent K", "Scrapidoo"},
                "Frank",
                Epreuve.NIVEAU_DIFFICILE,
                "men-in-black-frank.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce splendide canard?",
                new String[]{"Black Swan", "Donald Duck", "Daffy Duck", "Flagada Jones"},
                "Daffy Duck",
                Epreuve.NIVEAU_DIFFICILE,
                "daffy-duck.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce splendide canard?",
                new String[]{"Black Swan", "Donald Duck", "Daffy Duck", "Flagada Jones"},
                "Flagada Jones",
                Epreuve.NIVEAU_DIFFICILE,
                "flagada.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Quel est la phrase culte de Emmett Brown?",
                new String[]{"Non de dieu!", "Non d'un tonnerre!", "Par les dieux!", "Non de zeus!"},
                "Non de zeus!",
                Epreuve.NIVEAU_DIFFICILE,
                "nom-de-zeus.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "À qui appartient cette planche?",
                new String[]{"Marty McFly", "Anakin Skywalker", "Aladdin dans le futur", "Tony Hawk"},
                "Marty McFly",
                Epreuve.NIVEAU_DIFFICILE,
                "hoverboard.png"
        ), db);
        //kick ass
        insererEpreuve(new Epreuve(
                "Comment s'appelle ce personnage?",
                new String[]{"Bertolini", "Jim Carrey", "Syndrome", "Kick ass"},
                "Bertolini",
                Epreuve.NIVEAU_DIFFICILE,
                "Kick-Ass-2.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Jim Carrey dans...",
                new String[]{"Ace Ventura", "Je t'aime Philipe Morris", "Bruce tout puissant", "Menteur menteur"},
                "Menteur menteur",
                Epreuve.NIVEAU_DIFFICILE,
                "menteur-menteur.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "À quel film appartient cette image?",
                new String[]{"Case Départ", "Twenty years a slave", "Les évadés", "Django Unchained"},
                "Case Départ",
                Epreuve.NIVEAU_DIFFICILE,
                "case-depart.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Comment surnomme-t-on le Lieutenant Marion?",
                new String[]{"Cobra", "Rocky", "Rambo", "Terminator"},
                "Cobra",
                Epreuve.NIVEAU_DIFFICILE,
                "cobra.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est ce personnage?",
                new String[]{"Mega man", "Juge Dread", "Robot boy", "Optimus Prime"},
                "Juge Dread",
                Epreuve.NIVEAU_DIFFICILE,
                "juge-dread.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Que demande cet homme?",
                new String[]{"Où est-ce qu'il a appris à négocier?", "Où est-ce qu'il a appris à parler?", "Où est-ce qu'il a appris à chanter?", "Où est-ce qu'il a appris à se battre?"},
                "Où est-ce qu'il a appris à négocier?",
                Epreuve.NIVEAU_DIFFICILE,
                "5eme-element-negociation.png"
        ), db);
        insererEpreuve(new Epreuve(
                "À quel film appartient cette scène?",
                new String[]{"Demolition man", "Expendables", "Évasion", "Mafia love"},
                "Demolition man",
                Epreuve.NIVEAU_MOYEN,
                "demolitionman.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Que fait-il?",
                new String[]{"Il prend du papier toilette", "Il relève son courrier", "Il reçoit des ordres", "Il envoie un courrier"},
                "Il prend du papier toilette",
                Epreuve.NIVEAU_DIFFICILE,
                "demolition-man-papier.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Qui est cet homme?",
                new String[]{"le passager 57", "Blade", "john shaft", "50 cent"},
                "le passager 57",
                Epreuve.NIVEAU_DIFFICILE,
                "passager57.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Je suis...",
                new String[]{"un flic a la maternelle", "Terminator", "Hercule a new york", "le shérif"},
                "un flic a la maternelle",
                Epreuve.NIVEAU_DIFFICILE,
                "unflicmatternelle.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "J'ai un serpent...",
                new String[]{"dans ma botte", "devant moi", "à mes trousses", "dans mon chapeau"},
                "dans ma botte",
                Epreuve.NIVEAU_DIFFICILE,
                "toy-story-serpent-botte.jpg"
        ), db);/*
        insererEpreuve(new Epreuve(
                "Qu'est-ce que woody a dans sa botte?",
                new String[]{"un caillou", "un cactus", "une souris", "un serpent"},
                "un serpent",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);*/
        insererEpreuve(new Epreuve(
                "Que fabrique le Mask?",
                new String[]{"un chien", "une giraffe", "une arme", "un masque"},
                "une arme",
                Epreuve.NIVEAU_DIFFICILE,
                "mask-sulfateuse.png"
        ), db);
        insererEpreuve(new Epreuve(
                "Que vient-il de voir?",
                new String[]{"sa voisine", "son visage", "une jolie fille", "une souris"},
                "sa voisine",
                Epreuve.NIVEAU_DIFFICILE,
                "mask-voisin.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "Qui joue le rôle du Grinch?",
                new String[]{"Jim Carrey", "Eddy Murphy", "Adam Sandler", "Chris Toker"},
                "Jim Carrey",
                Epreuve.NIVEAU_MOYEN,
                "grinch.jpg"
        ), db);
        insererEpreuve(new Epreuve(
                "À qui appartient ce chapeau?",
                new String[]{"Indiana Jones", "Flagada Jones", "Django", "Lucky Luke"},
                "Indiana Jones",
                Epreuve.NIVEAU_DIFFICILE,
                "indiana-jones-fouet.png"
        ), db);
        insererEpreuve(new Epreuve(
                "Bonjour, je suis ..., votre assistant medical automatisé.",
                new String[]{"Baymax", "Cémax", "Daymax", "Baymin"},
                "Baymax",
                Epreuve.NIVEAU_DIFFICILE,
                "baymax.png"
        ), db);
        insererEpreuve(new Epreuve(
                "Comment est surnommé Max?",
                new String[]{"poche de sang", "le prisonnier", "le déserteur", "mad max"},
                "poche de sang",
                Epreuve.NIVEAU_DIFFICILE,
                ""
        ), db);
        insererEpreuve(new Epreuve(
                "Comment s'appelle cette espionne?",
                new String[]{"Carteapuce", "Antivirus", "Oeil de lynx", "Jyvoispasplus"},
                "Carteapuce",
                Epreuve.NIVEAU_DIFFICILE,
                "asterix-cleopatre.jpg"
        ), db);/*
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
        ), db);*/


    }
}
