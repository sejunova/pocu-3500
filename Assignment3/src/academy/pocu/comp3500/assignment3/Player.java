package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

import java.util.ArrayList;
import java.util.List;

import static academy.pocu.comp3500.assignment3.MoveValidator.BOARD_SIZE;

public class Player extends PlayerBase {
    private static final int MAX_DEPTH = 4;
    private static final int KING_VALUE = 900;
    private static final int QUEEN_VALUE = 90;
    private static final int ROCK_VALUE = 50;
    private static final int BISHOP_VALUE = 30;
    private static final int KNIGHT_VALUE = 30;
    private static final int PAWN_VALUE = 10;

    public Player(boolean isWhite, int maxMoveTimeMilliseconds) {
        super(isWhite, maxMoveTimeMilliseconds);
    }

    @Override
    public Move getNextMove(char[][] board) {
        board = createCopy(board);
        Score score = getBestMoveRecursive(board, this, new Player(!isWhite(), this.getMaxMoveTimeMilliseconds()), this, new Score(new Move(0, 0, 0, 0), 0), 0);
        return score.move;
    }

    @Override
    public Move getNextMove(char[][] board, Move opponentMove) {

        board = createCopy(board);
        Score score = getBestMoveRecursive(board, this, new Player(!isWhite(), this.getMaxMoveTimeMilliseconds()), this, new Score(opponentMove, 0), 0);
        return score.move;
    }

    private Score getBestMoveRecursive(final char[][] board, final Player player, final Player opponent, final Player turn, Score prevScore, int depth) {
        if (depth == MAX_DEPTH || isKingCaptured(board)) {
            return prevScore;
        }


        List<Move> nextAvailableMoves = getNextAvailableMoves(board, turn);
        List<Score> scores = new ArrayList<>(nextAvailableMoves.size());
        for (Move nextAvailableMove:nextAvailableMoves) {
            int scoreEarned = getScoreByCatching(board, nextAvailableMove) + getScoreByPositioning(nextAvailableMove);


            Player nextPlayer;
            Score nextScore;
            if (player == turn) {
                nextScore = new Score(nextAvailableMove, prevScore.score + scoreEarned);
                nextPlayer = opponent;
            } else {
                nextScore = new Score(nextAvailableMove, prevScore.score - scoreEarned);
                nextPlayer = player;
            }

            char fromChar = board[nextAvailableMove.fromY][nextAvailableMove.fromX];
            char toChar = board[nextAvailableMove.toY][nextAvailableMove.toX];

            board[nextAvailableMove.toY][nextAvailableMove.toX] = fromChar;
            board[nextAvailableMove.fromY][nextAvailableMove.fromX] = '\0';

            int bestScore = getBestMoveRecursive(board, player, opponent, nextPlayer, nextScore, depth + 1).score;

            board[nextAvailableMove.fromY][nextAvailableMove.fromX] = fromChar;
            board[nextAvailableMove.toY][nextAvailableMove.toX] = toChar;
            scores.add(new Score(nextAvailableMove, bestScore));
        }

        if (turn == player) {
            return getMaxScoreMove(scores);
        }

        return getMinScoreMove(scores);
    }

    private static int getScoreByCatching(final char[][] board, Move move) {
        if (Character.toLowerCase(board[move.toY][move.toX]) == 'k') {
            return KING_VALUE;
        } else if (Character.toLowerCase(board[move.toY][move.toX]) == 'q') {
            return QUEEN_VALUE;
        } else if (Character.toLowerCase(board[move.toY][move.toX]) == 'r') {
            return ROCK_VALUE;
        } else if (Character.toLowerCase(board[move.toY][move.toX]) == 'b') {
            return BISHOP_VALUE;
        } else if (Character.toLowerCase(board[move.toY][move.toX]) == 'n') {
            return KNIGHT_VALUE;
        } else if (Character.toLowerCase(board[move.toY][move.toX]) == 'p') {
            return PAWN_VALUE;
        } else {
            return 0;
        }
    }

