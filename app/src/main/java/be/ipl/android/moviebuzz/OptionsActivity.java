package be.ipl.android.moviebuzz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class OptionsActivity extends AppCompatActivity {

    public static final String PREF_KEY_PLAYER_NAME = "player_name";
    public static final String APP_PREFS = "APP_PREFS";

    protected EditText playerView;
    protected String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        SharedPreferences settings = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        playerName = settings.getString(PREF_KEY_PLAYER_NAME, null);

        playerView = (EditText) findViewById(R.id.optionsValuePlayerName);

        if (playerName != null)
            playerView.setText(playerName);
    }

    public void onSave(View view) {
        String name = playerView.getText().toString();

        if (!name.equals("") && !name.equals(playerName)) {
            SharedPreferences settings = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(PREF_KEY_PLAYER_NAME, name);
            editor.commit();
        }

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

}
