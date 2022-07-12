package group.playingcardsdemo;

import group.playingcardsdemo.FileIO.PayoutSheet;
import group.playingcardsdemo.FileIO.UTHPayoutSheet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GameConfig {
    public Player player;
    public GameMode gameMode;
    public PayoutSheet payoutSheet;
    public Image avatar;
    public Image cardBack;

    GameConfig(Player player, Games game, Image avatar, Image cardBack) {
        this.player = player;
        this.avatar = avatar;
        this.cardBack = cardBack;

        switch (game) {
            case UTH:
                this.gameMode = new UTH(game, player);
                this.payoutSheet = new UTHPayoutSheet();
                break;
            default:
                break;
        }
    }
}
