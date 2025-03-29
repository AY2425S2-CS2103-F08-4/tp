package seedu.address.logic.parser.event;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_END_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_LOCATION_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_NAME_LONG;
import static seedu.address.logic.parser.event.EventCliSyntax.PREFIX_EVENT_START_LONG;

import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDateTime;
import seedu.address.model.event.EventLocation;
import seedu.address.model.event.EventName;

/**
 * Parses input arguments and creates a new AddEventCommand object.
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand and returns
     * an AddEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_START_LONG,
            PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG);

        if (!argMultimap.arePrefixesPresent(PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_START_LONG,
            PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddEventCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_NAME_LONG, PREFIX_EVENT_START_LONG,
            PREFIX_EVENT_END_LONG, PREFIX_EVENT_LOCATION_LONG);
        EventName name = EventParseUtil.parseName(argMultimap.getValue(PREFIX_EVENT_NAME_LONG).get());
        EventDateTime startTime = EventParseUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_START_LONG).get());
        EventDateTime endTime = EventParseUtil.parseDateTime(argMultimap.getValue(PREFIX_EVENT_END_LONG).get());
        EventLocation location = EventParseUtil.parseLocation(argMultimap.getValue(PREFIX_EVENT_LOCATION_LONG).get());
        if (!startTime.isBefore(endTime)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EventDateTime.MESSAGE_NEGATIVE_DURATION));
        }
        Event event = new Event(name, startTime, endTime, location);

        return new AddEventCommand(event);
    }
}
