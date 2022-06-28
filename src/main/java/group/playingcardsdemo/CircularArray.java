package group.playingcardsdemo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CircularArray <T> {
    public T[] array;
    int length;

    public CircularArray(T[] array) {
        this.array = array;
        length = array.length;
    }
    public T get(int i) {
        if (i < 0) {
            return array[length - 1];
        }
        else if (i < length) {
            return array[i];
        }
        else if (i == length) {
            return array[0];
        }
        return null;
    }
    public void set(int i, T item) {
        array[i] = item;
    }
}