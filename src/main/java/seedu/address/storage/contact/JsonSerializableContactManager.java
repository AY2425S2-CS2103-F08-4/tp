package seedu.address.storage.contact;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactManager;
import seedu.address.model.item.ItemManager;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
public class JsonSerializableContactManager {

    public static final String MESSAGE_DUPLICATE_CONTACT = "Contacts list contains duplicate contact(s).";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableContactManager} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableContactManager(@JsonProperty("contacts") List<JsonAdaptedContact> contacts) {
        this.contacts.addAll(contacts);
    }

    /**
     * Converts a given {@code ItemManager<Contact>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableContactManager(ItemManager<Contact> source) {
        contacts.addAll(source.getItemList().stream().map(JsonAdaptedContact::new).toList());
    }

    /**
     * Converts this address book into the model's {@code ContactManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ContactManager toModelType() throws IllegalValueException {
        ContactManager contactManager = new ContactManager();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact = jsonAdaptedContact.toModelType();
            if (contactManager.hasItem(contact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONTACT);
            }
            contactManager.addItem(contact);
        }
        return contactManager;
    }

}
