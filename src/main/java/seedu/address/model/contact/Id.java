package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidId(String)}
 */
public class Id {

    public static final String MESSAGE_CONSTRAINTS =
            "IDs should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullId;

    /**
     * Constructs a {@code Id}.
     *
     * @param id A valid id.
     */
    public Id(String id) {
        requireNonNull(id);
        checkArgument(isValidId(id), MESSAGE_CONSTRAINTS);
        fullId = id;
    }

    /**
     * Returns true if a given string is a valid id.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Id)) {
            return false;
        }

        Id otherId = (Id) other;
        return fullId.equals(otherId.fullId);
    }

    @Override
    public int hashCode() {
        return fullId.hashCode();
    }

}
