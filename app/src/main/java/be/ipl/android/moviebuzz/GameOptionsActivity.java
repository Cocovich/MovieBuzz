package be.ipl.android.moviebuzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import be.ipl.android.moviebuzz.model.Jeu;

public class GameOptionsActivity extends AppCompatActivity {

    private static String TROUVER_CHALLENGER = "Trouver challenger !!!";
    public static final String PARAM_TYPE_EPR_CHOISI = "PARAM_TYPE_EPR_CHOISI";

    private SeekBar durationSeekBar;
    private SeekBar pointsSeekBar;
    private SeekBar questionsSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);

        constructDurationSeekBar();
        constructPointsSeekBar();
        constructQuestionsSeekBar();
    }

    private void constructDurationSeekBar() {
        // On récupère la seekbar et le label affichant sa valeur
        durationSeekBar = (SeekBar) findViewById(R.id.durationSeekBar);
        final TextView durationLabel = (TextView) findViewById(R.id.valueMaxDuration);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        durationSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minutes = progress / 60;
                int secondes = progress % 60;
                durationLabel.setText(String.format("%02d:%02d", minutes, secondes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // On remplit la valeur par défaut de la seekbar
        durationSeekBar.setProgress(Jeu.DEF_DUREE_MAX);
    }

    private void constructPointsSeekBar() {
        // On récupère la seekbar et le label affichant sa valeur
        pointsSeekBar = (SeekBar) findViewById(R.id.pointsSeekBar);
        final TextView pointsLabel = (TextView) findViewById(R.id.valueMaxPoints);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        pointsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pointsLabel.setText(String.format("%d points", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        // On remplit la valeur par défaut de la seekbar
        pointsSeekBar.setProgress(Jeu.DEF_NOMBRE_POINTS);
    }

    private void constructQuestionsSeekBar() {
        // On récupère la seekbar et le label affichant sa valeur
        questionsSeekBar = (SeekBar) findViewById(R.id.questionsSeekBar);
        final TextView questionsLabel = (TextView) findViewById(R.id.valueMaxQuestions);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        questionsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                questionsLabel.setText(String.format("%d épreuves", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        // On remplit la valeur par défaut de la seekbar
        pointsSeekBar.setProgress(Jeu.DEF_NOMBRE_EPREUVES);
    }

    public void onStartGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.PARAM_JEU_DUREE, durationSeekBar.getProgress());
        intent.putExtra(GameActivity.PARAM_JEU_POINTS, pointsSeekBar.getProgress());
        intent.putExtra(GameActivity.PARAM_JEU_EPREUVES, questionsSeekBar.getProgress());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

    /*public void onClickMaxPoint(View view) {
        dialogSelectionEpreuve("MAX POINT");
    }
    public void onClickMaxEpreuve(View view) {
        dialogSelectionEpreuve("MAX EPREUVE");
    }
    public void onClickContreMontre(View view) {
        dialogSelectionEpreuve("CONTRE MONTRE");
    }
    private void dialogSelectionEpreuve(final String titre) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //On instancie notre layout en tant que View
        View alertDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_type_partie, null);
        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);
        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);
        final EditText editText = (EditText) alertDialogView.findViewById(R.id.param_partie);
        final Intent jeuIntent = new Intent(this, GameActivity.class);
        adb.setPositiveButton(TROUVER_CHALLENGER, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    jeuIntent.putExtra(GameActivity.PARAM_JEU, Integer.parseInt(editText.getText().toString()));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Rentrez un nombre svp", Toast.LENGTH_LONG).show();
                    dialogSelectionEpreuve(titre);
                    return;
                }
                startActivityForResult(jeuIntent, 1);
            }
        });
        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Lorsque l'on cliquera sur annuler on quittera l'application
                //finish();
            }
        });
        adb.show();
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //TODO A lier a la fin de la partie

    public void dialogVictoire(View view) {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.dialog_game_end, null);

        //Création de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        adb.setTitle("FIN PARTIE");

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton("JE SUIS TROP FORT POUR TOI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //TODO
            }
        });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        adb.setNegativeButton("TU EN VEUX ENCIRE?", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //TODO
            }
        });
        adb.show();

    }

    public void dialogDefaite(View view) {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.dialog_fin_partie_perdu, null);

        //Création de l'AlertDialog
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        adb.setTitle("FIN PARTIE");

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton("J'ABONNE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //TODO
            }
        });

        //On crée un bouton "Annuler" à notre AlertDialog et on lui affecte un évènement
        adb.setNegativeButton("REVANCHE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //TODO
            }
        });
        adb.show();
        //int viewId = view.getId();
        //Intent detailIntent = new Intent(this, GameOptionsActivity.class);
        //startActivityForResult(detailIntent, 1);
    }*/
}
