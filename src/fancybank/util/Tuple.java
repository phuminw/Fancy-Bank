/*
 * Author: Phumin Walaipatchara
 */

package fancybank.util;

/**
 * A immutable tuple class to hold two objects
 * 
 * @param <T> First object
 * @param <U> Second object
 */

public class Tuple<T, U> {
    private T t;
    private U u;
    
    public Tuple(T t, U u) {
        this.t = t;
        this.u = u;
    }
    public T getFirst() {
        return t;
    }
    public U getSecond() {
        return u;
    }

    /**
     * equals method to check equality
     * 
     * @param other object to check against
     * @return equality result 
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    
    public boolean equals(Object other) {
        if (!getClass().equals(other.getClass()))
            return false;
        Tuple otherT = (Tuple) other;
        try {
            return t.equals((T) otherT.getFirst()) && u.equals((U) otherT.getSecond());
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", t, u);
    }
}