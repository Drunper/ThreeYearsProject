package it.unibs.ing.domohouse.interfaces;

import java.io.Serializable;
import java.util.function.BiConsumer;

public interface SerializableBiConsumer<T,U> extends Serializable, BiConsumer<T,U> {

}
