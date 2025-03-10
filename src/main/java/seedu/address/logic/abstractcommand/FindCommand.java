package seedu.address.logic.abstractcommand;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemManagerWithFilteredList;

public abstract class FindCommand<T extends Item> extends ItemCommand<T> {
    private final Predicate<T> predicate;

    public FindCommand(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ItemManagerWithFilteredList<T> managerAndList = getManagerAndList(model);
        managerAndList.updateFilteredItemsList(predicate);
        return new CommandResult(
                getResultOverviewMessage(managerAndList.getFilteredItemsList().size()));
    }

    abstract String getResultOverviewMessage(int numberOfResults);
}
