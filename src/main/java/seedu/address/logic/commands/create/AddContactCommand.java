package seedu.address.logic.commands.create;

import static seedu.address.logic.ContactMessages.MESSAGE_DUPLICATE_CONTACT;
import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Adds a {@code Contact} to the app.
 */
public class AddContactCommand extends AddCommand<Contact> {

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a contact to the address book. Parameters: "
            + PREFIX_CONTACT_ID_LONG + " ID "
            + PREFIX_CONTACT_NAME_LONG + " NAME "
            + PREFIX_CONTACT_EMAIL_LONG + " EMAIL "
            + PREFIX_CONTACT_COURSE_LONG + " COURSE "
            + PREFIX_CONTACT_GROUP_LONG + " GROUP "
            + "[" + PREFIX_CONTACT_TAG_LONG + " TAG]...\n"
            + "Example: " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_CONTACT_ID_LONG + " A1234567A "
            + PREFIX_CONTACT_NAME_LONG + " John Doe "
            + PREFIX_CONTACT_EMAIL_LONG + " johnd@example.com "
            + PREFIX_CONTACT_COURSE_LONG + " CS50 "
            + PREFIX_CONTACT_GROUP_LONG + " T01 "
            + PREFIX_CONTACT_TAG_LONG + " friends "
            + PREFIX_CONTACT_TAG_LONG + " owesMoney";

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";

    /**
     * Creates an AddContactCommand to add the specified {@code contact}
     */
    public AddContactCommand(Contact contact) {
        super(contact, Model::getContactManagerAndList);
    }

    @Override
    public String getDuplicateItemMessage() {
        return MESSAGE_DUPLICATE_CONTACT;
    }

    @Override
    public String getSuccessMessage(Contact itemToAdd) {
        return String.format(MESSAGE_SUCCESS, Messages.format(itemToAdd));
    }

}
