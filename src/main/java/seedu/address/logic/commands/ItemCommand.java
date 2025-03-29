package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Abstract command class for operations that involve items in the model.
 * <p>
 * This class serves as a base for commands that operate on {@link Item} objects and need to access
 * an {@link ItemManagerWithFilteredList} to manage the items in the model.
 *
 * @param <T> the type of {@code Item} that the command operates on, which must extend
 *            {@link Item}.
 */
public abstract class ItemCommand<T extends Item> extends Command {

    protected final Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter;

    /**
     * Creates an {@code ItemCommand} with the specified function for retrieving the
     * {@link ItemManagerWithFilteredList}.
     *
     * @param managerAndListGetter a function that retrieves the {@link ItemManagerWithFilteredList}
     *                             for the current model.
     * @throws NullPointerException if {@code managerAndListGetter} is {@code null}.
     */
    public ItemCommand(Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter) {
        requireNonNull(managerAndListGetter);
        this.managerAndListGetter = managerAndListGetter;
    }
}
