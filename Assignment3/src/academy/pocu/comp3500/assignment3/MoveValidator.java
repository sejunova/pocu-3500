package academy.pocu.comp3500.assignment3;

import academy.pocu.comp3500.assignment3.chess.Move;
import academy.pocu.comp3500.assignment3.chess.PlayerBase;

final class MoveValidator {
    public final static int BOARD_SIZE = 8;

    private static final int[][] KING_MOVE_OFFSETS = {
            {-1, 1},
            {-1, 0},
            {-1, -1},
            {0, 1},
            {0, -1},
            {1, 1},
            {1, 0},
            {1, -1}
    };

    private static final int[][] KNIGHT_MOVE_OFFSETS = {
            {-2, -1},
            {-2, 1},
            {-1, -2},
            {-1, 2},
            {1, -2},
            {1, 2},
            {2, -1},
            {2, 1}
    };

    public static boolean isMoveValid(char[][] board, PlayerBase player, Move moveOrNull) {
        if (moveOrNull == null) {
            return false;
        }

        if (moveOrNull.fromX >= BOARD_SIZE || moveOrNull.fromX < 0
                || moveOrNull.fromY >= BOARD_SIZE || moveOrNull.fromY < 0) {
            return false;
        }

        final char symbol = board[moveOrNull.fromY][moveOrNull.fromX];

        if (symbol == 0) {
            return false;
        }

        if ((player.isWhite() && !Character.isLowerCase(symbol))
                || !player.isWhite() && Character.isLowerCase(symbol)) {
            return false;
        }

        if (moveOrNull.toX >= BOARD_SIZE || moveOrNull.toX < 0
                || moveOrNull.toY >= BOARD_SIZE || moveOrNull.toY < 0) {
            return false;
        }

        if (moveOrNull.fromX == moveOrNull.toX && moveOrNull.fromY == moveOrNull.toY) {
            return false;
        }

        char symbolInvariant = Character.toLowerCase(symbol);

        switch (symbolInvariant) {
            case 'p':
                return isPawnMoveValid(board, moveOrNull);

            case 'n':
                return isKnightMoveValid(board, moveOrNull);

            case 'b':
                return isBishopMoveValid(board, moveOrNull);

            case 'r':
                return isRookMoveValid(board, moveOrNull);

            case 'q':
                return isQueenMoveValid(board, moveOrNull);

            case 'k':
                return isKingMoveValid(board, moveOrNull);

            default:
                throw new IllegalArgumentException("Unknown piece symbol");
        }
    }



    public static boolean isBishopMoveValid(char[][] board, Move move) {
        char fromPiece = board[move.fromY][move.fromX];
        char toPiece = board[move.toY][move.toX];

        if (toPiece != 0 && Character.isLowerCase(fromPiece) == Character.isLowerCase(toPiece)) {
            return false;
        }

        if (Math.abs(move.fromX - move.toX) != Math.abs(move.fromY - move.toY)) {
            return false;
        }

        int xIncrement = move.fromX < move.toX ? 1 : -1;
        int yIncrement = move.fromY < move.toY ? 1 : -1;

        int x = move.fromX + xIncrement;
        int y = move.fromY + yIncrement;

        while (x != move.toX && y != move.toY) {
            if (board[y][x] != 0 && x != move.toX && y != move.toY) {
                return false;
            }

            x += xIncrement;
            y += yIncrement;
        }

        return true;
    }

    public static boolean isRookMoveValid(char[][] board, Move move) {
        char fromPiece = board[move.fromY][move.fromX];
        char toPiece = board[move.toY][move.toX];

        if (toPiece != 0 && Character.isLowerCase(fromPiece) == Character.isLowerCase(toPiece)) {
            return false;
        }

        if (move.fromX == move.toX) {
            int yIncrement = move.fromY < move.toY ? 1 : -1;

            int y = move.fromY + yIncrement;

            while (y != move.toY) {
                if (board[y][move.fromX] != 0) {
                    return false;
                }

                y += yIncrement;
            }

            return true;

        } else if (move.fromY == move.toY) {
            int xIncrement = move.fromX < move.toX ? 1 : -1;

            int x = move.fromX + xIncrement;

            while (x != move.toX) {
                if (board[move.fromY][x] != 0) {
                    return false;
                }

                x += xIncrement;
            }

            return true;
        }

        return false;
    }

    public static boolean isKnightMoveValid(char[][] board, Move move) {
        char fromPiece = board[move.fromY][move.fromX];
        char toPiece = board[move.toY][move.toX];

        if (toPiece != 0 && Character.isLowerCase(fromPiece) == Character.isLowerCase(toPiece)) {
            return false;
        }

        for (int i = 0; i < KNIGHT_MOVE_OFFSETS.length; ++i) {
            if (move.fromX + KNIGHT_MOVE_OFFSETS[i][0] == move.toX && move.fromY + KNIGHT_MOVE_OFFSETS[i][1] == move.toY) {
                return true;
            }
        }

        return false;
    }

    public static boolean isQueenMoveValid(char[][] board, Move move) {
        return isBishopMoveValid(board, move) || isRookMoveValid(board, move);
    }

    public static boolean isKingMoveValid(char[][] board, Move move) {
        char fromPiece = board[move.fromY][move.fromX];
        char toPiece = board[move.toY][move.toX];

        if (toPiece != 0 && Character.isLowerCase(fromPiece) == Character.isLowerCase(toPiece)) {
            return false;
        }

        for (int i = 0; i < KING_MOVE_OFFSETS.length; ++i) {
            if (move.fromX + KING_MOVE_OFFSETS[i][0] == move.toX && move.fromY + KING_MOVE_OFFSETS[i][1] == move.toY) {
                return true;
            }
        }

        return false;
    }

    public static boolean isPawnMoveValid(char[][] board, Move move) {
        char fromPiece = board[move.fromY][move.fromX];
        char toPiece = board[move.toY][move.toX];

        boolean isFromPieceWhite = Character.isLowerCase(fromPiece);
        boolean isToPieceWhite = Character.isLowerCase(toPiece);

        if (toPiece != 0 && isFromPieceWhite == isToPieceWhite) {
            return false;
        }

        if (toPiece != 0 && move.fromX == move.toX) {
            return false;
        }

        boolean hasMoved = isFromPieceWhite ? move.fromY != 6 : move.fromY != 1;

        if (!hasMoved && move.fromX == move.toX && Math.abs(move.toY - move.fromY) == 2) {
            if (move.toY > move.fromY && !isFromPieceWhite && board[move.toY - 1][move.toX] == 0) {
                return true;
            }

            return move.toY < move.fromY && isFromPieceWhite && board[move.toY + 1][move.toX] == 0;
        } else if (move.fromX == move.toX && Math.abs(move.toY - move.fromY) == 1) {
            if (move.toY > move.fromY && !isFromPieceWhite) {
                return true;
            }

            return move.toY < move.fromY && isFromPieceWhite;
        } else if (move.toX == move.fromX - 1 || move.toX == move.fromX + 1) {
            if (toPiece != 0 && isToPieceWhite != isFromPieceWhite) {
                return isFromPieceWhite ? move.toY == move.fromY - 1 : move.toY == move.fromY + 1;
            }
        }

        return false;
    }
}