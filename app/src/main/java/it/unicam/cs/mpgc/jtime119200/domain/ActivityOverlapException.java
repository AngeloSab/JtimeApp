package it.unicam.cs.mpgc.jtime119200.domain;

/**
 * Exception thrown when attempting to schedule an activity that overlaps
 * with an existing activity in the same time period.
 * This class extends {@link RuntimeException} and is used to signal
 * violations of scheduling constraints within the calendar domain.
 */
public class ActivityOverlapException extends RuntimeException {

    /**
     * Constructs a new ActivityOverlapException with the specified detail message.
     *
     * @param message the detail message explaining the overlap conflict
     */
    public ActivityOverlapException(String message) {
        super(message);
    }
}