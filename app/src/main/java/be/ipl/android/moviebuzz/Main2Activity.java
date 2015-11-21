package be.ipl.android.moviebuzz;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {


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

    //TODO fusionner c tois methode en un seul et n'avoir ke une dialog ke je modifi avec setText
   /*renvoi un null pour kan je recupere le text prk??? ************

    public void onClickMaxPoint(View view) {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.dialog_max_point, null);

        //Création de l'AlertDialog
        AlertDialog.Builder adb= new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        int  viewId = view.getId();
        if(viewId==R.id.buttonMaxPoint){
            adb.setTitle("MAX POINTS");
            ????????????????????????????????
            TextView t = (TextView)findViewById(R.id.TextViewDeff);
            t.setText("Entrez le nombre max de points :");
        }
        if(viewId==R.id.buttonContreMontre){
            adb.setTitle("CONTRE LA MONTRE");
            TextView text = (TextView)findViewById(R.id.TextViewDeff);
            text.setText("Entrez le nombre max de minutes desirees :");
        }

        if(viewId==R.id.buttonMaxEpreuve){
            adb.setTitle("MAX EPREUVES");
            TextView text = (TextView)findViewById(R.id.TextViewDeff);
            text.setText("Entrez le nombre max epreuves :");
        }

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton(R.id.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                EditText et = (EditText) alertDialogView.findViewById(R.id.EditText1);

                //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                Toast.makeText(Main2Activity.this, et.getText(), Toast.LENGTH_SHORT).show();
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
    }*/
    public void onClickMaxPoint(View view) {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.dialog_max_point, null);

        //Création de l'AlertDialog
        AlertDialog.Builder adb= new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        adb.setTitle("MAX POINT");

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton(R.id.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                EditText et = (EditText) alertDialogView.findViewById(R.id.EditText1);

                //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                Toast.makeText(Main2Activity.this, et.getText(), Toast.LENGTH_SHORT).show();
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


    public void onClickMaxEpreuve(View view) {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.dialog_max_point, null);

        //Création de l'AlertDialog
        AlertDialog.Builder adb= new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        adb.setTitle("MAX EPREUVE");

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton(R.id.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                EditText et = (EditText) alertDialogView.findViewById(R.id.EditText1);

                //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                Toast.makeText(Main2Activity.this, et.getText(), Toast.LENGTH_SHORT).show();
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

    public void onClickContreMontre(View view) {

        //On instancie notre layout en tant que View
        LayoutInflater factory = LayoutInflater.from(this);
        final View alertDialogView = factory.inflate(R.layout.dialog_max_point, null);

        //Création de l'AlertDialog
        AlertDialog.Builder adb= new AlertDialog.Builder(this);

        //On affecte la vue personnalisé que l'on a crée à notre AlertDialog
        adb.setView(alertDialogView);

        //On donne un titre à l'AlertDialog
        adb.setTitle("CONTRE MONTRE");

        //On modifie l'icône de l'AlertDialog pour le fun ;)
        //adb.setIcon(android.R.drawable.ic_dialog_alert);

        //On affecte un bouton "OK" à notre AlertDialog et on lui affecte un évènement
        adb.setPositiveButton(R.id.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Lorsque l'on cliquera sur le bouton "OK", on récupère l'EditText correspondant à notre vue personnalisée (cad à alertDialogView)
                EditText et = (EditText) alertDialogView.findViewById(R.id.EditText1);

                //On affiche dans un Toast le texte contenu dans l'EditText de notre AlertDialog
                Toast.makeText(Main2Activity.this, et.getText(), Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder adb= new AlertDialog.Builder(this);

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
        AlertDialog.Builder adb= new AlertDialog.Builder(this);

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
