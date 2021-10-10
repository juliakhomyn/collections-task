package co.inventorsoft.academy.collections.model;

import java.util.*;
import java.util.function.Function;

public class Range<T> implements Set<T>, Comparable<T> {
    private T[] array;
    private int size;
    private int index;

    public Range() {
        array = (T[]) new Object[16];
        size = 0;
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new SetIterator();
    }

    private class SetIterator implements Iterator<T> {
        private int index = 0;
        private T el;
        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            el = array[index];
            index++;
            return el;
        }

        @Override
        public void remove() {

        }
    }

    public Object[] toArray() {
        Object[] newArray = new Object[size];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }

    public <T1> T1[] toArray(T1[] a) {
        for (int i = 0; i < size; i++) {
            a[i] = (T1)array[i];
        }
        for (int i = size; i < a.length; i++) {
            a[i] = null;
        }
        return a;
    }

    public boolean add(T t) {
        for (Object el : array) {
            if (el == null) {
                if (t == null) {
                    return false;
                }
            } else if (el.equals(t)) {
                return false;
            }
        }
        if (size == array.length) {
            T[] newArray = Arrays.copyOf(array, array.length * 2);
            newArray[size] = t;
            size++;
            array = newArray;
            return true;
        } else {
            array[size] = t;
            size++;
            return true;
        }
    }

    public boolean remove(Object o) {
        int i = 0;
        for (Object el : array) {
            if (o != null && o.equals(array[i])) {
                System.arraycopy(array, i + 1, array, i, size - i - 1);
                array[size - 1] = null;
                size--;
                return true;
            }
            i += 1;
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        for (T t : (List<T>)c) {
            if (!(this.contains(t))) {
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            this.add(t);
        }
        return true;
    }

    public boolean retainAll(Collection<?> c) {
        int index = 0;
        boolean result = false;
        if (this.containsAll(c)) {
            result = true;
        }
        while (index < size) {
            T t = array[index];
            if (t != null && !(c.contains(t))) {
                this.remove(t);
            } else {
                index++;
            }
        }
        return result;
    }

    public boolean removeAll(Collection<?> c) {
        for (Object el : c) {
            this.remove(el);
        }
        return true;
    }

    public void clear() {
        T[] newArray = (T[]) new Object[array.length];
        size = 0;
        array = newArray;
    }

    public static <T> Range<T> of(T t1, T t2) {
        Range<T> range = new Range<>();
        if (t1.equals(t2)) {
            return range;
        }
        T el = t1;
        if (t1 instanceof Integer) {
            while ((Integer) el <= (Integer) t2) {
                range.add(el);
                el = (T) increase((Integer) el);
            }
        }
        if (t1 instanceof Float) {
            while ((Float) el <= (Float) t2) {
                range.add(el);
                el = (T) increase((Float) el);
            }
        }
        return range;
    }

    private static Integer increase(Integer el) {
        return el + 1;
    }

    private static Float increase(Float el) {
        return el + 0.1f;
    }

    public int compareTo(T o) {
        if (this.compareTo(o) > 0) {
            return 1;
        } else if(this.compareTo(o) == 0) {
            return 0;
        }
        return 1;
    }

    public static Range<Character> of(char a, char d, Function<Character, Character> characterCharacterFunction) {
        Range<Character> range = new Range<>();
        if (a == d) {
            return range;
        }
        char el = a;
        while (el <= d) {
            range.add(el);
            el = characterCharacterFunction.apply(el);
        }
        return range;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size - 1; i++) {
            sb.append(array[i]).append(", ");
        }
        if (size > 0) {
            sb.append(array[size - 1]);
        }
        sb.append(']');
        return sb.toString();
    }
}
