package seedu.address.logic.commands.read;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;

import seedu.address.logic.commands.ItemCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;


/**
 * A command to list all {@code T} items in the model.
 *
 * @param <T> the type of {@code Item} being listed, which must extend {@link Item}.
 */
public abstract class ListCommand<T extends Item> extends ItemCommand<T> {

    /**
     * Creates a {@code ListCommand}.
     */
    public ListCommand(Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter) {
        super(managerAndListGetter);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        managerAndList.showAllItems();
        return new CommandResult(getSuccessMessage());
    }

    /**
     * Returns a success message to be displayed when the command executes successfully.
     */
    public abstract String getSuccessMessage();
}
