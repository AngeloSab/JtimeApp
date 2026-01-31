package it.unicam.cs.mpgc.jtime119200.xml;

import it.unicam.cs.mpgc.jtime119200.Activity;
import it.unicam.cs.mpgc.jtime119200.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.Project;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CalendarReader {

    private final File file;

    public CalendarReader(File file) {
        this.file = file;
    }

    public void saveRead(JtimeCalendar calendar) {
        if (!file.exists()) return; // niente da leggere

        try {
            DocumentBuilder builder = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            // --------------------------
            // 1️⃣ Carica i progetti
            // --------------------------
            Map<String, Project> projectMap = new HashMap<>();
            NodeList projectNodes = doc.getElementsByTagName("Project");
            for (int i = 0; i < projectNodes.getLength(); i++) {
                Element pElem = (Element) projectNodes.item(i);
                String name = pElem.getAttribute("name");
                if (name == null || name.isEmpty()) continue;

                Project project = new Project(name);
                calendar.addProject(project);
                projectMap.put(name, project);
            }

            // --------------------------
            // 2️⃣ Carica i giorni e le attività
            // --------------------------
            NodeList dayNodes = doc.getElementsByTagName("Day");
            for (int i = 0; i < dayNodes.getLength(); i++) {
                Element dayElem = (Element) dayNodes.item(i);
                String dateStr = dayElem.getAttribute("date");
                if (dateStr == null || dateStr.isEmpty()) continue;

                LocalDate date = LocalDate.parse(dateStr);

                NodeList activityNodes = dayElem.getElementsByTagName("Activity");
                for (int j = 0; j < activityNodes.getLength(); j++) {
                    Element aElem = (Element) activityNodes.item(j);

                    String title = aElem.getAttribute("title");
                    if (title == null || title.isEmpty()) continue;

                    String durationStr = aElem.getAttribute("duration");
                    if (durationStr == null || durationStr.isEmpty()) continue;
                    Duration duration = Duration.parse(durationStr);

                    String startTimeStr = aElem.getAttribute("startTime");
                    if (startTimeStr == null || startTimeStr.isEmpty()) continue;
                    Instant startTime = Instant.parse(startTimeStr);

                    String statusStr = aElem.getAttribute("status");
                    ActivityStatus status = (statusStr == null || statusStr.isEmpty())
                            ? ActivityStatus.PLANNED
                            : ActivityStatus.valueOf(statusStr);

                    String projectName = aElem.getAttribute("project");
                    Project project = projectMap.get(projectName);

                    Activity activity = calendar.createActivity(
                            project, title, duration, startTime, date
                    );

                    // Gestione status
                    switch (status) {
                        case COMPLETED -> {
                            String actualDurStr = aElem.getAttribute("actualDuration");
                            if (actualDurStr != null && !actualDurStr.isEmpty()) {
                                Duration actualDuration = Duration.parse(actualDurStr);
                                activity.complete(actualDuration);
                            }
                        }
                        case EXPIRED -> activity.expire();
                        case PLANNED -> {} // non fare nulla
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
