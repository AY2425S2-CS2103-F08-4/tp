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
 * Removes a tag from a specified person.
 */
public class RemoveTagFromPersonCommand extends EditCommand<Person> {
    public static final String COMMAND_WORD = "untag";
    public static final String MESSAGE_USAGE = PERSON_COMMAND_WORD + " " + COMMAND_WORD
            + ": Removes a tag from a specified person.\n"
            + "Parameters: INDEX " + PREFIX_PERSON_TAG_LONG + " <tag>\n"
            + "Example: " + PERSON_COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_PERSON_TAG_LONG + " TA";
    public static final String MESSAGE_REMOVE_TAG_SUCCESSFUL = "Removed tag from person: %1$s";
    public static final String MESSAGE_MISSING_TAG = "The tag is already removed from this person";
    private final Tag tag;

    /**
     * Creates an EditCommand to remove a tag from a specified {@code Person}.
     */
    public RemoveTagFromPersonCommand(Index index, Tag tag) {
        super(index, Model::getPersonManagerAndList);
        requireNonNull(tag);
        this.tag = tag;
    }

    @Override
    public Person createEditedItem(Model model, Person personToEdit) throws CommandException {
        if (!personToEdit.getTags().contains(tag)) {
            throw new CommandException(MESSAGE_MISSING_TAG);
        }
        Set<Tag> newTags = new HashSet<>(personToEdit.getTags());
        newTags.remove(tag);
        return new Person(
            personToEdit.getId(),
            personToEdit.getName(),
            personToEdit.getPhone(),
            personToEdit.getEmail(),
            personToEdit.getCourse(),
            personToEdit.getGroup(),
            Set.copyOf(newTags)
        );
    }

    @Override
    public String getInvalidIndexMessage() {
        return Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
    }

    @Override
    public String getDuplicateMessage() {
        return Messages.MESSAGE_DUPLICATE_FIELDS;
    }

    @Override
    public String getSuccessMessage(Person editedPerson) {
        return String.format(MESSAGE_REMOVE_TAG_SUCCESSFUL, Messages.format(editedPerson));
    }
}
