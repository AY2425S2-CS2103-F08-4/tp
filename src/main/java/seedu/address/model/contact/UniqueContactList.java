package seedu.address.model.contact;

import seedu.address.model.contact.exceptions.ContactNotFoundException;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.model.item.UniqueItemList;

/**
 * A list of contacts that enforces uniqueness between its elements and does not allow nulls.
 * A contact is considered unique by comparing using {@code Contact#isSameContact(Contact)}. As such, adding and
 * updating of contacts uses Contact#isSameContact(Contact) for equality to ensure that the contact being added or
 * updated is unique in terms of identity in the UniqueContactList. However, the removal of a contact uses
 * Contact#equals(Object) to ensure that the contact with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Contact#isSameContact(Contact)
 */
public class UniqueContactList extends UniqueItemList<Contact> {

    /**
     * Constructs a {@code UniqueItemList}.
     */
    public UniqueContactList() {
        super(new ContactDuplicateChecker());
    }

    @Override
    public void throwDuplicateException() {
        throw new DuplicateContactException();
    }

    @Override
    public void throwNotFoundException() {
        throw new ContactNotFoundException();
    }
}
