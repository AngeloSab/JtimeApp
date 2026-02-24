package it.unicam.cs.mpgc.jtime119200.repository.xml;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.repository.CalendarRepository;

import java.io.File;

public class CalendarRepositoryXML implements CalendarRepository {

    private final CalendarReader reader;
    private final CalendarWriter writer;


    public CalendarRepositoryXML() {
        File xmlFile = new File(System.getProperty("user.home"), "calendar_data.xml");
        this.reader = new CalendarReader(xmlFile);
        this.writer = new CalendarWriter(xmlFile);
    }

    @Override
    public void load(JtimeCalendar calendar) {
        reader.saveRead(calendar);
    }

    @Override
    public void save(JtimeCalendar calendar) {
        writer.saveWrite(calendar);
    }
}