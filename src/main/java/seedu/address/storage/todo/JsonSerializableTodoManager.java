package seedu.address.storage.todo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.ItemManager;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.TodoManager;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "todolist")
class JsonSerializableTodoManager {

    public static final String MESSAGE_DUPLICATE_TODO = "Todo list contains duplicate todo(s).";

    private final List<JsonAdaptedTodo> todos = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableContactManager} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableTodoManager(@JsonProperty("todos") List<JsonAdaptedTodo> todos) {
        this.todos.addAll(todos);
    }

    /**
     * Converts a given {@code ItemManager<Todo>} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableTodoManager(ItemManager<Todo> source) {
        todos.addAll(source.getItemList().stream().map(JsonAdaptedTodo::new).toList());
    }

    /**
     * Converts this address book into the model's {@code TodoManager} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TodoManager toModelType() throws IllegalValueException {
        TodoManager todoManager = new TodoManager();
        for (JsonAdaptedTodo jsonAdaptedTodo : todos) {
            Todo todo = jsonAdaptedTodo.toModelType();
            if (todoManager.hasItem(todo)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TODO);
            }
            todoManager.addItem(todo);
        }
        return todoManager;
    }

}
