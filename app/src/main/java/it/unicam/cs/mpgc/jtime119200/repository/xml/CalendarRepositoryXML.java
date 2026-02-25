package it.unicam.cs.mpgc.jtime119200.repository.xml;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.repository.CalendarRepository;

import java.io.File;

/**
 * XML-based implementation of the CalendarRepository interface.
 * Saves and loads a JtimeCalendar from a local XML file.
 */
public class CalendarRepositoryXML implements CalendarRepository {

    private final CalendarReader reader;
    private final CalendarWriter writer;

    /**
     * Initializes the repository with a default XML file located in the user's home directory.
     */
    public CalendarRepositoryXML() {
        File xmlFile = new File(System.getProperty("user.home"), "calendar_data.xml");
        this.reader = new CalendarReader(xmlFile);
        this.writer = new CalendarWriter(xmlFile);
    }

    /**
     * Loads the calendar data from the XML file into the given calendar instance.
     *
     * @param calendar the calendar instance to populate
     */
    @Override
    public void load(JtimeCalendar calendar) {
        reader.saveRead(calendar);
    }

    /**
     * Saves the state of the given calendar to the XML file.
     *
     * @param calendar the calendar instance to save
     */
    @Override
    public void save(JtimeCalendar calendar) {
        writer.saveWrite(calendar);
    }
}