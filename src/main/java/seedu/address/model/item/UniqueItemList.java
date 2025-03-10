package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class UniqueItemList<T extends Item> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final DuplicateChecker<T> duplicateChecker;

    public UniqueItemList(DuplicateChecker<T> duplicateChecker) {
        this.duplicateChecker = duplicateChecker;
    }

    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(elem -> duplicateChecker.check(elem, toCheck));
    }

    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throwDuplicateException();
            return;
        }
        internalList.add(toAdd);
    }

    public void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throwNotFoundException();
            return;
        }

        if (!duplicateChecker.check(target, editedItem) && contains(editedItem)) {
            throwDuplicateException();
            return;
        }

        internalList.set(index, editedItem);
    }

    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throwNotFoundException();
        }
    }

    public <U extends T> void setItems(UniqueItemList<U> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setItems(List<T> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throwDuplicateException();
            return;
        }

        internalList.setAll(items);
    }

    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueItemList<?> otherUniqueItemList)) {
            return false;
        }

        return internalList.equals(otherUniqueItemList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    private boolean itemsAreUnique(List<T> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (duplicateChecker.check(items.get(i), items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    abstract void throwDuplicateException();
    abstract void throwNotFoundException();
}

