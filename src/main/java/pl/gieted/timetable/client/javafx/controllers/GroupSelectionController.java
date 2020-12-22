package pl.gieted.timetable.client.javafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import pl.gieted.timetable.client.javafx.GroupTreeItemFactory;
import pl.gieted.timetable.client.javafx.TimetableSceneFactory;
import pl.gieted.timetable.client.timetable.TimetableService;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.scraping.TimetableUpdateException;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GroupSelectionController implements Initializable {
    private final TimetableService timetableService;
    private final GroupTreeItemFactory treeItemFactory;
    private final Stage primaryStage;
    private final TimetableSceneFactory timetableSceneFactory;

    @FXML
    private TreeView<Group> timetableSelectionTree;

    @FXML
    private Button nextButton;

    @Inject
    public GroupSelectionController(TimetableService timetableService,
                                    GroupTreeItemFactory treeItemFactory,
                                    Stage primaryStage,
                                    TimetableSceneFactory timetableSceneFactory) {

        this.timetableService = timetableService;
        this.treeItemFactory = treeItemFactory;
        this.primaryStage = primaryStage;
        this.timetableSceneFactory = timetableSceneFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timetableSelectionTree.setCellFactory(tree -> new TreeCell<>() {

            @Override
            protected void updateItem(Group item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    setText(item.getName());
                    if (item.isSelectable()) {
                        setStyle("-fx-text-fill: #3d3def");
                    } else {
                        setStyle("-fx-text-fill: black");
                    }
                }
            }
        });

        try {
            List<TreeItem<Group>> options = timetableService.getRootList().stream()
                    .map(treeItemFactory::create)
                    .collect(Collectors.toList());

            TreeItem<Group> root = new TreeItem<>();
            root.getChildren().addAll(options);
            root.setExpanded(true);
            timetableSelectionTree.setRoot(root);
        } catch (TimetableUpdateException e) {
            e.printStackTrace();
        }

        timetableSelectionTree.setOnMouseClicked(event -> {
            TreeItem<Group> selectedGroupItem = timetableSelectionTree.getSelectionModel().getSelectedItem();
            if (selectedGroupItem != null) {
                Group selectedGroup = selectedGroupItem.getValue();
                nextButton.setDisable(!selectedGroup.isSelectable());
            } else {
                nextButton.setDisable(true);
            }
        });
    }

    public void selectGroup() {
        Group selectedGroup = timetableSelectionTree.getSelectionModel().getSelectedItem().getValue();
        Scene scene = timetableSceneFactory.create(selectedGroup);
        primaryStage.setScene(scene);
    }
}
