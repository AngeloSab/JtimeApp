package it.unicam.cs.mpgc.jtime119200.xml;

import it.unicam.cs.mpgc.jtime119200.Activity;
import it.unicam.cs.mpgc.jtime119200.Day;
import it.unicam.cs.mpgc.jtime119200.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.Project;
import it.unicam.cs.mpgc.jtime119200.ActivityStatus;
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

public class CalendarWriter {

        private final File file;

        public CalendarWriter(File file) {
                this.file = file;
        }

        public void saveWrite(JtimeCalendar calendar) {
                try {
                        DocumentBuilder builder = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder();
                        Document doc = builder.newDocument();

                        // ROOT
                        Element root = doc.createElement("Calendar");
                        doc.appendChild(root);

                        // PROJECTS
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

                        // DAYS + ACTIVITIES
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
