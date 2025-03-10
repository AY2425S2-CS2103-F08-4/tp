package seedu.address.logic.abstractcommand;

import seedu.address.logic.commands.Command;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

public abstract class ItemCommand<T extends Item> extends Command {

    public abstract ItemManagerWithFilteredList<T> getManagerAndList(Model model);

}
