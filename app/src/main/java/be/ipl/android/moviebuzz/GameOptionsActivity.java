package be.ipl.android.moviebuzz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import be.ipl.android.moviebuzz.model.Jeu;

public class GameOptionsActivity extends AppCompatActivity {

    private AlertDialog.Builder adb;

    private TextView label;
    private TextView value;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);
    }

    public void onClickContreMontre(View view) {
        constructDialog();
        label.setText(R.string.options_duration);
        constructDurationSeekBar();
        fillDialog(GameActivity.PARAM_JEU_DUREE);
    }

    public void onClickMaxPoint(View view) {
        constructDialog();
        label.setText(R.string.options_points);
        constructPointsSeekBar();
        fillDialog(GameActivity.PARAM_JEU_POINTS);
    }

    public void onClickMaxEpreuve(View view) {
        constructDialog();
        label.setText(R.string.options_questions);
        constructQuestionsSeekBar();
        fillDialog(GameActivity.PARAM_JEU_EPREUVES);
    }

    private void constructDialog() {
        adb = new AlertDialog.Builder(this);
        //On instancie notre layout en tant que View
        View dialog = LayoutInflater.from(this).inflate(R.layout.dialog_type_partie, null);
        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(dialog);
        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        value = (TextView) dialog.findViewById(R.id.gameOptionValue);
        label = (TextView) dialog.findViewById(R.id.gameOptionLabel);
        seekBar = (SeekBar) dialog.findViewById(R.id.gameOptionSeekBar);
    }

    private void fillDialog(final String key) {

        final Intent gameIntent = new Intent(this, GameActivity.class);

        // Bouton "Commencer"
        adb.setPositiveButton("Commencer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                gameIntent.putExtra(key, seekBar.getProgress());
                startActivity(gameIntent);
            }
        });

        // Bouton "Annuler"
        adb.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Lorsque l'on cliquera sur annuler on quittera l'application
                //finish();
            }
        });

        adb.show();
    }

    private void constructDurationSeekBar() {
        seekBar.setMax(600);
        seekBar.setKeyProgressIncrement(10);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int minutes = progress / 60;
                int secondes = progress % 60;
                value.setText(String.format("%02d:%02d", minutes, secondes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // On remplit la valeur par défaut de la seekbar
        seekBar.setProgress(Jeu.DEF_DUREE);
    }

    private void constructPointsSeekBar() {
        seekBar.setMax(5000);
        seekBar.setKeyProgressIncrement(100);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value.setText(String.format("%d points", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        // On remplit la valeur par défaut de la seekbar
        seekBar.setProgress(Jeu.DEF_NOMBRE_POINTS);
    }

    private void constructQuestionsSeekBar() {
        seekBar.setMax(30);
        seekBar.setKeyProgressIncrement(1);
        // On associe un listener à la seekbar qui met à jour le label à chaque changement de valeur
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value.setText(String.format("%d épreuves", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        // On remplit la valeur par défaut de la seekbar
        seekBar.setProgress(Jeu.DEF_NOMBRE_EPREUVES);
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
}
