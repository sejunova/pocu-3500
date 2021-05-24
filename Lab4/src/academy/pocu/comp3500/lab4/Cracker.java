package academy.pocu.comp3500.lab4;

import academy.pocu.comp3500.lab4.pocuhacker.RainbowTable;
import academy.pocu.comp3500.lab4.pocuhacker.User;

public final class Cracker {
    private User[] userTable;
    private String email;
    private String password;

    public Cracker(User[] userTable, String email, String password) {
        this.userTable = userTable;
        this.email = email;
        this.password = password;
    }


    public String[] run(final RainbowTable[] rainbowTables) {
        String[] passwords = new String[userTable.length];
        //ainbowTables[0]은 CRC32용 레인보우 테이블
        //rainbowTables[1]은 MD2용 레인보우 테이블
        //rainbowTables[2]은 MD5용 레인보우 테이블
        //rainbowTables[3]은 SHA1용 레인보우 테이블
        //rainbowTables[4]은 SHA256용 레인보우 테이블
        for (int i = 0; i < userTable.length; i++) {
            User user = userTable[i];
            String password;
            String hash = user.getPasswordHash();
            if (hash.length() == 9) {
                password = rainbowTables[0].get(hash);
                if (password != null) {
                    passwords[i] = password;
                }
            } else if (hash.length() == 24) {
                password = rainbowTables[1].get(hash);
                if (password != null) {
                    passwords[i] = password;
                    continue;
                }
                password = rainbowTables[2].get(hash);
                if (password != null) {
                    passwords[i] = password;
                }
            } else if (hash.length() == 28) {
                password = rainbowTables[3].get(hash);
                if (password != null) {
                    passwords[i] = password;
                }
            } else {
                password = rainbowTables[4].get(hash);
                if (password != null) {
                    passwords[i] = password;
                }
            }
        }
        return passwords;
    }
}
