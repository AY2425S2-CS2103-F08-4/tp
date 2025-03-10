package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

public abstract class ItemManagerWithFilteredList<T extends Item> {
    private final Predicate<T> PREDICATE_SHOW_ALL = unused -> true;
    private final ItemManager<T> itemManager;
    private final FilteredList<T> filteredItems;

    public ItemManagerWithFilteredList(ItemManager<T> itemManager) {
        requireAllNonNull(itemManager);
        this.itemManager = itemManager;
        filteredItems = new FilteredList<>(this.itemManager.getItemList());
    }

    //=========== ItemManager ================================================================================

    public void setItemManager(ItemManager<T> itemManager) {
        this.itemManager.resetData(itemManager);
    }

    public ItemManager<T> getAddressBook() {
        return itemManager;
    }

    public boolean hasItem(T item) {
        requireNonNull(item);
        return itemManager.hasItem(item);
    }

    public void deleteItem(T target) {
        itemManager.removeItem(target);
    }

    public void addItem(T item) {
        itemManager.addItem(item);
        showAllItems();
    }

    public void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        itemManager.setItem(target, editedItem);
    }

    //=========== Filtered Item List Accessors =============================================================

    public ObservableList<T> getFilteredItemsList() {
        return filteredItems;
    }

    public void updateFilteredItemsList(Predicate<T> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    public void showAllItems() {
        filteredItems.setPredicate(PREDICATE_SHOW_ALL);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ItemManagerWithFilteredList<?> otherManagerAndList)) {
            return false;
        }

        return itemManager.equals(otherManagerAndList.itemManager)
                && filteredItems.equals(otherManagerAndList.filteredItems);
    }
}
