package group.playingcardsdemo.Structures;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

@Getter
@Setter
@NoArgsConstructor
public class CircularArray <T> {
    protected T[] array;
    public HashMap<T, Integer> hashMap;

    public CircularArray(T[] array) {
        this.array = array;
        initializeHashMap();
    }

    public T get(int i) {
        return array[indexWrap(i)];
    }
    public int getLength() {
        return array.length;
    }
    public void set(int i, T item) {
        //  Writes desired data to a specified index of the circular array.
        int index = indexWrap(i);
        array[index] = item;
    }

    private void initializeHashMap() {
        /*
            This method will initialize a hash map for the class, in case it is desired.

            1.  Construct new hashMap.
            2.  For loop: 0 < i < array length.
                -   put the item at index 'i' into the hashMap.

         */
        hashMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            hashMap.put(array[i], i);
        }
    }
    protected int indexWrap(int i) {
        /*
            This method's purpose is to ensure that attempts to access indexes that are out-of-bounds will overflow or
            underflow, creating the circular aspect of the class.

            1.  If 'i' is negative, return the sum of 'i' and the array's length. This will cause a cycle/underflow.
            3.  Else if 'i' is equal or greater than the array's length, return 'i' minus the array's length. This will
                cause a cycle/overflow effect.
            4.  Else, return 'i'.
         */

        if (i < 0) {
            return array.length + i;
        }
        else if (i >= array.length) {
            return i - array.length;
        }
        else {
            return i;
        }
    }
}