package pl.gieted.timetable.client.javafx;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pl.gieted.timetable.client.javafx.controllers.GroupSelectionController;

import javax.inject.Named;
import javax.inject.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

@Module
public abstract class JavaFxModule {

    @Provides
    @Named("groupSelection")
    public static Scene welcomeScene(SceneFactory sceneFactory, Provider<GroupSelectionController> groupSelectionControllerProvider) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(JavaFxModule.class.getResource("/scenes/group_selection.fxml"));
            fxmlLoader.setControllerFactory(param -> groupSelectionControllerProvider.get());

            Parent rootNode = fxmlLoader.load();
            int defaultWidth = 1280;
            int defaultHeight = 720;

            return sceneFactory.create(rootNode, defaultWidth, defaultHeight);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionError();
        }
    }

    @Binds
    @Named("default")
    public abstract Scene defaultScene(@Named("groupSelection") Scene editorScene);

    @Provides
    @Named("calendar")
    public static Image calendarIcon() {
        InputStream iconStream = JavaFxModule.class.getClassLoader().getResourceAsStream("icons/calendar.png");

        if (iconStream == null) {
            throw new AssertionError();
        }

        return new Image(iconStream);
    }
}
