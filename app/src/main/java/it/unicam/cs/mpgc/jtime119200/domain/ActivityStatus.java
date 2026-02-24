package it.unicam.cs.mpgc.jtime119200.domain;

/**
 * Represents the possible statuses of an {@link Activity}.
 * PLANNED – the activity is scheduled but not yet started.
 * COMPLETED – the activity has been finished successfully.
 * EXPIRED – the activity was not completed in time and is now expired.
 */
public enum ActivityStatus {
    PLANNED,
    COMPLETED,
    EXPIRED
}