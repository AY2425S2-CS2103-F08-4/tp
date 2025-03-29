package seedu.address.model.contact;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.item.ItemManager;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class ContactManager extends ItemManager<Contact> {
    public ContactManager() {
        super(new UniqueContactList());
    }

    /**
     * Creates a ContactManager using the Contacts in the {@code toBeCopied}
     */
    public ContactManager(ItemManager<Contact> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("contacts", getItemList()).toString();
    }
}
