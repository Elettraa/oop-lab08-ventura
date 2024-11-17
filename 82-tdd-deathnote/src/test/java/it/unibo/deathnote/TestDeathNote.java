package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImplementation;

class TestDeathNote {
    private static final int TIME_CAUSES = 100;
    private static final int TIME_DETAILS = 6500;
    private DeathNote myDeathNote;
    private String HUMAN_NAME;
    private String ANOTHER_HUMAN_NAME;

    @BeforeEach
    public void setUp() {
        this.myDeathNote = new DeathNoteImplementation();
        this.HUMAN_NAME = "Marco Rossi";
        this.ANOTHER_HUMAN_NAME = "Giulio Bianchi";
    }

    @Test
    public void NegativeRule() {
        try {
            myDeathNote.getRule(-1);
            fail("Expected IllegalArgumentException to be thrown");
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
            fail("Expected IllegalArgumentException to be thrown");
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
        assertFalse(myDeathNote.isNameWritten(HUMAN_NAME));
        myDeathNote.writeName(HUMAN_NAME);
        assertTrue(myDeathNote.isNameWritten(HUMAN_NAME));
        assertFalse(myDeathNote.isNameWritten(ANOTHER_HUMAN_NAME));
        assertFalse(myDeathNote.isNameWritten(""));
    }

    @Test
    public void CauseOfDeath() {
        try {
            myDeathNote.writeDeathCause("Injuries");
            fail("Expected Exception to be thrown");
        } catch (NullPointerException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        myDeathNote.writeName(ANOTHER_HUMAN_NAME);
        assertEquals(myDeathNote.getDeathCause(ANOTHER_HUMAN_NAME), "heart attack");
        myDeathNote.writeName(HUMAN_NAME);
        myDeathNote.writeDeathCause("karting accident");
        assertEquals("karting accident", myDeathNote.getDeathCause(HUMAN_NAME));
        try {
            Thread.sleep(TIME_CAUSES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myDeathNote.writeDeathCause("Injuries");
        assertEquals("karting accident", myDeathNote.getDeathCause(HUMAN_NAME));
    }

    @Test
    public void DeathDetails() {
        try {
            myDeathNote.writeDetails("Ran over by a bus");
            fail("Expected Exception to be thrown");
        } catch (NullPointerException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
        myDeathNote.writeName(ANOTHER_HUMAN_NAME);
        assertEquals("", myDeathNote.getDeathDetails(ANOTHER_HUMAN_NAME));
        myDeathNote.writeDetails("ran for too long");
        assertEquals("ran for too long", myDeathNote.getDeathDetails(ANOTHER_HUMAN_NAME));
        myDeathNote.writeName(HUMAN_NAME);
        try {
            Thread.sleep(TIME_DETAILS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myDeathNote.writeDetails("Got in a car accident");
        assertEquals("", myDeathNote.getDeathDetails(HUMAN_NAME));
    }

}