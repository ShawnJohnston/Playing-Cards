package group.playingcardsdemo.PlayingCards;

public interface CardInterface {
    String value = null;
    String suit = null;
    String name = null;
    String color = null;
    String front = null;
    String back = null;
    Facing currentFacing = Facing.faceDown;
}
