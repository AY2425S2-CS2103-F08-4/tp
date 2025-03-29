package seedu.address.logic.parser.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.contact.ContactCliSyntax.PREFIX_CONTACT_TAG_LONG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.contact.AddTagToContactCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Tag;

/**
 * Parses input arguments and creates a new AddTagToContactCommand object
 */
public class AddTagToContactCommandParser implements Parser<AddTagToContactCommand> {
    @Override
    public AddTagToContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CONTACT_TAG_LONG);
        if (!argMultimap.arePrefixesPresent(PREFIX_CONTACT_TAG_LONG)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTagToContactCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTagToContactCommand.MESSAGE_USAGE), pe);
        }
        String stringTag = argMultimap.getValue(PREFIX_CONTACT_TAG_LONG).get();
        if (!Tag.isValidTagName(stringTag)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Tag.MESSAGE_CONSTRAINTS));
        }
        Tag tag = new Tag(stringTag);
        return new AddTagToContactCommand(index, tag);
    }
}
