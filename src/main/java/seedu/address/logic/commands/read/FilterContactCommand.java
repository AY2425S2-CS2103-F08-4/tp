package seedu.address.logic.commands.read;

import static seedu.address.logic.parser.CliSyntax.CONTACT_COMMAND_WORD;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_COURSE_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_EMAIL_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_GROUP_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_ID_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_NAME_LONG;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactPredicate;

/**
 * Filters and lists all contacts in address book based on specified criteria.
 * Filter criteria are formed with columns, operators, and values.
 */
public class FilterContactCommand extends FilterCommand<Contact> {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = CONTACT_COMMAND_WORD + " " + COMMAND_WORD + ": Filters contacts based on"
        + "specified criteria and displays them as a list with index numbers.\n"
        + "Parameters: <col>/ [<op>:] <value(s)> [...]\n"
        + "- <col>/ : Column to filter on ("
        + PREFIX_CONTACT_NAME_LONG + ", "
        + PREFIX_CONTACT_EMAIL_LONG + ", "
        + PREFIX_CONTACT_ID_LONG + ", "
        + PREFIX_CONTACT_COURSE_LONG + ", "
        + PREFIX_CONTACT_GROUP_LONG + ", "
        + PREFIX_CONTACT_TAG_LONG + ")\n"
        + "- <op>: : Operator (and, or, nand, nor). Defaults to 'and' if not specified\n"
        + "- <value(s)>: One or more values to filter by. Use quotes for values with spaces.\n"
        + "Examples:\n"
        + "1. " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_CONTACT_ID_LONG + " or: 12 13\n"
        + "   Find students with ID 12 or 13.\n"
        + "2. " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_CONTACT_NAME_LONG + "\"Darren Tan\" "
        + PREFIX_CONTACT_COURSE_LONG + " CS1010S " + PREFIX_CONTACT_GROUP_LONG + "or: T01 T02 T03\n"
        + "   Find contacts with \"Darren Tan\" in their name who"
        + " enroll in course CS1010S and class T01, T02, or T03.\n"
        + "3. " + CONTACT_COMMAND_WORD + " " + COMMAND_WORD + " "
        + PREFIX_CONTACT_NAME_LONG + "nand: \"My enemy\" Hater " + PREFIX_CONTACT_TAG_LONG + "and: handsome smart\n"
        + "   Find contacts whose names do not contain \"My enemy\" and"
        + " \"Hater\" and are tagged with both \"handsome\" and \"smart\".";

    private final Predicate<Contact> predicate;

    /**
     * Constructs a FilterContactCommand with the given predicate.
     *
     * @param predicate the predicate used to filter contacts
     */
    public FilterContactCommand(ContactPredicate predicate) {
        super(Model::getContactManagerAndList);
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterContactCommand otherFilterCommand)) {
            return false;
        }

        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public Predicate<Contact> createPredicate(Model model) {
        return predicate;
    }

    @Override
    public String getResultOverviewMessage(int numberOfResults) {
        return String.format(Messages.MESSAGE_SEARCH_OVERVIEW, numberOfResults);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
