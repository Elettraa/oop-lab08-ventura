package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

/**
 * A DeathNote is a special book intended to be used by Shinigamis (Death gods)
 * to kill humans,
 * thereby extending their own lives. Each DeathNote has a set of rules that
 * must be followed
 * in order to use it properly.
 */
public class DeathNoteImplementation implements DeathNote {
    private static Map<String, DeathDescription> deathnote;
    private static String lastName = null;
    private static String defaultCauseOfDeath = "heart attack";
    private static final int TIME_CAUSES = 40;
    private static final int TIME_DETAILS = 6000;
    private static long start_time = 0;
    private static long final_time = 0;

    public DeathNoteImplementation() {
        deathnote = new HashMap<>();
    }

    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber < 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("This rule does not exist");
        }
        return (RULES.get(ruleNumber));
    }

    @Override
    public void writeName(String name) {
        if (name == null) {
            throw new NullPointerException("No name given");
        }
        deathnote.put(name, new DeathDescription(defaultCauseOfDeath, ""));
        lastName = name;
        start_time = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if (cause == null) {
            throw new NullPointerException("No cause given");
        }
        final_time = System.currentTimeMillis();
        if (final_time - start_time > TIME_CAUSES) {
            return false;
        }
        deathnote.get(lastName).setCauseOfDeath(cause);
        deathnote.put(lastName, deathnote.get(lastName));
        return true;
    }

    @Override
    public boolean writeDetails(String details) {
        if (details == null) {
            throw new NullPointerException("No details given");
        }
        final_time = System.currentTimeMillis();
        if (final_time - start_time > TIME_DETAILS + TIME_CAUSES) {
            return false;
        }
        deathnote.get(lastName).setDeathDetails(details);
        deathnote.put(lastName, deathnote.get(lastName));
        return true;
    }

    @Override
    public String getDeathCause(String name) {
        if (!deathnote.containsKey(name)) {
            throw new IllegalArgumentException("This name is not in the deathnote");
        }
        return deathnote.get(name).getCauseOfDeath();
    }

    @Override
    public String getDeathDetails(String name) {
        if (!deathnote.containsKey(name)) {
            throw new IllegalArgumentException("This name is not in the deathnote");
        }
        return deathnote.get(name).getDeathDetails();
    }

    @Override
    public boolean isNameWritten(String name) {
        return deathnote.containsKey(name);
    }

    private class DeathDescription {
        private String causeOfDeath;
        private String deathDetails;

        private DeathDescription(final String cause, final String details) {
            this.causeOfDeath = cause;
            this.deathDetails = details;
        }

        public String getCauseOfDeath() {
            return this.causeOfDeath;
        }

        public String getDeathDetails() {
            return this.deathDetails;
        }

        public void setCauseOfDeath(String newCause) {
            this.causeOfDeath = newCause;
        }

        public void setDeathDetails(String newDeatils) {
            this.deathDetails = newDeatils;
        }
    }

}