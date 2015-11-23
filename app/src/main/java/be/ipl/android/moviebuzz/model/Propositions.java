package be.ipl.android.moviebuzz.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Propositions extends HashSet<String> {

    private static final String DELIMITER = ";";

    public Propositions() {
        super(5, 1);
    }

    public Propositions(String[] propositions) {
        super(propositions.length+1, 1);
        Collections.addAll(this, propositions);
    }

    private Propositions(int capacity) {}
    private Propositions(int capacity, float loadFactor) {}
    private Propositions(Collection<? extends String> collection) {}


}
