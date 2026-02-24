package it.unicam.cs.mpgc.jtime119200.controllers.completeHandler;

/**
 * Contract for controllers responsible for completing an activity.
 * This interface defines the operation required to trigger
 * the completion workflow of an existing activity.
 * Implementing classes provide the specific logic needed to
 * mark an activity as completed and update the system state
 * accordingly.
 */
public interface CompleteActivityInterface {
    void showCompleteActivity();
}