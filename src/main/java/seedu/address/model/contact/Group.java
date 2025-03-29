package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's group in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGroup(String)}
 */
public class Group {

    public static final String MESSAGE_CONSTRAINTS =
        "groups should only contain alphanumeric " + "characters and spaces, and it should not " + "be " + "blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullGroup;

    /**
     * Constructs a {@code group}.
     *
     * @param group A valid group.
     */
    public Group(String group) {
        requireNonNull(group);
        checkArgument(isValidGroup(group), MESSAGE_CONSTRAINTS);
        fullGroup = group;
    }

    /**
     * Returns true if a given string is a valid group.
     */
    public static boolean isValidGroup(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return fullGroup.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return fullGroup.equals(otherGroup.fullGroup);
    }

    @Override
    public String toString() {
        return fullGroup;
    }

}
