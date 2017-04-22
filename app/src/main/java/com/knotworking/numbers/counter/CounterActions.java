package com.knotworking.numbers.counter;

/**
 * Created by BRL on 20/04/17.
 */

public interface CounterActions {
    boolean deleteCounterItem(int id);

    void incrementCount(int id);

    void decrementCount(int id);
}
