package group.playingcardsdemo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TogglingArray<T> extends CircularArray<T> {
    protected T currentT;
    protected int currentIndex;
    protected String packagePath;

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
        currentIndex = indexWrap(currentIndex);
        currentT = array[currentIndex];
    }
    public void toggleRight() {
        currentIndex++;
        currentIndex = indexWrap(currentIndex);
        currentT = array[currentIndex];
    }
}
