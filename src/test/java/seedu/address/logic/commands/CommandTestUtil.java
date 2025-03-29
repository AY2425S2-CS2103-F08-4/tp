package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_COURSE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_GROUP_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_ID_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_PHONE_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG_LONG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.EditPersonCommand;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonManager;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ID_AMY = "B0000000001";
    public static final String VALID_ID_BOB = "B0000000002";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_COURSE_AMY = "CS50";
    public static final String VALID_COURSE_BOB = "CS60";
    public static final String VALID_GROUP_AMY = "T09";
    public static final String VALID_GROUP_BOB = "T08";

    public static final String ID_DESC_AMY = " " + PREFIX_PERSON_ID_LONG + VALID_ID_AMY;
    public static final String ID_DESC_BOB = " " + PREFIX_PERSON_ID_LONG + VALID_ID_BOB;
    public static final String NAME_DESC_AMY = " " + PREFIX_PERSON_NAME_LONG + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_PERSON_NAME_LONG + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PERSON_PHONE_LONG + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PERSON_PHONE_LONG + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_PERSON_EMAIL_LONG + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_PERSON_EMAIL_LONG + VALID_EMAIL_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_PERSON_TAG_LONG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_PERSON_TAG_LONG + VALID_TAG_HUSBAND;
    public static final String COURSE_DESC_AMY = " " + PREFIX_PERSON_COURSE_LONG + VALID_COURSE_AMY;
    public static final String COURSE_DESC_BOB = " " + PREFIX_PERSON_COURSE_LONG + VALID_COURSE_BOB;
    public static final String GROUP_DESC_AMY = " " + PREFIX_PERSON_GROUP_LONG + VALID_GROUP_AMY;
    public static final String GROUP_DESC_BOB = " " + PREFIX_PERSON_GROUP_LONG + VALID_GROUP_BOB;

    // '&' not allowed in names
    public static final String INVALID_NAME_DESC = " " + PREFIX_PERSON_NAME_LONG + "James&";
    // 'a' not allowed in phones
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PERSON_PHONE_LONG + "911a";
    // missing '@' symbol
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_PERSON_EMAIL_LONG + "bob!yahoo";
    // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_PERSON_TAG_LONG + "hubby*";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonCommand.EditPersonDescriptor DESC_AMY;
    public static final EditPersonCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                                                    .withPhone(VALID_PHONE_AMY)
                                                    .withEmail(VALID_EMAIL_AMY)
                                                    .withTags(VALID_TAG_FRIEND)
                                                    .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                                                    .withPhone(VALID_PHONE_BOB)
                                                    .withEmail(VALID_EMAIL_BOB)
                                                    .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                                                    .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PersonManager expectedPersonManager =
                new PersonManager(actualModel.getPersonManagerAndList().getItemManager());
        List<Person> expectedFilteredList =
                new ArrayList<>(actualModel.getPersonManagerAndList().getFilteredItemsList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedPersonManager, actualModel.getPersonManagerAndList().getItemManager());
        assertEquals(expectedFilteredList,
                actualModel.getPersonManagerAndList().getFilteredItemsList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased()
                < model.getPersonManagerAndList().getFilteredItemsList().size());

        Person person = model.getPersonManagerAndList().getFilteredItemsList()
                .get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.getPersonManagerAndList()
                .updateFilteredItemsList(new NameContainsKeywordsPredicate(List.of(splitName[0])));

        assertEquals(1, model.getPersonManagerAndList().getFilteredItemsList().size());
    }

}
