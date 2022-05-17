package Interfaces;

import java.util.Collection;

public interface Informable<T> {
    void inform(Collection<T> collection);
}
