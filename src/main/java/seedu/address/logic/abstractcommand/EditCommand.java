package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

public abstract class EditCommand<T extends Item> extends ItemCommand<T> {
    private final Index index;

    public EditCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = getManagerAndList(model);
        List<T> lastShownList = managerAndList.getFilteredItemsList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(getInvalidIndexMessage());
        }

        T itemToEdit = lastShownList.get(index.getZeroBased());
        T editedItem = createEditedItem(itemToEdit);

        if (!isDuplicate(itemToEdit, editedItem) && managerAndList.hasItem(editedItem)) {
            throw new CommandException(getDuplicateMessage());
        }

        managerAndList.setItem(itemToEdit, editedItem);
        managerAndList.showAllItems();
        return new CommandResult(getSuccessMessage(editedItem));
    }

    abstract T createEditedItem(T itemToEdit);

    abstract boolean isDuplicate(T itemToEdit, T editedItem);

    abstract String getInvalidIndexMessage();

    abstract String getDuplicateMessage();

    abstract String getSuccessMessage(T editedItem);
}
