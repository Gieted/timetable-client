package pl.gieted.timetable.client.javafx;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import pl.gieted.timetable.client.timetable.TimetableService;
import pl.gieted.timetable.client.timetable.Group;
import pl.gieted.timetable.client.timetable.scraping.TimetableUpdateException;

import java.util.List;
import java.util.stream.Collectors;

@AutoFactory
public class GroupTreeItem extends TreeItem<Group> {
    private final TimetableService timetableService;
    private final GroupTreeItemFactory factory;
    private boolean childrenLoaded = false;

    public GroupTreeItem(TimetableService timetableService, GroupTreeItemFactory factory) {
        this.timetableService = timetableService;
        this.factory = factory;
    }

    public GroupTreeItem(Group value, @Provided TimetableService timetableService, @Provided GroupTreeItemFactory factory) {
        super(value);
        this.timetableService = timetableService;
        this.factory = factory;
    }

    public GroupTreeItem(Group value, Node graphic, @Provided TimetableService timetableService, @Provided GroupTreeItemFactory factory) {
        super(value, graphic);
        this.timetableService = timetableService;
        this.factory = factory;
    }

    @Override
    public boolean isLeaf() {
        return !getValue().hasChildren();
    }

    @Override
    public ObservableList<TreeItem<Group>> getChildren() {
        if (childrenLoaded) {
            return super.getChildren();
        }

        try {
            List<TreeItem<Group>> options = timetableService.getSublist(getValue()).stream()
                    .map(factory::create)
                    .collect(Collectors.toList());

            super.getChildren().addAll(options);
            this.childrenLoaded = true;
        } catch (TimetableUpdateException e) {
            e.printStackTrace();
        }

        return super.getChildren();
    }
}
