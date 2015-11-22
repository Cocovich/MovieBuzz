package be.ipl.android.moviebuzz;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    public final static String PARAM_NOMBRE_CHOISI = "PARAM_NOMBRE_CHOISI";
    public final static String NOMBRE_POINTS_MAX = "NOMBRE_POINTS_MAX";
    public final static String CONTRE_MONTRE = "CONTRE_MONTRE";

    private int nombreVoulu = 0;
    private String typeEpreuve = "";
    CountDownTimer timerPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //recuperation du param nbr epreuve
        Intent intent = getIntent();
        this.nombreVoulu = intent.getIntExtra(PARAM_NOMBRE_CHOISI, 1);
        //recup type epreuve
        typeEpreuve = intent.getStringExtra(Main2Activity.PARAM_TYPE_EPR_CHOISI);

        /*Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, "type epreuve: " + typeEpreuve + "\nnombre=" + nombreVoulu, duration);
        toast.show();*/

        //timer
        timerPoint = new CountDownTimer(10000, 1000) {
            TextView layouTimer = (TextView) findViewById(R.id.timer);

            public void onTick(long millisUntilFinished) {
                long temp = millisUntilFinished / 1000;
                layouTimer.setText("" + temp);
                if (temp < 5) layouTimer.setTextColor(Color.RED);
                if (temp <= 1) layouTimer.setTextColor(Color.GRAY);
            }

            public void onFinish() {
                layouTimer.setText("0");
            }
        };//.start();

        //si choisi contre la montre
        if (this.typeEpreuve.equals(Main2Activity.TYPE_EPR_CONTRE_MONTRE)) contreMontre();
        //si choisi max epreuves
        if (this.typeEpreuve.equals(Main2Activity.TYPE_EPR_MAX_EPREUVE)) maxEpreuves();
        //si choisi max points
        if (this.typeEpreuve.equals(Main2Activity.TYPE_EPR_MAX_POINT)) maxPoints();

    }

    public void buzz() {

    }

    public void contreMontre() {
        TextView points = (TextView) findViewById(R.id.points);
        final TextView timerContreMontre = (TextView) findViewById(R.id.valeur_indic);
        final TextView labelTimer = (TextView) findViewById(R.id.indicateur);

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
        }.start();
        timerPoint.start();
        buzz();
    }

    public void maxPoints() {
        TextView points = (TextView) findViewById(R.id.points);
        // affichage du nombre max de point
        final TextView maxpoint = (TextView) findViewById(R.id.valeur_indic);

        final TextView indicateurMaxPoint = (TextView) findViewById(R.id.indicateur);
        maxpoint.setText(""+this.nombreVoulu);
        indicateurMaxPoint.setText("Max points");

        timerPoint.start();
        buzz();
    }

    public void maxEpreuves() {
        TextView points = (TextView) findViewById(R.id.points);
        // affichage du nombre max de point
        final TextView resteEpreuve = (TextView) findViewById(R.id.valeur_indic);
        final TextView indicateurMaxEpreuve = (TextView) findViewById(R.id.indicateur);
        resteEpreuve.setText(""+this.nombreVoulu);
        indicateurMaxEpreuve.setText("Restants");

        timerPoint.start();
        buzz();
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
}
