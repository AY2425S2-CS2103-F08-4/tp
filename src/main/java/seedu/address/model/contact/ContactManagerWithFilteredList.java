package seedu.address.model.contact;

import seedu.address.model.item.ItemManager;
import seedu.address.model.item.ItemManagerWithFilteredList;

/**
 * Manages a list of {@code Contact} objects and provides a filtered list for display purposes.
 * Duplicates are not allowed in the underlying list.
 */
public class ContactManagerWithFilteredList extends ItemManagerWithFilteredList<Contact> {
    public ContactManagerWithFilteredList(ItemManager<Contact> contactManager) {
        super(contactManager);
    }

    public ContactManagerWithFilteredList() {
        super(new ContactManager());
    }
}
