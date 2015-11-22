package be.ipl.android.moviebuzz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private static String TROUVER_CHALLENGER = "Trouver challenger !!!";
    public static final String TYPE_EPR_MAX_POINT = "TYPE_EPR_MAX_POINT";
    public static final String TYPE_EPR_CONTRE_MONTRE = "TYPE_EPR_CONTRE_MONTRE";
    public static final String TYPE_EPR_MAX_EPREUVE = "TYPE_EPR_MAX_EPREUVE";
    public static final String PARAM_TYPE_EPR_CHOISI = "PARAM_TYPE_EPR_CHOISI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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

    public void onClickMaxPoint(View view) {
        dialogSelectionEpreuve("MAX POINT", this.TYPE_EPR_MAX_POINT);
    }
    public void onClickMaxEpreuve(View view) {
        dialogSelectionEpreuve("MAX EPREUVE",this.TYPE_EPR_MAX_EPREUVE);
    }
    public void onClickContreMontre(View view) {
        dialogSelectionEpreuve("CONTRE MONTRE",this.TYPE_EPR_CONTRE_MONTRE);
    }
    private void dialogSelectionEpreuve(String titre,final String typeEpreuve) {
        //Création de l'AlertDialog
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.dialog_type_partie, null);
        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);
        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);
        final Intent detailIntent = new Intent(this, Main3Activity.class);
        final String t= new String(titre);
        adb.setPositiveButton(TROUVER_CHALLENGER, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText et = (EditText) alertDialogView.findViewById(R.id.EditText1);
                try {
                    detailIntent.putExtra(Main3Activity.PARAM_NOMBRE_CHOISI, Integer.parseInt(et.getText().toString()));
                } catch (Exception e) {
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "Rentrez le nombre voulu", duration);
                    toast.show();
                    dialogSelectionEpreuve(t, typeEpreuve);
                    return;
                }
                detailIntent.putExtra(Main2Activity.PARAM_TYPE_EPR_CHOISI, typeEpreuve);
                startActivityForResult(detailIntent, 1);
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
        //int viewId = view.getId();
        //Intent detailIntent = new Intent(this, Main2Activity.class);
        //startActivityForResult(detailIntent, 1);
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //TODO A lier a la fin de la partie

    public void dialogVictoire(View view) {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.dialog_fin_partie_gagne, null);

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
        //Intent detailIntent = new Intent(this, Main2Activity.class);
        //startActivityForResult(detailIntent, 1);
    }
}
