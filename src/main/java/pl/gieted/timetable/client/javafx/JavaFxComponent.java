package pl.gieted.timetable.client.javafx;

import dagger.BindsInstance;
import dagger.Component;
import javafx.stage.Stage;
import pl.gieted.timetable.client.AppModule;

@Component(modules = AppModule.class)
public interface JavaFxComponent {
    StageInitializer stageInitializer();

    @Component.Factory
    interface Factory {
        JavaFxComponent create(@BindsInstance Stage primaryStage);
    }
}
