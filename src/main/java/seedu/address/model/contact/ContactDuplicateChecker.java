package seedu.address.model.contact;

import seedu.address.model.item.DuplicateChecker;

/**
 * Checks if two given contacts are considered duplicates of each other.
 */
public class ContactDuplicateChecker implements DuplicateChecker<Contact> {
    @Override
    public boolean check(Contact first, Contact second) {
        return first == second || (first != null && first.getId().equals(second.getId()));
    }
}
