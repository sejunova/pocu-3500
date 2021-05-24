package academy.pocu.comp3500.lab4;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.CRC32;

public final class Cracker {
    private final int hashIndex;
    private final User[] userTable;

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        String passwordHash = null;
        for (User user : userTable) {
            if (user.getEmail().equals(email)) {
                passwordHash = user.getPasswordHash();
                break;
            }
        }

        if (getHash(password, "CRC32").equals(passwordHash)) {
            hashIndex = 0;
        } else if (getHash(password, "MD2").equals(passwordHash)) {
            hashIndex = 1;
        } else if (getHash(password, "MD5").equals(passwordHash)) {
            hashIndex = 2;
        } else if (getHash(password, "SHA1").equals(passwordHash)) {
            hashIndex = 3;
        } else {
            hashIndex = 4;
        }
    }


    public String[] run(final RainbowTable[] rainbowTables) {
        String[] passwords = new String[userTable.length];
        for (int i = 0; i < userTable.length; i++) {
            passwords[i] = rainbowTables[hashIndex].get(userTable[i].getPasswordHash());
        }
        return passwords;
    }

    private static String getHash(String password, String algorithm) {
        if (algorithm.equals("CRC32")) {
            CRC32 crc32 = new CRC32();
            crc32.update(password.getBytes());
            return String.format("%d", crc32.getValue());
        }

        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] messageDigest = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
