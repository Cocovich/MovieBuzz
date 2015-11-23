package be.ipl.android.moviebuzz.model;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import be.ipl.android.moviebuzz.R;

public class GameCursorAdapter extends CursorAdapter {

    public GameCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Epreuve epreuve = DAO.getEpreuveFromCursor(cursor);
        // Lookup views
        ImageView image = (ImageView) view.findViewById(R.id.gameImage);
        TextView question = (TextView) view.findViewById(R.id.gameQuestion);
        RadioButton prop1 = (RadioButton) view.findViewById(R.id.gameProp1);
        RadioButton prop2 = (RadioButton) view.findViewById(R.id.gameProp2);
        RadioButton prop3 = (RadioButton) view.findViewById(R.id.gameProp3);
        RadioButton prop4 = (RadioButton) view.findViewById(R.id.gameProp4);
        Iterator<String> it = epreuve.getPropositions().iterator();
        // Populate data
        question.setText(epreuve.getQuestion());
        prop1.setText(it.next());
        prop2.setText(it.next());
        prop3.setText(it.next());
        prop4.setText(it.next());
        if (epreuve.getCheminImage() != null) {
            // get input stream
            try {
                InputStream ims = context.getAssets().open(epreuve.getCheminImage());
                // load image as Drawable
                Drawable d = Drawable.createFromStream(ims, null);
                // set image to ImageView
                image.setImageDrawable(d);
            }
            catch(IOException ignored) {}
        }
    }
}
