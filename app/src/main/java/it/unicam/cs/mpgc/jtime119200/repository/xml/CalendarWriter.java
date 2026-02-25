package it.unicam.cs.mpgc.jtime119200.repository.xml;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.Day;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Writes calendar data into an XML file.
 * Saves projects, days, and activities with their details and status.
 */
public class CalendarWriter {

        private final File file;

        /**
         * Initializes the writer with the specified XML file.
         *
         * @param file the XML file where calendar data will be saved
         */
        public CalendarWriter(File file) {
                this.file = file;
        }

        /**
         * Saves the content of the given JtimeCalendar into the XML file.
         * All projects, days, and activities are written along with their attributes.
         *
         * @param calendar the calendar to save into XML
         */
        public void saveWrite(JtimeCalendar calendar) {
                try {
                        DocumentBuilder builder = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder();
                        Document doc = builder.newDocument();

                        Element root = doc.createElement("Calendar");
                        doc.appendChild(root);

                        // projects
                        Element projectsEl = doc.createElement("Projects");
                        root.appendChild(projectsEl);

                        Set<String> writtenProjects = new HashSet<>();
                        for (Project project : calendar.getProjects()) {
                                if (project != null && writtenProjects.add(project.getName())) {
                                        Element pEl = doc.createElement("Project");
                                        pEl.setAttribute("name", project.getName());
                                        projectsEl.appendChild(pEl);
                                }
                        }

                        // days and activities
                        Element daysEl = doc.createElement("Days");
                        root.appendChild(daysEl);

                        for (Day day : calendar.getAllDays()) {
                                Element dayEl = doc.createElement("Day");
                                dayEl.setAttribute("date", day.getDate().toString());
                                daysEl.appendChild(dayEl);

                                for (Activity activity : day.getActivities()) {
                                        Element actEl = doc.createElement("Activity");

                                        actEl.setAttribute("title", activity.getTitle());
                                        actEl.setAttribute("duration", activity.getExpectedDuration().toString());
                                        actEl.setAttribute("startTime", activity.getStartTime().toString());
                                        actEl.setAttribute("status", activity.getStatus().toString());
                                        actEl.setAttribute("project", activity.getProject().getName());
                                        if (activity.getStatus() == ActivityStatus.COMPLETED) {
                                                actEl.setAttribute("actualDuration", activity.getActualDuration().toString());
                                        }

                                        dayEl.appendChild(actEl);
                                }
                        }

                        Transformer transformer = TransformerFactory
                                .newInstance()
                                .newTransformer();
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        transformer.setOutputProperty(
                                "{http://xml.apache.org/xslt}indent-amount", "2");

                        transformer.transform(
                                new DOMSource(doc),
                                new StreamResult(file)
                        );

                } catch (Exception e) {
                        throw new RuntimeException("Error saving calendar to XML", e);
                }
        }
}