package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Function;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ItemCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Abstract command to delete an {@code Item} from the model based on a given index.
 *
 * @param <T> the type of {@code Item} being deleted, which must extend {@link Item}.
 */
public abstract class DeleteCommand<T extends Item> extends ItemCommand<T> {
    protected final Index targetIndex;

    /**
     * Creates a {@code DeleteCommand} to delete an {@code Item} at the specified
     * {@code targetIndex}.
     *
     * @throws NullPointerException if {@code targetIndex} or {@code managerAndListGetter} is
     *                              {@code null}.
     */
    public DeleteCommand(Index targetIndex,
                         Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter) {
        super(managerAndListGetter);
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        List<T> lastShownList = managerAndList.getFilteredItemsList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        T itemToDelete = lastShownList.get(targetIndex.getZeroBased());
        managerAndList.deleteItem(itemToDelete);
        return new CommandResult(getSuccessMessage(itemToDelete));
    }

    /**
     * Returns the message to be displayed when the provided {@code targetIndex} is invalid.
     */
    public abstract String getInvalidIndexMessage();

    /**
     * Returns the success message to be displayed when an {@code Item} is successfully deleted.
     */
    public abstract String getSuccessMessage(T item);
}
