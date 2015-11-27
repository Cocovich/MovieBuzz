package be.ipl.android.moviebuzz.model;

import java.util.Set;

public class Util {

    public static String join(Set<String> propositions) {
        String props = "";
        for (String prop : propositions)
            props += prop + Epreuve.Propositions.DELIMITER;
        return props.substring(0, props.length()-1);
    }

    public static String[] split(String propositions) {
        return propositions.split(Epreuve.Propositions.DELIMITER);
    }
}
