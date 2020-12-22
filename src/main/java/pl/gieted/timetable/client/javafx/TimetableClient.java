package pl.gieted.timetable.client.javafx;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Map;

public class TimetableClient extends Application {

    @Override
    public void start(Stage primaryStage) {
        Map<String, String> params = getParameters().getNamed();
        String defaultGroupId = params.get("group-id");

        JavaFxComponent component = DaggerJavaFxComponent.factory().create(primaryStage);

        StageInitializer stageInitializer = component.stageInitializer();
        stageInitializer.initialize(primaryStage, defaultGroupId);

        primaryStage.show();
    }
}
