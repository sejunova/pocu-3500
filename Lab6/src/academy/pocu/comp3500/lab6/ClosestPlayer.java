package academy.pocu.comp3500.lab6;

import academy.pocu.comp3500.lab6.leagueofpocu.Player;

public class ClosestPlayer {
    Player left;
    Player right;
    int baseRating;

    ClosestPlayer(int baseRating) {
        this.baseRating = baseRating;
    }

    void updateNodes(Player player) {
        if (player.getRating() < baseRating) {
            if (left == null || player.getRating() > left.getRating()) {
                left = player;
            }
        } else {
            if (right == null || player.getRating() < right.getRating()) {
                right = player;
            }
        }
    }

    Player getClosestPlayer() {
        if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            int rightDiff = right.getRating() - baseRating;
            int leftDiff = baseRating - left.getRating();
            return (rightDiff <= leftDiff) ? right : left;
        }
    }
}
