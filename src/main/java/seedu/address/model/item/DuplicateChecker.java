package seedu.address.model.item;

public interface DuplicateChecker<T extends Item> {
    boolean check(T first, T second);
}
