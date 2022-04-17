package Interfaces;

import Data.Collectables;
import Data.CollectionController;

/**
 * Responsible for creating class objects.
 *
 * @param <T> the class whose object is being formed
 */
public interface IFormer<T extends Collectables> {
    T formObj(String object);
}
