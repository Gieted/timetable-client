package pl.gieted.timetable.client.timetable;

import com.google.auto.factory.AutoFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AutoFactory
public class Group {
    private final @NotNull String name;
    private final @NotNull String id;

    private final @Nullable Group parent;
    private final boolean hasChildren;
    private final boolean selectable;

    public Group(@NotNull String name,
                 @NotNull String id,
                 @Nullable Group parent,
                 boolean hasChildren,
                 boolean selectable) {

        this.name = name;
        this.id = id;
        this.parent = parent;
        this.hasChildren = hasChildren;
        this.selectable = selectable;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getId() {
        return id;
    }

    public @Nullable Group getParent() {
        return parent;
    }

    public boolean hasChildren() {
        return hasChildren;
    }

    public boolean isSelectable() {
        return selectable;
    }
}
