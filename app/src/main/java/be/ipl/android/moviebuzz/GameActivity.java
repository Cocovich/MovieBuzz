package be.ipl.android.moviebuzz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import be.ipl.android.moviebuzz.events.TimerEvent;
import be.ipl.android.moviebuzz.events.TimerListener;
import be.ipl.android.moviebuzz.exceptions.EndOfGameException;
import be.ipl.android.moviebuzz.model.DAO;
import be.ipl.android.moviebuzz.model.Epreuve;
import be.ipl.android.moviebuzz.model.Jeu;
import be.ipl.android.moviebuzz.model.JeuContreMontre;
import be.ipl.android.moviebuzz.model.JeuMaxEpreuves;
import be.ipl.android.moviebuzz.model.JeuMaxPoints;

public class GameActivity extends AppCompatActivity implements TimerListener {

    public static final String PARAM_JEU_DUREE = "PARAM_JEU_DUREE";
    public static final String PARAM_JEU_EPREUVES = "PARAM_JEU_EPREUVES";
    public static final String PARAM_JEU_POINTS = "PARAM_JEU_POINTS";

    private Jeu jeu;

    private TextView pointsView;
    private TextView gameTimer;
    private ResizableImageView imageView;
    private TextView questionView;
    private RadioGroup radioGroup;
    private TextView questionTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // On reçoit les paramètres de jeu
        Intent intent = getIntent();
        int dureeMax = intent.getIntExtra(PARAM_JEU_DUREE, 0);
        int epreuvesMax = intent.getIntExtra(PARAM_JEU_EPREUVES, 0);
        int pointsMax = intent.getIntExtra(PARAM_JEU_POINTS, 0);

        // On crée le jeu selon son type
        if (dureeMax != 0)
            jeu = new JeuContreMontre(dureeMax);
        else if (epreuvesMax != 0)
            jeu = new JeuMaxEpreuves(epreuvesMax);
        else if (pointsMax != 0)
            jeu = new JeuMaxPoints(pointsMax);
        else
            throw new RuntimeException("Pas de type d'épreuve sélectionnée");

        jeu.addListener(this);

        // On remplit le jeu avec les épreuves sorties de la BDD
        DAO dao = new DAO(this);
        dao.open();
        dao.fillGame(jeu);
        dao.close();

        // On cherche les vues à contenu dynamique seulement une fois
        pointsView = (TextView) findViewById(R.id.gameValuePoints);
        gameTimer = (TextView) findViewById(R.id.gameValueTimer);
        imageView = (ResizableImageView) findViewById(R.id.gameImage);
        questionView = (TextView) findViewById(R.id.gameQuestion);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        questionTimer = (TextView) findViewById(R.id.questionTimer);

        startGame();
    }

    private void startGame() {
        jeu.start();
        refresh();
    }

    private void endGame() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        final Intent menuIntent = new Intent(this, MainActivity.class);

        if (jeu.isWon()) {
            adb.setTitle("Vous avez gagné !");
        } else {
            adb.setTitle("Vous avez perdu :(");
        }
        adb.setMessage("Vous avez récolté "+jeu.getPoints()+" points.");
        adb.setNeutralButton("Retourner au menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(menuIntent);
            }
        });

        adb.show();
    }

    private void refresh() {

        Epreuve epreuve = jeu.getEpreuve();

        // Image
        if (epreuve.getCheminImage() != null) {
            try {
                InputStream ims = getAssets().open(epreuve.getCheminImage());
                Drawable d = Drawable.createFromStream(ims, null);
                imageView.setImageDrawable(d);
            } catch (IOException ignored) {}
        }

        // Question
        questionView.setText(epreuve.getQuestion());

        // Propositions
        radioGroup.clearCheck();
        radioGroup.removeAllViews();
        for (String prop : epreuve.getPropositions()) {
            RadioButton radio = new RadioButton(this);
            radio.setText(prop);
//            radio.setGravity();
            radioGroup.addView(radio);
        }
    }

    public void buzz(View view) {

        RadioButton checked = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        boolean bonneReponse = jeu.buzz(checked.getText().toString());

        String message;
        if (bonneReponse) {
            message = "Bonne réponse !!!\n+" + jeu.getEpreuve().getPoints() + " points";
            pointsView.setText(String.valueOf(jeu.getPoints()));
        } else {
            message = "Non... La bonne réponse était :\n" + jeu.getEpreuve().getReponse();
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        try {
            jeu.nextEpreuve();
            refresh();
        } catch (EndOfGameException e) {
            endGame();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void timerChanged(TimerEvent event) {
        /* Timer question */

        int milliseconds = jeu.getQuestionRemainingTime();

        questionTimer.setText(String.valueOf(milliseconds / 1000));
        if (milliseconds < 5000) questionTimer.setTextColor(Color.RED);
        if (milliseconds <= 1000) questionTimer.setTextColor(Color.GRAY);

        /* Timer jeu */

        int seconds;
        if (jeu instanceof JeuContreMontre)
            seconds = ((JeuContreMontre) jeu).getGameRemainingTime();
        else
            seconds = jeu.getGameTime();

        int minutes = seconds / 60;
        int secondes = seconds % 60;

        gameTimer.setText(String.format("%02d:%02d", minutes, secondes));
        if (seconds <= 10) gameTimer.setTextColor(Color.RED);
        if (seconds <= 1) gameTimer.setTextColor(Color.GRAY);
    }

    @Override
    public void endOfTimer(TimerEvent event) {
        endGame();
    }
}
