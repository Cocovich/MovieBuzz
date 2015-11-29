package be.ipl.android.moviebuzz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Map;

import be.ipl.android.moviebuzz.model.DAO;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        SharedPreferences settings = getSharedPreferences(OptionsActivity.APP_PREFS, MODE_PRIVATE);
        String player = settings.getString(OptionsActivity.PREF_KEY_PLAYER_NAME, "");

        DAO dao = new DAO(this);
        dao.open();
        Map<String, Integer> stats = dao.getStats(player);
        Map<String, Integer> lastGameStats = dao.getLastGameStats(player);
        dao.close();

        // Statistiques globales
        TextView points = (TextView) findViewById(R.id.statsValueTotalPoints);
        TextView duration = (TextView) findViewById(R.id.statsValueTotalTime);
        TextView games = (TextView) findViewById(R.id.statsValueTotalGames);
        TextView answers = (TextView) findViewById(R.id.statsValueTotalAnswers);
        TextView answersTrue = (TextView) findViewById(R.id.statsValueTotalAnswersTrue);
        TextView answersTrueRatio = (TextView) findViewById(R.id.statsValueRatioAnswersTrue);
        TextView answersFalse = (TextView) findViewById(R.id.statsValueTotalAnswersFalse);
        TextView answersFalseRatio = (TextView) findViewById(R.id.statsValueRatioAnswersFalse);

        int heures = stats.get("Durée") / 3600;
        int minutes = (stats.get("Durée") - heures * 3600) / 60;
        int secondes = stats.get("Durée") % 60;

        int ratioBonnesReponses = 0, ratioMauvaisesReponses = 0;
        if (stats.get("Réponses") > 0) {
            ratioBonnesReponses = 100 * stats.get("Bonnes réponses") / stats.get("Réponses");
            ratioMauvaisesReponses = 100 * stats.get("Mauvaises réponses") / stats.get("Réponses");
        }

        points.setText(String.valueOf(stats.get("Points")));
        duration.setText(String.format("%02d:%02d:%02d", heures, minutes, secondes));
        games.setText(String.valueOf(stats.get("Jeux")));
        answers.setText(String.valueOf(stats.get("Réponses")));
        answersTrue.setText(String.valueOf(stats.get("Bonnes réponses")));
        answersFalse.setText(String.valueOf(stats.get("Mauvaises réponses")));
        if (stats.get("Réponses") > 0) {
            answersTrueRatio.setText(String.format("%02d%%", ratioBonnesReponses));
            answersFalseRatio.setText(String.format("%02d%%", ratioMauvaisesReponses));
        }

        // Dernière partie
        TextView lastGamePoints = (TextView) findViewById(R.id.statsValueLastGamePoints);
        TextView lastGameDuration = (TextView) findViewById(R.id.statsValueLastGameTime);
        TextView lastGameAnswers = (TextView) findViewById(R.id.statsValueLastGameQuestions);

        lastGamePoints.setText(String.valueOf(lastGameStats.get("Points")));
        lastGameDuration.setText(String.format("%02d:%02d", lastGameStats.get("Durée") / 60, lastGameStats.get("Durée") % 60));
        lastGameAnswers.setText(String.valueOf(lastGameStats.get("Réponses")));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
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
