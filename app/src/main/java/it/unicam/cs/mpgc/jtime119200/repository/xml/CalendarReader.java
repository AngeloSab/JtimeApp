package it.unicam.cs.mpgc.jtime119200.repository.xml;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.domain.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads calendar data from an XML file and loads it into a JtimeCalendar instance.
 * It reconstructs projects, days, and activities with their status.
 */
public class CalendarReader {

    private final File file;

    /**
     * Initializes the reader with the given XML file.
     *
     * @param file the XML file containing calendar data
     */
    public CalendarReader(File file) {
        this.file = file;
    }

    /**
     * Loads the calendar data from the XML file into the provided JtimeCalendar.
     * Projects, days, and activities are recreated along with their status.
     *
     * @param calendar the calendar to populate with data from XML
     */
    public void saveRead(JtimeCalendar calendar) {
        if (!file.exists()) return; // niente da leggere
        DocumentBuilder builder;
        try {
            builder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc;
        try {
            doc = builder.parse(file);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
        doc.getDocumentElement().normalize();

        // projects
        Map<String, Project> projectMap = new HashMap<>();
        NodeList projectNodes = doc.getElementsByTagName("Project");
        for (int i = 0; i < projectNodes.getLength(); i++) {
            Element pElem = (Element) projectNodes.item(i);
            String name = pElem.getAttribute("name");

            Project project = new Project(name);
            calendar.addProject(project);
            projectMap.put(name, project);
        }

        // days and activities
        NodeList dayNodes = doc.getElementsByTagName("Day");
        for (int i = 0; i < dayNodes.getLength(); i++) {
            Element dayElem = (Element) dayNodes.item(i);
            String dateStr = dayElem.getAttribute("date");

            LocalDate date = LocalDate.parse(dateStr);

            NodeList activityNodes = dayElem.getElementsByTagName("Activity");
            for (int j = 0; j < activityNodes.getLength(); j++) {
                Element aElem = (Element) activityNodes.item(j);

                String title = aElem.getAttribute("title");

                String durationStr = aElem.getAttribute("duration");
                Duration duration = Duration.parse(durationStr);

                String startTimeStr = aElem.getAttribute("startTime");
                Instant startTime = Instant.parse(startTimeStr);

                String statusStr = aElem.getAttribute("status");
                ActivityStatus status = ActivityStatus.valueOf(statusStr);

                String projectName = aElem.getAttribute("project");
                Project project = projectMap.get(projectName);

                Activity activity = calendar.createActivity(
                        project, title, duration, startTime, date
                );

                switch (status) {
                    case COMPLETED -> {
                        String actualDurStr = aElem.getAttribute("actualDuration");
                        Duration actualDuration = Duration.parse(actualDurStr);
                        activity.complete(actualDuration);
                    }
                    case EXPIRED -> activity.expire();
                    case PLANNED -> {}
                }
            }
        }
    }
}