package seedu.address.logic.commands.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PERSON_COMMAND_WORD;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG_LONG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.abstractcommand.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * Adds a tag to a Person in contact.
 */
public class AddTagToPersonCommand extends EditCommand<Person> {
    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = PERSON_COMMAND_WORD + " " + COMMAND_WORD
            + ": Adds a tag to a specified person.\n"
            + "Parameters: INDEX " + PREFIX_PERSON_TAG_LONG + " <tag>\n"
            + "Example: " + PERSON_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_PERSON_TAG_LONG + " TA";
    public static final String MESSAGE_ADD_TAG_SUCCESSFUL = "Added tag to person: %1$s";
    public static final String MESSAGE_DUPLICATE_TAGS = "The tag is already assigned to this person";
    private final Tag tag;

    /**
     * Creates an EditCommand to add a tag to the specified {@code Person}
     */
    public AddTagToPersonCommand(Index index, Tag tag) {
        super(index, Model::getPersonManagerAndList);
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public Person createEditedItem(Model model, Person personToEdit) throws CommandException {
        if (personToEdit.getTags().contains(tag)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_TAGS));
        }
        Set<Tag> newTags = new HashSet<>(personToEdit.getTags());
        newTags.add(tag);
        return new Person(
                personToEdit.getId(),
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getCourse(),
                personToEdit.getGroup(),
                Set.copyOf(newTags));
    }

    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return MESSAGE_DUPLICATE_TAGS;
    }

    @Override
    public String getSuccessMessage(Person editedPerson) {
        return String.format(MESSAGE_ADD_TAG_SUCCESSFUL, Messages.format(editedPerson));
    }
}
