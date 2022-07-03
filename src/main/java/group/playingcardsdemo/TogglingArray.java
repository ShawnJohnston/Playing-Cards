package group.playingcardsdemo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TogglingArray<T> extends CircularArray<T> {

    private T currentT;
    private int currentIndex;
    private String packagePath;

    public TogglingArray(T[] array) {
        this.array = array;
        currentT = array[0];
        currentIndex = 0;
    }
    public TogglingArray(T[] array, String packagePath) {
        this.array = array;
        this.packagePath = packagePath;
        currentT = array[0];
        currentIndex = 0;
    }
    public void toggleLeft() {
        currentIndex--;
        indexWrap();
        currentT = array[currentIndex];
    }
    public void toggleRight() {
        currentIndex++;
        indexWrap();
        currentT = array[currentIndex];
    }
    private void indexWrap() {
        if (currentIndex < 0) {
            currentIndex = array.length - 1;
        }
        else if (currentIndex >= array.length) {
            currentIndex = 0;
        }
    }
}
