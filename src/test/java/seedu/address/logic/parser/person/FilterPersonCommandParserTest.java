package seedu.address.logic.parser.person;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_OPERATOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME_LONG;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG_LONG;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Operator;
import seedu.address.logic.commands.person.FilterPersonCommand;
import seedu.address.model.person.FilterCriteria;
import seedu.address.model.person.PersonColumn;
import seedu.address.model.person.PersonPredicate;

public class FilterPersonCommandParserTest {

    private final FilterPersonCommandParser parser = new FilterPersonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // Single value with default AND operator
        Map<PersonColumn, FilterCriteria> expectedPredicateMap = new HashMap<>();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND,
                Collections.singletonList("alice")));
        PersonPredicate expectedPredicate = new PersonPredicate(expectedPredicateMap);
        FilterPersonCommand expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + " alice", expectedFilterCommand);

        // Multiple values with explicit OR operator
        expectedPredicateMap.clear();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.OR,
                Arrays.asList("alice", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + " or: alice bob", expectedFilterCommand);

        // Multiple columns
        expectedPredicateMap.clear();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND,
                Collections.singletonList("alice")));
        expectedPredicateMap.put(PersonColumn.EMAIL, new FilterCriteria(Operator.AND,
                Collections.singletonList("gmail")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + " alice " + PREFIX_PERSON_EMAIL_LONG + " gmail",
            expectedFilterCommand);
    }
    @Test
    public void parse_quotedValues_returnsFilterCommand() {
        // Quote-wrapped values
        Map<PersonColumn, FilterCriteria> expectedPredicateMap = new HashMap<>();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND,
                Collections.singletonList("alice pauline")));
        PersonPredicate expectedPredicate = new PersonPredicate(expectedPredicateMap);
        FilterPersonCommand expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + "\"alice pauline\"", expectedFilterCommand);

        // Mix of quoted and unquoted values
        expectedPredicateMap.clear();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.OR,
                Arrays.asList("alice pauline", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + "or: \"alice pauline\" bob", expectedFilterCommand);
    }

    @Test
    public void parse_allOperators_returnsFilterCommand() {
        // AND operator
        Map<PersonColumn, FilterCriteria> expectedPredicateMap = new HashMap<>();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND,
                Arrays.asList("alice", "pauline")));
        PersonPredicate expectedPredicate = new PersonPredicate(expectedPredicateMap);
        FilterPersonCommand expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + "and: alice pauline", expectedFilterCommand);

        // OR operator
        expectedPredicateMap.clear();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.OR,
                Arrays.asList("alice", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + "or: alice bob", expectedFilterCommand);

        // NAND operator
        expectedPredicateMap.clear();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.NAND,
                Arrays.asList("alice", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + "nand: alice bob", expectedFilterCommand);

        // NOR operator
        expectedPredicateMap.clear();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.NOR,
                Arrays.asList("alice", "bob")));
        expectedPredicate = new PersonPredicate(expectedPredicateMap);
        expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + "nor: alice bob", expectedFilterCommand);
    }

    @Test
    public void parse_invalidOperator_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_PERSON_NAME_LONG + "invalid: alice",
                String.format(MESSAGE_UNRECOGNIZED_OPERATOR, "invalid")
        );
    }

    @Test
    public void parse_multipleFilters_returnsFilterCommand() {
        // Test combining multiple filters with different operators
        Map<PersonColumn, FilterCriteria> expectedPredicateMap = new HashMap<>();
        expectedPredicateMap.put(PersonColumn.NAME, new FilterCriteria(Operator.AND, List.of(
                "alice")));
        expectedPredicateMap.put(PersonColumn.EMAIL, new FilterCriteria(Operator.OR,
                Arrays.asList("gmail", "yahoo")));
        expectedPredicateMap.put(PersonColumn.TAG, new FilterCriteria(Operator.NAND, List.of(
                "friend")));
        PersonPredicate expectedPredicate = new PersonPredicate(expectedPredicateMap);
        FilterPersonCommand expectedFilterCommand = new FilterPersonCommand(expectedPredicate);

        assertParseSuccess(parser, " " + PREFIX_PERSON_NAME_LONG + "alice "
                + PREFIX_PERSON_EMAIL_LONG + "or: gmail yahoo "
                + PREFIX_PERSON_TAG_LONG + "nand: friend", expectedFilterCommand);
    }
}
