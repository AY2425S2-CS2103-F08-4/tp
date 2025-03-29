package seedu.address.logic.commands.delete;

import static java.util.Objects.requireNonNull;

import java.util.function.Function;
import java.util.function.Supplier;

import seedu.address.logic.commands.ItemCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Abstract command to clear the list of {@code Item} objects in the model.
 *
 * @param <T> the type of {@code Item} being cleared, which must extend {@link Item}.
 */
public abstract class ClearCommand<T extends Item> extends ItemCommand<T> {
    protected final Supplier<ItemManager<T>> emptyItemManagerSupplier;

    /**
     * Creates a {@code ClearCommand} to clear the item list in the model.
     */
    public ClearCommand(Function<Model, ItemManagerWithFilteredList<T>> managerAndListGetter,
                        Supplier<ItemManager<T>> emptyItemManagerSupplier) {
        super(managerAndListGetter);
        this.emptyItemManagerSupplier = emptyItemManagerSupplier;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = managerAndListGetter.apply(model);
        managerAndList.setItemManager(emptyItemManagerSupplier.get());
        return new CommandResult(getSuccessMessage());
    }

    /**
     * Returns the success message to be displayed when the item list is cleared.
     */
    public abstract String getSuccessMessage();
}
