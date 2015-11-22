package be.ipl.android.moviebuzz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    public final static String PARAM_NOMBRE_CHOISI = "PARAM_NOMBRE_CHOISI";
    public final static String NOMBRE_POINTS_MAX = "NOMBRE_POINTS_MAX";
    public final static String CONTRE_MONTRE = "CONTRE_MONTRE";

    private int nombreVoulu = 0;
    private String typeEpreuve="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //recuperation du param nbr epreuve
        Intent intent = getIntent();
        this.nombreVoulu = intent.getIntExtra(PARAM_NOMBRE_CHOISI,1);
        //recup type epreuve
        typeEpreuve=intent.getStringExtra(Main2Activity.PARAM_TYPE_EPR_CHOISI);

        //test debug
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, "type epreuve: "+typeEpreuve+"\nnombre="+nombreVoulu, duration);
        toast.show();
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
