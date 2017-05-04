package com.knotworking.numbers.counter;

/**
 * Created by BRL on 20/04/17.
 */

public interface CounterActions {
    boolean itemLongClick(int id);

    boolean nameLongClick(int id, String name);

    void incrementCount(int id);

    void decrementCount(int id);
}
