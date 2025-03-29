package seedu.address.logic.parser.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LINKED_CONTACT_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;

import java.util.List;

import seedu.address.commons.core.Operator;
import seedu.address.commons.core.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.FilterEventCommand;
import seedu.address.logic.commands.event.FilterEventCommand.EventPredicate;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.predicate.EventEndTimePredicate;
import seedu.address.model.event.predicate.EventLocationPredicate;
import seedu.address.model.event.predicate.EventNamePredicate;
import seedu.address.model.event.predicate.EventStartTimePredicate;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class FilterEventCommandParser implements Parser<FilterEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand and returns an
     * EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME_LONG,
            PREFIX_EVENT_START_LONG, PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG,
            PREFIX_EVENT_LINKED_CONTACT_LONG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_START_LONG,
            PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG, PREFIX_EVENT_LINKED_CONTACT_LONG);

        EventPredicate predicate = new EventPredicate();

        if (argMultimap.getValue(PREFIX_EVENT_NAME_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_NAME_LONG).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_EVENT_NAME_LONG));
            }
            predicate.setNamePredicate(new EventNamePredicate(operatorStringPair.first(),
                List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).get());
            if (operatorStringPair.second().trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES, PREFIX_EVENT_LOCATION_LONG));
            }
            predicate.setLocationPredicate(new EventLocationPredicate(operatorStringPair.first(),
                List.of(operatorStringPair.second().split("\\s+"))));
        }
        if (argMultimap.getValue(PREFIX_EVENT_START_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_START_LONG).get());
            predicate.setStartTimePredicate(new EventStartTimePredicate(operatorStringPair.first(),
                ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
        if (argMultimap.getValue(PREFIX_EVENT_END_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair =
                ParserUtil.parseOperatorAndString(argMultimap.getValue(PREFIX_EVENT_END_LONG).get());
            predicate.setEndTimePredicate(new EventEndTimePredicate(operatorStringPair.first(),
                ParserUtil.parseDatetimePredicates(operatorStringPair.second())));
        }
        if (argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).isPresent()) {
            Pair<Operator, String> operatorStringPair = ParserUtil.parseOperatorAndString(
                argMultimap.getValue(PREFIX_EVENT_LINKED_CONTACT_LONG).get());
            predicate.setContactOperator(operatorStringPair.first());
            List<Index> contactIndices = ParserUtil.parseIndices(operatorStringPair.second());
            if (contactIndices.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_NO_VALUES,
                    PREFIX_EVENT_LINKED_CONTACT_LONG));
            }
            predicate.setContactIndices(ParserUtil.parseIndices(operatorStringPair.second()));
        }

        if (!predicate.isAnyFieldNonNull()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterEventCommand(predicate);
    }
}