    private static int getScoreByPositioning(Move move) {
        int fromScore = 0;
        if (0 <= move.fromX && move.fromX <= 2) {
            fromScore -= move.fromX - 3;
        } else if (5 <= move.fromX && move.fromX <= 7) {
            fromScore -= 4 - move.fromX;
        }
        if (0 <= move.fromY && move.fromY <= 2) {
            fromScore -= move.fromY - 3;
        } else if (5 <= move.fromY && move.fromY <= 7) {
            fromScore -= 4 - move.fromY;
        }

        int toScore = 0;
        if (0 <= move.toX && move.toX <= 2) {
            toScore -= move.toX - 3;
        } else if (5 <= move.toX && move.toX <= 7) {
            toScore -= 4 - move.toX;
        }
        if (0 <= move.toY && move.toY <= 2) {
            toScore -= move.toY - 3;
        } else if (5 <= move.toY && move.toY <= 7) {
            toScore -= 4 - move.toY;
        }
        return toScore - fromScore;
    }

    private static Score getMaxScoreMove(final List<Score> Scores) {
        Score bestMove = Scores.get(0);
        for (int i = 1; i < Scores.size(); ++i) {
            if (Scores.get(i).score > bestMove.score) {
                bestMove = Scores.get(i);
            }
        }
        return bestMove;
    }

    private static Score getMinScoreMove(final List<Score> scores) {
        Score bestMove = scores.get(0);
        for (int i = 1; i < scores.size(); ++i) {
            if (scores.get(i).score < bestMove.score) {
                bestMove = scores.get(i);
            }
        }
        return bestMove;
    }




    private static List<Move> getNextAvailableMoves(final char[][] board, Player player) {
        List<Move> nextAvailableMoves = new ArrayList<>();
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (x ==4 && y == 4) {
                    int z=  0;
                }
                if (board[y][x] == '\0' || (player.isWhite() && Character.isUpperCase(board[y][x])) || (!player.isWhite() && Character.isLowerCase(board[y][x]))) {
                    continue;
                }
                addNextAvailableMoves(board, player, x, y, nextAvailableMoves);
            }
        }
        return nextAvailableMoves;
    }

    private static void addNextAvailableMoves(final char[][] board, Player player, final int fromX, final int fromY, final List<Move> nextAvailableMoves) {
        for (int toY = 0; toY < BOARD_SIZE; toY++) {
            for (int toX = 0; toX < BOARD_SIZE; toX++) {
                Move move = new Move(fromX, fromY, toX, toY);
                if (MoveValidator.isMoveValid(board, player, move)) {
//                    char[][] fromBoard = new char[BOARD_SIZE][BOARD_SIZE];
//                    char[][] toBoard = new char[BOARD_SIZE][BOARD_SIZE];
//                    fromBoard[fromY][fromX] = board[fromY][fromX];
//                    toBoard[toY][toX] = board[fromY][fromX];

                    nextAvailableMoves.add(move);
                }
            }
        }
    }

    public static boolean isKingCaptured(final char[][] board) {
        boolean blackKingAlive = false;
        boolean whiteKingAlive = false;

        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (board[y][x] == 'K') {
                    blackKingAlive = true;
                }
                if (board[y][x] == 'k') {
                    whiteKingAlive = true;
                }
            }
        }
        return !(blackKingAlive && whiteKingAlive);
    }

    private static class Score {
        Move move;
        int score;

        public Score(Move move, int score) {
            this.move = move;
            this.score = score;
        }
    }

    private static char[][] createCopy(final char[][] board) {
        assert (board.length == BOARD_SIZE);
        assert (board[0].length == BOARD_SIZE);

        final char[][] copy = new char[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                copy[i][j] = board[i][j];
            }
        }

        return copy;
    }

    public static void main(String[] args) {
        Player white = new Player(true, 100000000);
        Player black = new Player(false, 100000000);

        char[][] board = new char[][] {
                {'\0', '\0', '\0', 'K', '\0', '\0', '\0', '\0'},
                {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
                {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
                {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
                {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
                {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
                {'\0', '\0', '\0', '\0', '\0', '\0', '\0', '\0'},
                {'\0', '\0', '\0', '\0', 'k', '\0', '\0', '\0'},
        };
        Move move = black.getNextMove(board, new Move(4, 1, 4, 2));
        int x = 0;
    }

}
