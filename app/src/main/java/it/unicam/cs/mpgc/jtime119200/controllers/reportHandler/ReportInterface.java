package it.unicam.cs.mpgc.jtime119200.controllers.reportHandler;

/**
 * Contract for controllers responsible for generating or displaying reports.
 * This interface defines the operation required to trigger
 * the report visualization workflow.
 * Implementing classes encapsulate the logic needed to
 * collect data and present project-related statistics
 * or summaries.
 */
public interface ReportInterface {
    void showReportSignal();
}