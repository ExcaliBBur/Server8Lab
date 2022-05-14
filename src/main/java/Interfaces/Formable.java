package Interfaces;

import Models.Collectables;

/**
 * Responsible for creating class objects.
 *
 * @param <T> the class whose object is being formed
 */
public interface Formable<T extends Collectables> {
    T formObj(String object);
}
