package seedu.address.testutil;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withContact("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ContactManager contactManager;

    public AddressBookBuilder() {
        contactManager = new ContactManager();
    }

    public AddressBookBuilder(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    /**
     * Adds a new {@code Contact} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withContact(Contact contact) {
        contactManager.addItem(contact);
        return this;
    }

    public ContactManager build() {
        return contactManager;
    }
}
