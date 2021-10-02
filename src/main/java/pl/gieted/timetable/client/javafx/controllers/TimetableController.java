package pl.gieted.timetable.client.javafx.controllers;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import pl.gieted.timetable.client.Range;
import pl.gieted.timetable.client.javafx.NoSelectionModelFactory;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.Timetable;
import pl.gieted.timetable.client.timetable.TimetableService;
import pl.gieted.timetable.client.timetable.scraping.TimetableUpdateException;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@AutoFactory
public class TimetableController implements Initializable {
    private final Group group;
    private final TimetableService timetableService;
    private final NoSelectionModelFactory noSelectionModelFactory;

    @FXML
    private ListView<Timetable.Entry> monday;

    @FXML
    private ListView<Timetable.Entry> tuesday;

    @FXML
    private ListView<Timetable.Entry> wednesday;

    @FXML
    private ListView<Timetable.Entry> thursday;

    @FXML
    private ListView<Timetable.Entry> friday;

    public TimetableController(Group group, @Provided TimetableService timetableService, @Provided NoSelectionModelFactory noSelectionModelFactory) {
        this.group = group;
        this.timetableService = timetableService;
        this.noSelectionModelFactory = noSelectionModelFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<ListView<Timetable.Entry>> listViews = Arrays.asList(monday, tuesday, wednesday, thursday, friday);
        listViews.forEach(listView -> {
            listView.setFocusTraversable(false);
            listView.setSelectionModel(noSelectionModelFactory.create());
        });
        listViews.forEach(listView -> listView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Timetable.Entry event, boolean empty) {
                super.updateItem(event, empty);

                double width = param.getWidth() - 30;
                setMinWidth(width);
                setMaxWidth(width);
                setPrefWidth(width);
                setWrapText(true);

                if (event != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    setText("%s-%s %s".formatted(dateFormat.format(event.getStartDate()), dateFormat.format(event.getEndDate()), event.getTitle()));
                }
            }
        }));

        try {
            int currentWeek = timetableService.getCurrentWeek(group);
            Timetable timetable = timetableService.getTimetable(group, new Range(currentWeek, currentWeek));
            Timetable.Week week = timetable.getWeeks().get(0);
            week.getEvents().forEach(event -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(event.getStartDate());
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

                listViews.get(dayOfWeek - 2).getItems().add(event);
            });

        } catch (TimetableUpdateException e) {
            e.printStackTrace();
        }
    }
}
