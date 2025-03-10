package seedu.address.model.item;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

public abstract class ItemManager<T extends Item> {

    private final UniqueItemList<T> items;

    public ItemManager(UniqueItemList<T> items) {
        this.items = items;
    }

    //// list overwrite operations

    public void setItems(List<T> items) {
        this.items.setItems(items);
    }

    public void resetData(ItemManager<T> newData) {
        requireNonNull(newData);

        setItems(newData.getItemList());
    }

    //// item-level operations

    public boolean hasItem(T item) {
        requireNonNull(item);
        return items.contains(item);
    }

    public void addItem(T p) {
        items.add(p);
    }

    public void setItem(T target, T editedItem) {
        requireNonNull(editedItem);

        items.setItem(target, editedItem);
    }

    public void removeItem(T key) {
        items.remove(key);
    }

    public ObservableList<T> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemManager<?> otherAddressBook)) {
            return false;
        }

        return items.equals(otherAddressBook.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}
