package seedu.address.logic.commands.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_SEARCH_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.CARL;
import static seedu.address.testutil.TypicalContacts.DANIEL;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.ContactColumn;
import seedu.address.model.contact.ContactManagerWithFilteredList;
import seedu.address.model.contact.ContactPredicate;
import seedu.address.model.contact.FilterCriteria;
import seedu.address.model.event.EventManagerWithFilteredList;
import seedu.address.model.todo.TodoManagerWithFilteredList;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterContactCommand}.
 */
public class FilterContactCommandTest {
    private final Model model = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(getTypicalAddressBook()),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList()
    );
    private final Model expectedModel = new ModelManager(
            new UserPrefs(),
            new ContactManagerWithFilteredList(getTypicalAddressBook()),
            new TodoManagerWithFilteredList(),
            new EventManagerWithFilteredList()
    );

    @Test
    public void equals() {
        Map<ContactColumn, FilterCriteria> firstPredicateMap = new HashMap<>();
        Map<ContactColumn, FilterCriteria> secondPredicateMap = new HashMap<>();
        firstPredicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.AND, Arrays.asList(
                "first", "test")));
        secondPredicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.OR, Arrays.asList(
                "second", "test")));

        ContactPredicate firstPredicate = new ContactPredicate(firstPredicateMap);
        ContactPredicate secondPredicate = new ContactPredicate(secondPredicateMap);

        FilterContactCommand findFirstCommand = new FilterContactCommand(firstPredicate);
        FilterContactCommand findSecondCommand = new FilterContactCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FilterContactCommand findFirstCommandCopy = new FilterContactCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different predicates -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_nameFilter_multipleContactsFound() {
        Map<ContactColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.OR, Arrays.asList("Carl",
                "Daniel")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 2);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL),
                model.getContactManagerAndList().getFilteredItemsList());
    }

    @Test
    public void execute_multipleFilters_matchingContactFound() {
        Map<ContactColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.AND, List.of("alice")));
        predicateMap.put(ContactColumn.TAG, new FilterCriteria(Operator.AND, List.of("friends")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        FilterContactCommand command = new FilterContactCommand(predicate);

        expectedModel.getContactManagerAndList().updateFilteredItemsList(predicate);
        String expectedMessage = String.format(MESSAGE_SEARCH_OVERVIEW, 1);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE),
                model.getContactManagerAndList().getFilteredItemsList());
    }
}
