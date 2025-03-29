package seedu.address.logic.commands;

import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_DEADLINE_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_LOCATION_LONG;
import static seedu.address.logic.parser.todo.TodoCliSyntax.PREFIX_TODO_NAME_LONG;

/**
 * Contains helper methods for testing todo commands.
 */
public class TodoCommandTestUtil {
    public static final String VALID_NAME_REPORT = "Report";
    public static final String VALID_NAME_GRADING = "Grading submissions";
    public static final String VALID_DEADLINE_REPORT = "25-12-31 23:59";
    public static final String VALID_DEADLINE_GRADING = "25-11-30 23:59";
    public static final String VALID_LOCATION_REPORT = "Canvas";
    public static final String VALID_LOCATION_GRADING = "Prof office";

    public static final String NAME_DESC_REPORT = " " + PREFIX_TODO_NAME_LONG + VALID_NAME_REPORT;
    public static final String NAME_DESC_GRADING = " " + PREFIX_TODO_NAME_LONG + VALID_NAME_GRADING;
    public static final String DEADLINE_DESC_REPORT =
            " " + PREFIX_TODO_DEADLINE_LONG + VALID_DEADLINE_REPORT;
    public static final String DEADLINE_DESC_GRADING =
            " " + PREFIX_TODO_DEADLINE_LONG + VALID_DEADLINE_GRADING;
    public static final String LOCATION_DESC_REPORT =
            " " + PREFIX_TODO_LOCATION_LONG + VALID_LOCATION_REPORT;
    public static final String LOCATION_DESC_GRADING =
            " " + PREFIX_TODO_LOCATION_LONG + VALID_LOCATION_GRADING;

    public static final String INVALID_TODO_NAME_DESC = " " + PREFIX_TODO_NAME_LONG + "Stuff&stuff";
    public static final String INVALID_TODO_DEADLINE_DESC_NOT_DATETIME =
            " " + PREFIX_TODO_DEADLINE_LONG + "aaaa";
    public static final String INVALID_TODO_DEADLINE_DESC_INCORRECT_FORMAT =
            " " + PREFIX_TODO_DEADLINE_LONG + "23:59 25-12-31";
}
