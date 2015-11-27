package be.ipl.android.moviebuzz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import be.ipl.android.moviebuzz.model.DAO;
import be.ipl.android.moviebuzz.model.Epreuve;
import be.ipl.android.moviebuzz.model.Jeu;

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
        int dureeMax = intent.getIntExtra(PARAM_JEU_DUREE, Jeu.DEF_DUREE_MAX);
        int epreuvesMax = intent.getIntExtra(PARAM_JEU_EPREUVES, Jeu.DEF_NOMBRE_EPREUVES);
        int pointsMax = intent.getIntExtra(PARAM_JEU_POINTS, Jeu.DEF_NOMBRE_POINTS);
        jeu = new Jeu(epreuvesMax, dureeMax, pointsMax);
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
        jeu.finish();
        /*Intent intent = new Intent(this, GameEndActivity.class);
        intent.putExtra(GameEndActivity.WIN, jeu.estGagne());
        startActivity(intent);*/
    }

    private void refresh() {

        Epreuve epreuve = jeu.getQuestion();

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
            message = "Bonne réponse !!!\n+" + jeu.getQuestion().getPoints() + " points";
            pointsView.setText(String.valueOf(jeu.getPoints()));
        } else {
            message = "Non... La bonne réponse était :\n" + jeu.getQuestion().getReponse();
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        jeu.nextQuestion();

        if (jeu.isInProgress()) {
            refresh();
        } else {
            endGame();
        }
    }

    /*public void contreMontre() {

        jeu = new JeuContreLaMontre(nombreVoulu);
        ((JeuContreLaMontre) jeu).addListener(this);

        TextView points = (TextView) findViewById(R.id.points);
        final TextView timerContreMontre = (TextView) findViewById(R.id.valeur_indic);
        final TextView labelTimer = (TextView) findViewById(R.id.indicateur);

        // creation  et lancement du compte a rebour Total du compte a rebour
        // avec le minute et seconde//
        CountDownTimer timerTotal = new CountDownTimer(this.nombreVoulu * 60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //mise a jour du timer contre montre
                long temp = millisUntilFinished / 1000;
                long minute = temp / 60 % 60;
                long seconde = temp % 60;
                timerContreMontre.setText("" + minute + ":" + seconde);
                if (temp < 5) timerContreMontre.setTextColor(Color.RED);
                if (temp <= 1) timerContreMontre.setTextColor(Color.GRAY);
            }

            public void onFinish() {
                timerContreMontre.setText("0");
                //TODO qui a gagner dans le dialog machin
            }
            //
        }.start();
        //timerPoint.start();
        lancerTimer();
        //buzz();
    }

    public void maxPoints() {
        TextView points = (TextView) findViewById(R.id.points);
        // affichage du nombre max de point
        final TextView maxpoint = (TextView) findViewById(R.id.valeur_indic);

        final TextView indicateurMaxPoint = (TextView) findViewById(R.id.indicateur);
        maxpoint.setText("" + this.nombreVoulu);
        indicateurMaxPoint.setText("Max points");

        timerPoint.start();
        //buzz();
    }

    public void maxEpreuves() {
        TextView points = (TextView) findViewById(R.id.points);
        // affichage du nombre max de point
        final TextView resteEpreuve = (TextView) findViewById(R.id.valeur_indic);
        final TextView indicateurMaxEpreuve = (TextView) findViewById(R.id.indicateur);
        resteEpreuve.setText("" + this.nombreVoulu);
        indicateurMaxEpreuve.setText("Restants");

        timerPoint.start();
        //buzz();
    }*/

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

        int seconds = jeu.getGameRemainingTime();
        int minutes = seconds / 60;
        int secondes = seconds % 60;

        gameTimer.setText(String.format("%02d:%02d", minutes, secondes));
        if (seconds <= 10) gameTimer.setTextColor(Color.RED);
        if (seconds <= 1) gameTimer.setTextColor(Color.GRAY);
    }
}
