package seedu.address.logic.parser.person;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_COLUMN;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_COLUMNS;
import static seedu.address.logic.Messages.MESSAGE_NO_VALUES;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_COLUMN;
import static seedu.address.logic.Messages.MESSAGE_UNRECOGNIZED_OPERATOR;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_COURSE;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_EMAIL;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_GROUP;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_ID;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_NAME;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_PHONE;
import static seedu.address.logic.parser.person.PersonCliSyntax.PREFIX_PERSON_TAG;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.Operator;
import seedu.address.logic.commands.person.FilterPersonCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FilterCriteria;
import seedu.address.model.person.PersonColumn;
import seedu.address.model.person.PersonPredicate;

/**
 * Parses input arguments and creates a new FilterPersonCommand object. Handles complex filter
 * criteria with different operators and values.
 */
public class FilterPersonCommandParser implements Parser<FilterPersonCommand> {

    private static final Pattern OPERATOR_FORMAT = Pattern.compile("^([a-zA-Z]+):");

    private static final Pattern QUOTED_VALUE_PATTERN = Pattern.compile("\"([^\"]*)\"");

    /**
     * Converts a prefix to its corresponding column.
     *
     * @param prefix the prefix to convert
     * @return the corresponding column
     * @throws ParseException if the prefix does not correspond to a valid column
     */
    private PersonColumn getColumnFromPrefix(Prefix prefix) throws ParseException {
        String prefixStr = prefix.getPrefix();

        if (prefixStr.equals(PREFIX_PERSON_NAME.getPrefix())) {
            return PersonColumn.NAME;
        } else if (prefixStr.equals(PREFIX_PERSON_PHONE.getPrefix())) {
            return PersonColumn.PHONE;
        } else if (prefixStr.equals(PREFIX_PERSON_EMAIL.getPrefix())) {
            return PersonColumn.EMAIL;
        } else if (prefixStr.equals(PREFIX_PERSON_TAG.getPrefix())) {
            return PersonColumn.TAG;
        } else if (prefixStr.equals(PREFIX_PERSON_COURSE.getPrefix())) {
            return PersonColumn.COURSE;
        } else if (prefixStr.equals(PREFIX_PERSON_GROUP.getPrefix())) {
            return PersonColumn.GROUP;
        } else {
            throw new ParseException(
                    String.format(MESSAGE_UNRECOGNIZED_COLUMN, prefixStr)
            );
        }
    }

    /**
     * Parses all prefixes in the argument multimap and adds the corresponding filter criteria to
     * the provided map.
     *
     * @param allPrefixes       the list of all prefixes to parse
     * @param argMultimap       the argument multimap containing the parsed arguments
     * @param filterCriteriaMap the map to store the parsed filter criteria
     * @throws ParseException if there is an error parsing any prefix
     */
    private void parsePrefixes(List<Prefix> allPrefixes, ArgumentMultimap argMultimap,
                               Map<PersonColumn, FilterCriteria> filterCriteriaMap) throws ParseException {
        for (Prefix prefix : allPrefixes) {
            List<String> rawValues = argMultimap.getAllValues(prefix);
            if (rawValues.isEmpty()) {
                continue;
            }

            try {
                PersonColumn column = getColumnFromPrefix(prefix);

                parseValues(prefix, column, filterCriteriaMap, rawValues);
            } catch (IllegalArgumentException e) {
                throw new ParseException(
                        String.format(MESSAGE_UNRECOGNIZED_COLUMN, prefix.getPrefix())
                );
            }
        }
    }

    /**
     * Parses the values for a specific prefix and adds the corresponding filter criteria to the
     * provided map.
     *
     * @param prefix            the prefix being parsed
     * @param column            the column corresponding to the prefix
     * @param filterCriteriaMap the map to store the parsed filter criteria
     * @param rawValues         the list of raw values to parse
     * @throws ParseException if there is an error parsing the values
     */
    private void parseValues(Prefix prefix, PersonColumn column, Map<PersonColumn,
            FilterCriteria> filterCriteriaMap,
                             List<String> rawValues) throws ParseException {
        if (filterCriteriaMap.containsKey(column)) {
            throw new ParseException(MESSAGE_DUPLICATE_COLUMN);
        }

        String firstValue = rawValues.get(0);
        Operator operator = Operator.AND; // Default operator
        List<String> values = new ArrayList<>();

        Matcher operatorMatcher = OPERATOR_FORMAT.matcher(firstValue);
        if (operatorMatcher.find()) {
            String operatorStr = operatorMatcher.group(1);
            try {
                operator = Operator.valueOf(operatorStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ParseException(
                        String.format(MESSAGE_UNRECOGNIZED_OPERATOR, operatorStr)
                );
            }

            firstValue = firstValue.substring(operatorMatcher.end()).trim();
            if (!firstValue.isEmpty()) {
                values.addAll(extractValues(firstValue));
            }
        } else {
            values.addAll(extractValues(firstValue));
        }

        for (int i = 1; i < rawValues.size(); i++) {
            values.addAll(extractValues(rawValues.get(i)));
        }

        if (values.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_NO_VALUES, prefix.getPrefix())
            );
        }

        filterCriteriaMap.put(column, new FilterCriteria(operator, values));
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FilterPersonCommand and
     * returns a FilterPersonCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterPersonCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FilterPersonCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_PERSON_NAME, PREFIX_PERSON_PHONE, PREFIX_PERSON_EMAIL, PREFIX_PERSON_ID,
                PREFIX_PERSON_COURSE,
                PREFIX_PERSON_GROUP, PREFIX_PERSON_TAG);

        Map<PersonColumn, FilterCriteria> filterCriteriaMap = new HashMap<>();

        List<Prefix> allPrefixes = List.of(PREFIX_PERSON_NAME, PREFIX_PERSON_PHONE,
                PREFIX_PERSON_EMAIL,
                PREFIX_PERSON_ID, PREFIX_PERSON_COURSE, PREFIX_PERSON_GROUP, PREFIX_PERSON_TAG);

        parsePrefixes(allPrefixes, argMultimap, filterCriteriaMap);

        if (filterCriteriaMap.isEmpty()) {
            throw new ParseException(MESSAGE_NO_COLUMNS);
        }

        return new FilterPersonCommand(new PersonPredicate(filterCriteriaMap));
    }

    /**
     * Extracts individual values from an input string, handling both quoted and unquoted values.
     *
     * @param input the input string to extract values from
     * @return a list of extracted values
     */
    private List<String> extractValues(String input) {
        List<String> values = new ArrayList<>();
        Matcher quotedValueMatcher = QUOTED_VALUE_PATTERN.matcher(input);

        int lastPosition = 0;

        while (quotedValueMatcher.find()) {
            values.add(quotedValueMatcher.group(1).trim());
            lastPosition = quotedValueMatcher.end();
        }

        if (lastPosition > 0 && lastPosition < input.length()) {
            String remaining = input.substring(lastPosition).trim();
            if (!remaining.isEmpty()) {
                for (String value : remaining.split("\\s+")) {
                    if (!value.trim().isEmpty()) {
                        values.add(value.trim());
                    }
                }
            }
        } else if (values.isEmpty() && !input.trim().isEmpty()) {
            for (String value : input.trim().split("\\s+")) {
                if (!value.trim().isEmpty()) {
                    values.add(value.trim());
                }
            }
        }

        return values;
    }
}
