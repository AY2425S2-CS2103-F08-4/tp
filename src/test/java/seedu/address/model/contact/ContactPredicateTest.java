package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BENSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;

public class ContactPredicateTest {

    @Test
    public void equals() {
        Map<ContactColumn, FilterCriteria> firstPredicateMap = new HashMap<>();
        firstPredicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.AND,
                Collections.singletonList("Alice")));

        Map<ContactColumn, FilterCriteria> secondPredicateMap = new HashMap<>();
        secondPredicateMap.put(ContactColumn.EMAIL, new FilterCriteria(Operator.OR,
                Collections.singletonList("gmail.com")));

        ContactPredicate firstPredicate = new ContactPredicate(firstPredicateMap);
        ContactPredicate secondPredicate = new ContactPredicate(secondPredicateMap);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        ContactPredicate firstPredicateCopy = new ContactPredicate(firstPredicateMap);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different predicate -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // AND operator - all keywords must match
        Map<ContactColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.AND, Arrays.asList("Alice",
                "Pauline")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice Pauline matches

        // OR operator - at least one keyword must match
        predicateMap.clear();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.OR, Arrays.asList("Alice",
                "Bob")));
        predicate = new ContactPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice matches

        // NAND operator - at least one keyword must not match
        predicateMap.clear();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.NAND, Arrays.asList("Bob",
                "Charlie")));
        predicate = new ContactPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice doesn't contain Bob or Charlie

        // NOR operator - all keywords must not match
        predicateMap.clear();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.NOR, Arrays.asList("Bob",
                "Charlie")));
        predicate = new ContactPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice doesn't contain Bob or Charlie
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // AND operator
        Map<ContactColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.AND, Arrays.asList("Alice",
                "Bob")));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        assertFalse(predicate.test(BENSON)); // Benson doesn't contain Alice and Bob

        // OR operator
        predicateMap.clear();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.OR, Arrays.asList(
                "Charlie", "David")));
        predicate = new ContactPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice doesn't contain Charlie or David

        // NAND operator
        predicateMap.clear();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.NAND, List.of("Alice")));
        predicate = new ContactPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice contains Alice, so NAND fails

        // NOR operator
        predicateMap.clear();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.NOR, Arrays.asList("Alice",
                "Bob")));
        predicate = new ContactPredicate(predicateMap);
        assertFalse(predicate.test(ALICE)); // Alice contains Alice, so NOR fails
    }

    @Test
    public void test_multipleColumns_matchesAccordingly() {
        // Test multiple columns with AND relationship (implicit between columns)
        Map<ContactColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(ContactColumn.NAME, new FilterCriteria(Operator.AND,
                Collections.singletonList("Alice")));
        predicateMap.put(ContactColumn.EMAIL, new FilterCriteria(Operator.AND,
                Collections.singletonList("example")));

        ContactPredicate predicate = new ContactPredicate(predicateMap);
        assertTrue(predicate.test(ALICE)); // Alice has both Alice in name and example in email
        assertFalse(predicate.test(BENSON)); // Benson doesn't have Alice in name
    }

    @Test
    public void test_differentFields_returnsCorrectResults() {
        // Test ID field
        Map<ContactColumn, FilterCriteria> predicateMap = new HashMap<>();
        predicateMap.put(ContactColumn.ID, new FilterCriteria(Operator.AND,
                Collections.singletonList(ALICE.getId().fullId)));
        ContactPredicate predicate = new ContactPredicate(predicateMap);
        assertTrue(predicate.test(ALICE));

        // Test TAG field
        predicateMap.clear();
        String tag = ALICE.getTags().stream().findFirst().get().tagName;
        predicateMap.put(ContactColumn.TAG, new FilterCriteria(Operator.AND,
                Collections.singletonList(tag)));
        predicate = new ContactPredicate(predicateMap);
        assertTrue(predicate.test(ALICE));
    }
}
