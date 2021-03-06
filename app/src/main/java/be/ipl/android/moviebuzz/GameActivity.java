package be.ipl.android.moviebuzz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.KeyEvent;
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

import be.ipl.android.moviebuzz.events.GameListener;
import be.ipl.android.moviebuzz.exceptions.GameFinishedException;
import be.ipl.android.moviebuzz.model.DAO;
import be.ipl.android.moviebuzz.model.Epreuve;
import be.ipl.android.moviebuzz.model.Jeu;
import be.ipl.android.moviebuzz.model.JeuContreMontre;
import be.ipl.android.moviebuzz.model.JeuMaxEpreuves;
import be.ipl.android.moviebuzz.model.JeuMaxPoints;

public class GameActivity extends AppCompatActivity implements GameListener {

    public static final String PARAM_JEU_DUREE = "PARAM_JEU_DUREE";
    public static final String PARAM_JEU_EPREUVES = "PARAM_JEU_EPREUVES";
    public static final String PARAM_JEU_POINTS = "PARAM_JEU_POINTS";
    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    private AlertDialog ad;

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

        DAO dao = new DAO(this);
        SharedPreferences settings = getSharedPreferences(OptionsActivity.APP_PREFS, MODE_PRIVATE);
        String player = settings.getString(OptionsActivity.PREF_KEY_PLAYER_NAME, null);

        // On crée le jeu selon son type
        if (dureeMax != 0)
            jeu = new JeuContreMontre(dao, player, dureeMax);
        else if (epreuvesMax != 0)
            jeu = new JeuMaxEpreuves(dao, player, epreuvesMax);
        else if (pointsMax != 0)
            jeu = new JeuMaxPoints(dao, player, pointsMax);
        else
            throw new RuntimeException("Pas de type d'épreuve sélectionnée");

        jeu.fillGame(); // Remplit le jeu avec des questions
        jeu.addListener(this);

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

    private void endGame() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        ad = adb.create();
        //On instancie notre layout en tant que View
        View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_fin_partie, null);
        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setTitle("Jeu terminé");
        adb.setView(dialog);

        final EditText playerName = (EditText) dialog.findViewById(R.id.optionsLabelPlayerName);
        phoneNumber = (EditText) dialog.findViewById(R.id.phoneNumber);
        final EditText message = (EditText) dialog.findViewById(R.id.smsMessage);

        if (jeu.getPlayer() != null)
            playerName.setText(jeu.getPlayer());

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

        adb.setOnKeyListener(new AlertDialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    jeu.close();
                    finish();
                    ad.dismiss();
                }
                return true;
            }
        });

        adb.show();
    }

    private void retourMenu() {
        finish();
    }

    private void refresh() {

        Epreuve epreuve = jeu.getEpreuve();

        epreuveView.setText(String.valueOf(jeu.getNbReponses() + 1));
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
        } catch (GameFinishedException e) {
            jeu.finish();
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
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Confirmer");
        dialog.setMessage("Êtes-vous sûr de vouloir quitter la partie ?");
        dialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jeu.close();
                finish();
            }
        });

        dialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialog.show();
    }

    @Override
    public void gameStateChanged(int event) {
        switch (event) {
            /* Timer question */
            case GAME_TIMER_EVENT:

                int milliseconds = jeu.getQuestionRemainingTime();

                questionTimer.setText(String.valueOf(milliseconds / 1000));
                if (milliseconds < 5000) questionTimer.setTextColor(Color.RED);
                if (milliseconds <= 1000) questionTimer.setTextColor(Color.GRAY);

                break;

            /* Timer jeu */
            case QUESTION_TIMER_EVENT:

                int seconds;
                if (jeu instanceof JeuContreMontre) {
                    seconds = ((JeuContreMontre) jeu).getGameRemainingTime();
                    if (seconds <= 10) gameTimer.setTextColor(Color.RED);
                    if (seconds == 0) gameTimer.setTextColor(Color.GRAY);
                }
                else
                    seconds = jeu.getGameTime();

                int minutes = seconds / 60;
                int secondes = seconds % 60;

                gameTimer.setText(String.format("%02d:%02d", minutes, secondes));

                break;

            /* Jeu fini */
            case GAME_FINISHED_EVENT:
                jeu.finish();
                endGame();
                break;
        }
    }

    /*
    * SOURCE pour chercher les contacts
    * http://developer.android.com/training/basics/intents/result.html
    */

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
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);

                if (cursor == null)
                    return;

                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                phoneNumber.setText(cursor.getString(column));

                cursor.close();
            }
        }
    }
}
