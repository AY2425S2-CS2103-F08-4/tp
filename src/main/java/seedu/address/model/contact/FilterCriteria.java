package seedu.address.model.contact;

import java.util.List;

import seedu.address.commons.core.Operator;

/**
 * Represents the filter criteria for a single column.
 * Each criteria consists of a logical operator and a list of values to match against.
 */
public class FilterCriteria {
    private final Operator operator;

    private final List<String> values;

    /**
     * Constructs a FilterCriteria with the given operator and values.
     *
     * @param operator the logical operator to apply
     * @param values the list of values to match against
     */
    public FilterCriteria(Operator operator, List<String> values) {
        this.operator = operator;
        this.values = values;
    }

    /**
     * Gets the logical operator for this criteria.
     *
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Gets the list of values for this criteria.
     *
     * @return the list of values
     */
    public List<String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCriteria otherCriteria)) {
            return false;
        }

        return operator == otherCriteria.operator
                && values.equals(otherCriteria.values);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + operator.hashCode();
        result = 31 * result + values.hashCode();
        return result;
    }
}
