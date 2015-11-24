package be.ipl.android.moviebuzz.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class Util {

    public static String join(String[] propositions, String delimiter) {
        String props = "";
        for (String prop : propositions) {
            props += prop+";";
        }
        return props.substring(0, props.length()-1);
    }
}
