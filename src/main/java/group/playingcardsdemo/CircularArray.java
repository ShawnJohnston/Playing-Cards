package group.playingcardsdemo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class CircularArray <T> {
    public T[] array;
    public HashMap<T, Integer> hashMap;

    public CircularArray(T[] array) {
        this.array = array;
        initializeHashMap();
    }
    public T get(int i) {
        if (i < 0) {
            return array[array.length - 1];
        }
        else if (i < array.length) {
            return array[i];
        }
        else if (i == array.length) {
            return array[0];
        }
        return null;
    }
    public int getLength() {
        return array.length;
    }
    public void set(int i, T item) {
        array[i] = item;
    }
    private void initializeHashMap() {
        hashMap = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            hashMap.put(array[i], i);
        }
    }
}