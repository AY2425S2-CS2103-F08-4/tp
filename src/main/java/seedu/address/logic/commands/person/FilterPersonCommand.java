package seedu.address.logic.commands.person;

import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_COURSE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_GROUP_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_ID_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_PHONE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG_LONG;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.FilterCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonPredicate;

/**
 * Filters and lists all persons in address book based on specified criteria.
 * Filter criteria are formed with columns, operators, and values.
 */
public class FilterPersonCommand extends FilterCommand<Person> {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = PERSON_COMMAND_WORD + " " + COMMAND_WORD + ": Filters persons based on"
        + "specified criteria and displays them as a list with index numbers.\n"
        + "Parameters: <col>/ [<op>:] <value(s)> [...]\n"
        + "- <col>/ : Column to filter on (" + PREFIX_PERSON_NAME_LONG + ", " + PREFIX_PERSON_PHONE_LONG + ", "
        + PREFIX_PERSON_EMAIL_LONG + ", " + PREFIX_PERSON_ID_LONG + ", " + PREFIX_PERSON_COURSE_LONG + ", "
        + PREFIX_PERSON_GROUP_LONG + ", " + PREFIX_PERSON_TAG_LONG + ")\n"
        + "- <op>: : Operator (and, or, nand, nor). Defaults to 'and' if not specified\n"
        + "- <value(s)>: One or more values to filter by. Use quotes for values with spaces.\n"
        + "Examples:\n"
        + "1. " + PERSON_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_PERSON_ID_LONG + " or: 12 13\n"
        + "   Find students with ID 12 or 13.\n"
        + "2. " + PERSON_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_PERSON_NAME_LONG + "\"Darren Tan\" "
        + PREFIX_PERSON_COURSE_LONG + " CS1010S " + PREFIX_PERSON_GROUP_LONG + "or: T01 T02 T03\n"
        + "   Find contacts with \"Darren Tan\" in their name who"
        + " enroll in course CS1010S and class T01, T02, or T03.\n"
        + "3. " + PERSON_COMMAND_WORD + " " + COMMAND_WORD + " " + PREFIX_PERSON_NAME_LONG + "nand: \"My enemy\" Hater "
        + PREFIX_PERSON_TAG_LONG + "and: handsome smart\n"
        + "   Find contacts whose names do not contain \"My enemy\" and"
        + " \"Hater\" and are tagged with both \"handsome\" and \"smart\".";

    private final Predicate<Person> predicate;

    /**
     * Constructs a FilterPersonCommand with the given predicate.
     *
     * @param predicate the predicate used to filter persons
     */
    public FilterPersonCommand(PersonPredicate predicate) {
        super(Model::getPersonManagerAndList);
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterPersonCommand otherFilterCommand)) {
            return false;
        }

        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public Predicate<Person> createPredicate(Model model) {
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
