package pl.gieted.timetable.client.javafx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class SceneFactory {

    @Inject
    public SceneFactory() {
    }

    public @NotNull Scene create(@NotNull Parent rootNode, double width, double height) {
        return new Scene(rootNode, width, height);
    }
}
