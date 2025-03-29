package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;
import seedu.address.model.event.Event;
import seedu.address.model.todo.Todo;

/**
 * Panel containing the unified list that can display contacts, events, or todos.
 */
public class ListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<Object> listView;

    /**
     * Constructor to create a new ListPanel
     */
    public ListPanel() {
        super(FXML);
    }

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList} of contacts.
     */
    public void setContactList(ObservableList<Contact> contactList) {
        listView.setItems((ObservableList<Object>) (Object) contactList);
        listView.setCellFactory(listView -> new ListViewCell());
    }

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList} of events.
     */
    public void setEventList(ObservableList<Event> eventList) {
        listView.setItems((ObservableList<Object>) (Object) eventList);
        listView.setCellFactory(listView -> new ListViewCell());
    }

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList} of todos.
     */
    public void setTodoList(ObservableList<Todo> todoList) {
        listView.setItems((ObservableList<Object>) (Object) todoList);
        listView.setCellFactory(listView -> new ListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of items.
     */
    class ListViewCell extends ListCell<Object> {
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else if (item instanceof Contact) {
                setGraphic(new ContactCard((Contact) item, getIndex() + 1).getRoot());
            } else if (item instanceof Event) {
                setGraphic(new EventCard((Event) item, getIndex() + 1).getRoot());
            } else if (item instanceof Todo) {
                setGraphic(new TodoCard((Todo) item, getIndex() + 1).getRoot());
            }
        }
    }
}
