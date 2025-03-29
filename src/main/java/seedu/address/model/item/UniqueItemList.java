package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls. Supports
 * a minimal set of list operations.
 *
 * @param <T> the type of item to be stored in the list, which extends {@link Item}.
 */
public abstract class UniqueItemList<T extends Item> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final DuplicateChecker<T> duplicateChecker;

    /**
     * Constructs a {@code UniqueItemList} with the given {@code duplicateChecker}.
     *
     * @param duplicateChecker the {@code DuplicateChecker} used to check for duplicates.
     */
    public UniqueItemList(DuplicateChecker<T> duplicateChecker) {
        this.duplicateChecker = duplicateChecker;
    }

    /**
     * Returns the duplicate checker instance used in the list.
     */
    public DuplicateChecker<T> getDuplicateChecker() {
        return duplicateChecker;
    }

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(elem -> duplicateChecker.check(elem, toCheck));
    }

    /**
     * Adds an item to the list. The item must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throwDuplicateException();
            return;
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the item {@code target} in the list with {@code editedItem}. {@code target} must
     * exist in the list. {@code editedContact} must not be the same as another existing item in the
     * list.
     */
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

    /**
     * Removes the equivalent item from the list. The item must exist in the list.
     */
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

    /**
     * Replaces the contents of this list with {@code items}. {@code items} must not contain
     * duplicate contacts.
     */
    public void setItems(List<T> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throwDuplicateException();
            return;
        }

        internalList.setAll(items);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
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

    /**
     * Returns true if {@code items} contains only unique contacts.
     */
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

    /**
     * Throws an exception when attempting to add a duplicate item.
     */
    public abstract void throwDuplicateException();

    /**
     * Throws an exception when attempting to modify an item that does not exist in the list.
     */
    public abstract void throwNotFoundException();
}
