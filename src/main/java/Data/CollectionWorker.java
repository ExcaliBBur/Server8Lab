package Data;

import Interfaces.IFormer;

import java.util.List;

/**
 * Abstract class that represents all classes for working with a collection.
 *
 * @param <T> the class whose objects the implementation should work with
 */
public abstract class CollectionWorker<T extends Collectables> extends Worker {
    private CollectionController<T> collectionController;
    private IFormer<T> iFormer;

    /**
     * Constructor, gets all necessary things.
     *
     * @param commands             list of commands to work with
     * @param collectionController complete controller to work with
     * @param iFormer              object former
     */
    public CollectionWorker(CollectionController<T> collectionController, List<Command> commands, IFormer<T> iFormer) {
        super(commands);
        this.collectionController = collectionController;
        this.iFormer = iFormer;
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

    public IFormer<T> getIFormer() {
        return iFormer;
    }
}
