package academy.pocu.comp3500.lab5;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

public class Bank {
    HashMap<byte[], Long> pubKeyAmountMap = new HashMap<>();

    public Bank(byte[][] pubKeys, final long[] amounts) {
        for (int i = 0; i < pubKeys.length; i++) {
            pubKeyAmountMap.put(pubKeys[i], amounts[i]);
        }
    }

    public long getBalance(final byte[] pubKey) {
        if (pubKeyAmountMap.containsKey(pubKey)) {
            return pubKeyAmountMap.get(pubKey);
        }
        return 0;
    }

    public boolean transfer(final byte[] from, byte[] to, final long amount, final byte[] signature) {
        // 선행조건
        if (!pubKeyAmountMap.containsKey(from)) {
            return false;
        }

        long senderAmount = pubKeyAmountMap.get(from);
        long receiverAmount;
        if (pubKeyAmountMap.containsKey(to)) {
            receiverAmount = pubKeyAmountMap.get(to);
        } else {
            pubKeyAmountMap.put(to, 0L);
            receiverAmount = 0;
        }
        if (senderAmount <= 0 || amount <= 0) {
            return false;
        }

        if (amount > senderAmount) {
            return false;
        }

        if (receiverAmount + amount < 0) {
            return false;
        }

        // 복호화 시작
        byte[] message = new byte[from.length + to.length + Long.BYTES];
        int i = 0;
        for (byte b : from) {
            message[i++] = b;
        }
        for (byte b : to) {
            message[i++] = b;
        }

        for (byte b : longToBytes(amount)) {
            message[i++] = b;
        }


        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageHashed = md.digest(message);
            PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(from));

            byte[] signatureDecrypted = decryptWithPublicKey(signature, pubKey);
            for (int k = 0; k < signatureDecrypted.length; k++) {
                if (signatureDecrypted[k] != messageHashed[k]) {
                    return false;
                }
            }

            pubKeyAmountMap.put(from, senderAmount - amount);
            pubKeyAmountMap.put(to, receiverAmount + amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] longToBytes(long l) {
        byte[] result = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i >= 0; i--) {
            result[i] = (byte) (l & 0xFF);
            l >>= Byte.SIZE;
        }
        return result;
    }

    private static byte[] decryptWithPublicKey(byte[] message, PublicKey publicKey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(message);
    }

}