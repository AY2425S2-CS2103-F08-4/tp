package seedu.address.logic.commands.read;

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
 * Abstract command to display information about an {@code Item} from the model based on a given
 * index.
 *
 * @param <T> the type of {@code Item} whose information is being displayed, which must extend
 *            {@link Item}.
 */
public abstract class InfoCommand<T extends Item> extends ItemCommand<T> {
    protected final Index index;

    /**
     * Creates a {@code InfoCommand} to display information of the {@code Item} at the
     * specified {@code index}.
     *
     * @throws NullPointerException if {@code index} or {@code managerAndListGetter} is
     *                              {@code null}.
     */
    public InfoCommand(Index index, Function<Model,
            ItemManagerWithFilteredList<T>> managerAndListGetter) {
        super(managerAndListGetter);
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        List<T> lastShownList = managerAndList.getFilteredItemsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        T itemToDisplay = lastShownList.get(index.getZeroBased());
        return new CommandResult(getInformationMessage(itemToDisplay));
    }

    /**
     * Returns the message to be displayed when the provided {@code index} is invalid.
     */
    public abstract String getInvalidIndexMessage();

    /**
     * Returns the information message to be displayed for the given {@code item}.
     */
    public abstract String getInformationMessage(T itemToDisplay);
}
