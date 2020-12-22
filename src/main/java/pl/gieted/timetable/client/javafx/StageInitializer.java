package pl.gieted.timetable.client.javafx;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.GroupFactory;

import javax.inject.Inject;
import javax.inject.Named;

public class StageInitializer {
    private final String applicationName;
    private final Scene defaultScene;
    private final Image calendarIcon;
    private final GroupFactory groupFactory;
    private final TimetableSceneFactory timetableSceneFactory;

    @Inject
    public StageInitializer(@Named("applicationName") String applicationName,
                            @Named("default") Scene defaultScene,
                            @Named("calendar") Image calendarIcon,
                            GroupFactory groupFactory,
                            TimetableSceneFactory timetableSceneFactory) {

        this.applicationName = applicationName;
        this.defaultScene = defaultScene;
        this.calendarIcon = calendarIcon;
        this.groupFactory = groupFactory;
        this.timetableSceneFactory = timetableSceneFactory;
    }

    public void initialize(@NotNull Stage stage, @Nullable String defaultGroupId) {
        if (defaultGroupId != null) {
            Group defaultGroup = groupFactory.create("", defaultGroupId, null, false, true);
            Scene timetableScene = timetableSceneFactory.create(defaultGroup);
            stage.setScene(timetableScene);
        } else {
            stage.setScene(defaultScene);
        }

        stage.setTitle(applicationName);
        stage.getIcons().add(calendarIcon);
    }
}
