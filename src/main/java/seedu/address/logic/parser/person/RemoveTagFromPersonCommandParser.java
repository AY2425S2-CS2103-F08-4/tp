package seedu.address.logic.parser.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.person.RemoveTagFromPersonCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Tag;

/**
 * Parses input arguments and creates a new RemoveTagFromPersonCommand object
 */
public class RemoveTagFromPersonCommandParser implements Parser<RemoveTagFromPersonCommand> {
    @Override
    public RemoveTagFromPersonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON_TAG);
        if (!argMultimap.arePrefixesPresent(PREFIX_PERSON_TAG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveTagFromPersonCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveTagFromPersonCommand.MESSAGE_USAGE), pe);
        }
        String stringTag = argMultimap.getValue(PREFIX_PERSON_TAG).get();
        if (!Tag.isValidTagName(stringTag)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Tag.MESSAGE_CONSTRAINTS));
        }
        Tag tag = new Tag(stringTag);
        return new RemoveTagFromPersonCommand(index, tag);
    }
}
