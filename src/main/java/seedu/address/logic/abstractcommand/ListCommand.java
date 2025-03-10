package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

public abstract class ListCommand<T extends Item> extends ItemCommand<T> {
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = getManagerAndList(model);
        managerAndList.showAllItems();
        return new CommandResult(getSuccessMessage());
    }

    abstract String getSuccessMessage();
}
