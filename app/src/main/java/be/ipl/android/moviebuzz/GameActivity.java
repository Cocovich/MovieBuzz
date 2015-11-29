package be.ipl.android.moviebuzz;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    static final int PICK_CONTACT_REQUEST = 1;  // The request code


    private Jeu jeu;

    private EditText phoneNumber;
    private TextView epreuveView;
    private TextView pointsView;
    private TextView gameTimer;
    private ImageView imageView;
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
        epreuveView = (TextView) findViewById(R.id.gameValueEpreuve);
        gameTimer = (TextView) findViewById(R.id.gameValueTimer);
        pointsView = (TextView) findViewById(R.id.gameValuePoints);
        imageView = (ImageView) findViewById(R.id.gameImage);
        questionView = (TextView) findViewById(R.id.gameQuestion);
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        questionTimer = (TextView) findViewById(R.id.questionTimer);

        startGame();
    }

    private void startGame() {
        jeu.start();
        refresh();
    }

    //SOURCE pour le sms : http://javatechig.com/android/sending-sms-message-in-android
    private void endGame() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //On instancie notre layout en tant que View
        View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_fin_partie, null);
        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setTitle("Jeu terminé");
        adb.setView(dialog);

        final EditText playerName = (EditText) dialog.findViewById(R.id.playerName);
        phoneNumber = (EditText) dialog.findViewById(R.id.phoneNumber);
        final EditText message = (EditText) dialog.findViewById(R.id.smsMessage);

        adb.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String defaultMessage = String.format("%s vient de gagner %d points au jeu MovieBuzz.", playerName.getText().toString(), jeu.getPoints());
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber.getText().toString(), null, defaultMessage + " " + message.getText().toString(), null, null);
                    retourMenu();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Il y a eu une erreur lors de l'envoi.", Toast.LENGTH_LONG).show();
                }
            }
        });

        adb.setNegativeButton("Retourner au menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                retourMenu();
            }
        });

        adb.show();
    }

    private void retourMenu() {
        Intent menuIntent = new Intent(this, MainActivity.class);
        startActivity(menuIntent);
    }

    private void refresh() {

        Epreuve epreuve = jeu.getEpreuve();

        epreuveView.setText(String.valueOf(jeu.getNbEpreuves() + 1));
        pointsView.setText(String.valueOf(jeu.getPoints()));

        // Image
        if (epreuve.getCheminImage() != null) {
            try {
                InputStream ims = getAssets().open(epreuve.getCheminImage());
                Drawable d = Drawable.createFromStream(ims, null);
                imageView.setImageDrawable(d);
            } catch (IOException ignored) {
            }
        }

        // Question
        questionView.setText(epreuve.getQuestion());

        // Propositions
        radioGroup.clearCheck();
        radioGroup.removeAllViews();
        for (String prop : epreuve.getPropositions()) {
            RadioButton radio = new RadioButton(this);
            radio.setText(prop);
            radioGroup.addView(radio);
        }
    }

    public void buzz(View view) {

        RadioButton checked = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        if (checked == null) return;
        boolean bonneReponse = jeu.buzz(checked.getText().toString());

        String message;
        if (bonneReponse) {
            message = "Bonne réponse !!!\n+" + jeu.getEpreuve().getPoints() + " points";
        } else {
            message = "Non... La bonne réponse était :\n" + jeu.getEpreuve().getReponse();
        }
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

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
        if (jeu instanceof JeuContreMontre) {
            seconds = ((JeuContreMontre) jeu).getGameRemainingTime();
            if (seconds <= 10) gameTimer.setTextColor(Color.RED);
            if (seconds == 0) gameTimer.setTextColor(Color.GRAY);
        } else
            seconds = jeu.getGameTime();

        int minutes = seconds / 60;
        int secondes = seconds % 60;

        gameTimer.setText(String.format("%02d:%02d", minutes, secondes));
    }

    @Override
    public void endOfTimer(TimerEvent event) {
        endGame();
    }

    //SOURCE pour chercher les contactes : http://developer.android.com/training/basics/intents/result.html
    public void pickContact(View view) {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);


                // Do something with the phone number...

                Toast.makeText(getApplicationContext(), "" + number, Toast.LENGTH_LONG).show();
                phoneNumber.setText(number);
            }
        }
    }
}
