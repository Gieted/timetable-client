package pl.gieted.timetable.client.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import pl.gieted.timetable.client.javafx.controllers.TimetableControllerFactory;
import pl.gieted.timetable.client.timetable.Group;
import javax.inject.Inject;
import java.io.IOException;

public class TimetableSceneFactory {
    private final TimetableControllerFactory timetableControllerFactory;
    private final SceneFactory sceneFactory;

    @Inject
    public TimetableSceneFactory(TimetableControllerFactory timetableControllerFactory, SceneFactory sceneFactory) {
        this.timetableControllerFactory = timetableControllerFactory;
        this.sceneFactory = sceneFactory;
    }

    public Scene create(Group group) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(JavaFxModule.class.getResource("/scenes/timetable.fxml"));
            fxmlLoader.setControllerFactory(param -> timetableControllerFactory.create(group));

            Parent rootNode = fxmlLoader.load();
            int defaultWidth = 1280;
            int defaultHeight = 720;

            return sceneFactory.create(rootNode, defaultWidth, defaultHeight);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }
}
