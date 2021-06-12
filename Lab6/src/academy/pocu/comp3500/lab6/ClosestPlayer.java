package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class ClosestPlayer {
    Player cp;
    int baseRating;

    ClosestPlayer(int baseRating) {
        this.baseRating = baseRating;
    }

    void updateNodes(Player player) {
        if (cp == null) {
            cp = player;
        } else {
            int curDiff = Math.abs(baseRating - cp.getRating());
            int diff = Math.abs(baseRating - player.getRating());
            if (diff < curDiff || (diff == curDiff && player.getRating() > cp.getRating())) {
                cp = player;
            }
        }
    }
}
