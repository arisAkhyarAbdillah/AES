public class Main {
    public static void main(String[] args) {
        String plainText = "Kuis kan 10 nomor";
        String key = "hdjskfhbvyeplmznv";

        Hex[] pInHexs = Hex.hexArrayOf(plainText);
        Hex[] kInHexs = Hex.hexArrayOf(key);

        for (int i = 0; i < plainText.length(); i++) {
            System.out.print(plainText.charAt(i) + ((i==plainText.length()-1)?"\n":"\t"));
        }

        for (int i=0; i<pInHexs.length; i++){
            System.out.print(pInHexs[i].get()+((i==pInHexs.length-1)?"\n":"\t"));
        }

        for (int i = 0; i < key.length(); i++) {
            System.out.print(key.charAt(i) + ((i==key.length()-1)?"\n":"\t"));
        }

        for (int i=0; i<kInHexs.length; i++){
            System.out.print(kInHexs[i].get()+((i==kInHexs.length-1)?"\n":"\t"));
        }

        // Mix Columns Testing
        // String[][] a = {{"02","03","01","01"},{"01","02","03","01"},{"01","01","02","03"},{"03","01","01","02"}};
        // // String[][] b = {{"D4","E0","B8","1E"},{"BF","B4","41","27"},{"5D","52","11","98"},{"30","AE","F1","E5"}};
        // String[][] b = {{"63","EB","9F","A0"},{"2F","93","92","C0"},{"AF","C7","AB","30"},{"A2","20","CB","2B"}};
        // Hex[][] hA = new Hex[a.length][a[0].length];
        // Hex[][] hB = new Hex[b.length][b[0].length];
        // for (int i=0; i<a.length; i++){
        //     for (int j=0; j<a[0].length; j++){
        //         hA[i][j] = new Hex(a[i][j]);
        //     }
        // }
        // for (int i=0; i<b.length; i++){
        //     for (int j=0; j<b[0].length; j++){
        //         hB[i][j] = new Hex(b[i][j]);
        //     }
        // }
        // Hex[][] c = mixCol(hA,hB);
        // printMatrix(c);
    }

    public static void printMatrix(Hex[][] m){
        for (int i=0; i<m.length; i++){
            for (int j=0; j<m[i].length; j++){
                System.out.printf("%5s", m[i][j].get());
            }
            System.out.println();
        }
    }

    public static Hex[][] mixCol(Hex[][] mA, Hex[][] mB){
        int[][] dA = new int[mA.length][mA[1].length];
        int[][] dB = new int[mB.length][mB[1].length];

        for (int i=0; i<mA.length; i++){
            for (int j=0; j<mA[i].length; j++){
                dA[i][j] = mA[i][j].toDec();
            }
        }

        for (int i=0; i<mB.length; i++){
            for (int j=0; j<mB[i].length; j++){
                dB[i][j] = mB[i][j].toDec();
            }
        }
        int[][] mMixed = product(dA, dB);
        Hex[][] mixed = new Hex[mMixed.length][mMixed[1].length];

        for (int i=0; i<mMixed.length; i++){
            for (int j=0; j<mMixed[i].length; j++){
                mixed[i][j] = Hex.valueOf(mMixed[i][j]);
            }
        }

        return mixed;
    }

    private static int[][] product(int[][] mA, int[][] mB){
        int[][] m = new int[mA.length][mB[1].length];

        for (int i=0; i<mA.length; i++){
            for (int j=0; j<mB[1].length; j++){
                for (int k=0; k<mB.length; k++){
                    m[i][j] ^= GaloisField.dotProduct(mA[i][k], mB[k][j]);
                }
                String red = GaloisField.reductionMod(Integer.toBinaryString(m[i][j]));
                m[i][j] = Integer.parseInt(red, 2);
            }
        }
        return m;
    }

    public static Hex sBox(Hex hex) {
        int x = 0;
        int y = 0;
        if (hex.length() < 2) {
            y = hex.toDec();
        } else {
            Hex a = new Hex(""+hex.charAt(0));
            Hex b = new Hex(""+hex.charAt(1));
            x = a.toDec();
            y = b.toDec();
        }
        return new Hex(SBOX[x][y]);
    }

    public static final String[][] SBOX = {
        {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"},
        {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "A2", "A2", "AF", "9C", "A4", "72", "C0"},
        {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"},
        {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"},
        {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"},
        {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"},
        {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"},
        {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"},
        {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"},
        {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"},
        {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"},
        {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"},
        {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"},
        {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"},
        {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"},
        {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"},
    };
}