package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;
import static java.lang.Thread.sleep;

class TestDeathNote {
    private static final int TIME_CAUSES = 100;
    private static final int TIME_DETAILS = 6100;
    private DeathNote myDeathNote;
    private String humanName;
    private String anotherHumanName;

    @BeforeEach
    public void setUp() {
        this.myDeathNote = new DeathNoteImplementation();
        this.humanName = "Abi Cidieeffe";
        this.anotherHumanName = "Gia Caielle";
    }

    @Test
    public void NegativeRule() {
        try {
            myDeathNote.getRule(-1);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

    @Test
    public void RuleZero() {
        try {
            myDeathNote.getRule(0);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

    @Test
    public void EmptyOrNullRule() {
        for (String rule : DeathNote.RULES) {
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    public void AllHumansWillDie() {
        assertFalse(myDeathNote.isNameWritten(humanName));
        myDeathNote.writeName(humanName);
        assertTrue(myDeathNote.isNameWritten(humanName));
        assertFalse(myDeathNote.isNameWritten(anotherHumanName));
        assertFalse(myDeathNote.isNameWritten(""));
    }

    @Test
    public void CauseOfDeath() {
        try {
            myDeathNote.writeDeathCause("Injuries");
        } catch (NullPointerException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        myDeathNote.writeName(anotherHumanName);
        assertEquals(myDeathNote.getDeathCause(anotherHumanName), "heart attack");
        myDeathNote.writeName(humanName);
        myDeathNote.writeDeathCause("karting accident");
        assertEquals("karting accident", myDeathNote.getDeathCause(humanName));
        try {
            sleep(TIME_CAUSES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myDeathNote.writeDeathCause("Injuries");
        assertEquals("karting accident", myDeathNote.getDeathCause(humanName));
    }

    @Test
    public void DeathDetails() {
        try {
            myDeathNote.writeDetails("Ran over by a bus");
        } catch (NullPointerException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        myDeathNote.writeName(anotherHumanName);
        assertEquals("", myDeathNote.getDeathDetails(anotherHumanName));
        myDeathNote.writeDetails("ran for too long");
        assertEquals("ran for too long", myDeathNote.getDeathDetails(anotherHumanName));
        myDeathNote.writeName(humanName);
        try {
            sleep(TIME_DETAILS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myDeathNote.writeDetails("Got in a car accident");
        assertEquals("", myDeathNote.getDeathDetails(humanName));
    }

}