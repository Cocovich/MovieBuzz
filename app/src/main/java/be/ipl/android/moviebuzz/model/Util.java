package be.ipl.android.moviebuzz.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class Util {

    public static String join(Collection<String> propositions, String delimiter) {
        Iterator<String> it = propositions.iterator();
        String props = "";
        while (it.hasNext()) {
            props += it.next();
            if (it.hasNext()) props += delimiter;
        }
        return props;
    }

    public static Propositions split(String string, String delimiter) {
        String[] array = string.split(delimiter);
        Propositions props = new Propositions();
        Collections.addAll(props, array);
        return props;
    }
}
