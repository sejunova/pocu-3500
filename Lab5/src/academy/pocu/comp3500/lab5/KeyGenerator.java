package academy.pocu.comp3500.lab5;

import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator {
    public static boolean isPrime(final BigInteger number) {
        return isProbablePrime(number, 10);
    }

    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");

    public static boolean isProbablePrime(BigInteger n, int k) {
        if (n.compareTo(TWO) < 0)
            return false;
        if (n.equals(TWO))
            return true;
        int s = 0;
        BigInteger d = n.subtract(ONE);
        while (d.mod(TWO).equals(ZERO)) {
            s++;
            d = d.divide(TWO);
        }
        for (int i = 0; i < k; i++) {
            BigInteger a = uniformRandom(TWO, n.subtract(ONE));
            BigInteger x = a.modPow(d, n);
            if (x.equals(ONE) || x.equals(n.subtract(ONE)))
                continue;
            int r = 0;
            for (; r < s; r++) {
                x = x.modPow(TWO, n);
                if (x.equals(ONE))
                    return false;
                if (x.equals(n.subtract(ONE)))
                    break;
            }
            if (r == s) // None of the steps made x equal n-1.
                return false;
        }
        return true;
    }

    private static BigInteger uniformRandom(BigInteger bottom, BigInteger top) {
        Random rnd = new Random();
        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), rnd);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
    }
}

