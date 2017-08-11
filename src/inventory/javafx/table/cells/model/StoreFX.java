package inventory.javafx.table.cells.model;

import inventory.model.Store;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author VakSF
 */
public class StoreFX {
    
    /**
     * A bean to indicate if the cell using this model is checked.
     */
    private final SimpleBooleanProperty selected;
    
    /**
     * A database model containing a store.
     */
    private final Store store;
    
    /**
     * Create a new cell model which should be used with a {@link CheckBoxListCell}.
     *
     * @param store a database model containing a store.
     * @param selected a boolean flag to indicate if the cell is selected.
     */
    public StoreFX(Store store, boolean selected) {
        this.store = store;
        this.selected = new SimpleBooleanProperty(selected);
    }
    
    /**
     * Returns the boolean value that determines if the model is selected.
     *
     * @return <i>true</i> if selected, <i>false</i> otherwise.
     */
    public boolean getSelected() {
        return selected.get();
    }
    
    /**
     * Set the boolean value that determines if the model is selected.
     *
     * @param selected a boolean value to determine if the model is selected.
     */
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
    
    /**
     * Returns the <code>SimpleBooleanProperty</code>.
     *
     * @return the <code>SimpleBooleanProperty</code>
     */
    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }
    
    /**
     * Returns the underlying database model containing a store.
     *
     * @return the underlying database model containing a store.
     */
    public Store getStore() {
        return store;
    }
    
    /**
     * Returns a string representation of the cell model.
     *
     *  @return a string representation of the cell model.
     */
    @Override
    public String toString() {
        return store.getName();
    }
    
}
