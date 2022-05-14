package Models;

import Interfaces.Formable;

import java.util.List;

/**
 * Abstract class that represents all classes for working with a collection.
 *
 * @param <T> the class whose objects the implementation should work with
 */
public abstract class CollectionWorker<T extends Collectables> extends Worker {
    private CollectionController<T> collectionController;
    private Formable<T> formable;

    /**
     * Constructor, gets all necessary things.
     *
     * @param commands             list of commands to work with
     * @param collectionController complete controller to work with
     * @param formable              object former
     */
    public CollectionWorker(CollectionController<T> collectionController, List<Command> commands, Formable<T> formable) {
        super(commands);
        this.collectionController = collectionController;
        this.formable = formable;
    }

    /**
     * Initializing realization of commands.
     */
    public abstract void initCommands();

    public CollectionController<T> getController() {
        return collectionController;
    }

    public void setController(CollectionController<T> collectionController) {
        this.collectionController = collectionController;
    }

    public Formable<T> getIFormer() {
        return formable;
    }
}
