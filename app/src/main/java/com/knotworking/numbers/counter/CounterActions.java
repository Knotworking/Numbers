package com.knotworking.numbers.counter;

/**
 * Created by BRL on 20/04/17.
 */

public interface CounterActions {
    boolean itemLongClick(int id);

    void incrementCount(int id);

    void decrementCount(int id);
}
