package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

public abstract class AddCommand<T extends Item> extends ItemCommand<T> {

    private final T itemToAdd;

    public AddCommand(T item) {
        requireNonNull(item);
        itemToAdd = item;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ItemManagerWithFilteredList<T> managerAndList = getManagerAndList(model);
        if (managerAndList.hasItem(itemToAdd)) {
            throw new CommandException(getDuplicateItemMessage());
        }

        managerAndList.addItem(itemToAdd);
        return new CommandResult(getSuccessMessage());
    }

    abstract String getDuplicateItemMessage();

    abstract String getSuccessMessage();
}
