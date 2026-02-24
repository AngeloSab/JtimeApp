package it.unicam.cs.mpgc.jtime119200.controllers.activityHandler;

/**
 * Contract for controllers responsible for handling activity-related actions.
 * This interface defines the common behavior required to trigger
 * the activity handling workflow (creation, modification or removal).
 * Implementing classes encapsulate the specific logic needed to
 * manage an activity and expose a unified method that can be
 * invoked by higher-level controllers.
 */
public interface HandleActivityInterface {
    void showActivityHandlerSignal();
}