package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;

public abstract class ClearCommand<T extends Item> extends ItemCommand<T> {
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = getManagerAndList(model);
        managerAndList.setItemManager(getEmptyItemManager());
        return new CommandResult(getSuccessMessage());
    }

    abstract ItemManager<T> getEmptyItemManager();

    abstract String getSuccessMessage();
}
