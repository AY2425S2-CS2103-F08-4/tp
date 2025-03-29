package seedu.address.logic.commands.person;

import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_COURSE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_GROUP_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_ID_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_PHONE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG_LONG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.AddCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddPersonCommand extends AddCommand<Person> {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = PERSON_COMMAND_WORD + " " + COMMAND_WORD
        + ": Adds a person to the address book. Parameters: "
        + PREFIX_PERSON_ID_LONG + " ID "
        + PREFIX_PERSON_NAME_LONG + " NAME "
        + PREFIX_PERSON_PHONE_LONG + " PHONE "
        + PREFIX_PERSON_EMAIL_LONG + " EMAIL "
        + PREFIX_PERSON_COURSE_LONG + " COURSE "
        + PREFIX_PERSON_GROUP_LONG + " GROUP "
        + "[" + PREFIX_PERSON_TAG_LONG + " TAG]...\n"
        + "Example: " + PERSON_COMMAND_WORD + " " + COMMAND_WORD + " "
        + PREFIX_PERSON_ID_LONG + " A1234567A "
        + PREFIX_PERSON_NAME_LONG + " John Doe "
        + PREFIX_PERSON_PHONE_LONG + " 98765432 "
        + PREFIX_PERSON_EMAIL_LONG + " johnd@example.com "
        + PREFIX_PERSON_COURSE_LONG + " CS50 "
        + PREFIX_PERSON_GROUP_LONG + " T01 "
        + PREFIX_PERSON_TAG_LONG + " friends "
        + PREFIX_PERSON_TAG_LONG + " owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddPersonCommand(Person person) {
        super(person, Model::getPersonManagerAndList);
    }


    @Override
    public String getDuplicateItemMessage() {
        return MESSAGE_DUPLICATE_PERSON;
    }

    @Override
    public String getSuccessMessage(Person itemToAdd) {
        return String.format(MESSAGE_SUCCESS, Messages.format(itemToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonCommand otherAddPersonCommand)) {
            return false;
        }

        return itemToAdd.equals(otherAddPersonCommand.itemToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("toAdd", itemToAdd)
            .toString();
    }
}
