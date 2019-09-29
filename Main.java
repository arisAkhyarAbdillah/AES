import java.util.Scanner;
public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static int round = 0;
    private static Hex[][] state;
    private static final String[] RCON = new String[]{"01","02","04","08","10","20","40","80","1B","36"};
    public static void main(String[] args) {
        String plainText = "Two One Nine Two";
        String key = "Thats my Kung Fu";
        System.out.println("PLAIN TEXT: "+plainText);
        System.out.println("CHIPER KEY: "+key);

        Hex[] pInHexs = Hex.hexArrayOf(plainText);
        Hex[] kInHexs = Hex.hexArrayOf(key);
        state = toMatrix(pInHexs);
        System.out.println("\nTRANSLATION TO HEX:\n");
        System.out.print("PLAIN TEXT: "); printArray(pInHexs);
        System.out.print("CHIPER KEY: "); printArray(kInHexs);
        System.out.println("\nSTATE MATRIX: "); printMatrix(state);
        System.out.println("\nKEY MATRIX: "); printMatrix(toMatrix(kInHexs));

        Hex[][] keys = new Hex[11][16];

        command(scan.nextLine());

        // First RoundKey
        System.out.println("\n<==================> FIRST ROUND <==================>\n");
        keys[0] = kInHexs;
        System.out.print("Expand Key-"+round+": ");
        printArray(keys[0]);

        // First Add RoundKey
        System.out.println("\nADD ROUND KEY "+round);
        Hex[][] roundKey = toMatrix(keys[0]);
        state = addRoundKey(state, roundKey);
        printMatrix(state);
        command(scan.nextLine());

        for (int i=1; i<keys.length; i++){
            System.out.println("\n<===================> ROUND "+(round+1)+" <===================>\n");

            // Subtitotion Bytes
            for (int j=0; j<state.length; j++){
                for (int k=0; k<state[j].length; k++) {
                    state[j][k] = sBox(state[j][k]);
                }
            }
            System.out.println("\nSUBTITUTION BYTES: ");
            printMatrix(state);
            command(scan.nextLine());

            // Shift Row
            state = shiftRows(state);
            System.out.println("\nSHIFT ROWS: ");
            printMatrix(state);
            command(scan.nextLine());

            // Mix Column
            if (i<keys.length-1){
                String[][] a = {{"02","03","01","01"},{"01","02","03","01"},{"01","01","02","03"},{"03","01","01","02"}};
                Hex[][] hA = new Hex[a.length][a[0].length];
                for (int j=0; j<a.length; j++){
                    for (int k=0; k<a[0].length; k++){
                        hA[j][k] = new Hex(a[j][k]);
                    }
                }
                state = mixCol(hA, state);
                System.out.println("\nMIX COLUMN: ");
                printMatrix(state);
                command(scan.nextLine());
            }

            // New RoundKey
            keys[i] = expandKey(keys[i-1]);
            System.out.print("\nEXPAND KEY-"+round+": ");
            printArray(keys[i]);

            // Add RoundKey
            roundKey = toMatrix(keys[i]);
            state = addRoundKey(state, roundKey);
            System.out.println("\nADD ROUNDKEY: ");
            printMatrix(state);
            command(scan.nextLine());
        }

        // Print Chiper Text
        System.out.print("\nCHIPER TEXT: ");
        for (int i=0; i<state.length; i++){
            for (int j=0; j<state[i].length; j++) {
                if (state[i][j].length()==1){
                    state[i][j].setValue("0" + state[i][j].get());
                }
                System.out.printf("%3s", state[j][i].get());
            }
        }

        System.out.print("\n\n<======================> END <======================>\n");
    }

    public static void printMatrix(Hex[][] m){
        for (int i=0; i<m.length; i++){
            for (int j=0; j<m[i].length; j++){
                System.out.printf("%5s", m[i][j].get());
            }
            System.out.println();
        }
    }

    public static void printArray(Hex[] arr){
        for (Hex x: arr){
            System.out.printf("%3s", x.get());
        }
        System.out.println();
    }

    private static Hex[][] shiftRows(Hex[][] state){
        Hex[][] temp = new Hex[state.length][state[0].length];
        temp[1][0] = state[1][1];
        temp[2][0] = state[2][2];
        temp[3][0] = state[3][3];
        temp[1][1] = state[1][2];
        temp[2][1] = state[2][3];
        temp[3][1] = state[3][0];
        temp[1][2] = state[1][3];
        temp[2][2] = state[2][0];
        temp[3][2] = state[3][1];
        temp[1][3] = state[1][0];
        temp[2][3] = state[2][1];
        temp[3][3] = state[3][2];
        for (int i=0; i<state[0].length; i++) {
            temp[0][i] = state[0][i];            
        }
        return temp;
    }

    private static Hex[][] toMatrix(Hex[] arr){
        Hex[][] matrix = new Hex[arr.length/4][arr.length/4];
        for (int i=0; i<arr.length; i++){
            matrix[i%4][i/4] = arr[i];
        }
        return matrix;
    }

    private static Hex[] addRoundKey(Hex[] state, Hex[] key){
        Hex[] result = new Hex[state.length];
        for (int i=0; i<state.length; i++){
            result[i] = state[i].xor(key[i]);
            System.out.printf("%5s", result[i].get());
        }
        System.out.println();
        return result;
    }

    private static Hex[][] addRoundKey(Hex[][] state, Hex[][] key){
        Hex[][] result = new Hex[state.length][state[0].length];
        for (int i=0; i<result.length; i++){
            for (int j=0; j<result[i].length; j++) {
                result[i][j] = state[i][j].xor(key[i][j]);
            }
        }
        return result;
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

    
    public static Hex[] expandKey(Hex[] fKey){
        Hex[] key = new Hex[16];
        
        Hex[][] words = new Hex[8][4];

        for (int i=0; i<fKey.length; i++){
            words[i/4][i%4] = fKey[i];
        }

        Hex[] w3 = new Hex[words[3].length];
        
        for (int i=0; i<w3.length; i++){
            w3[i] = words[3][i];
        }

        Hex temp = w3[0];
        for (int i=0; i<w3.length-1; i++){
            w3[i] = w3[i+1];
        }
        w3[3] = temp;

        for (int i=0; i<w3.length; i++){
            w3[i] = sBox(w3[i]);
        }

        Hex rcon = new Hex(RCON[round]);
        w3[0] = w3[0].xor(rcon);
        
        for (int i=0; i<words[4].length; i++){
            words[4][i] = words[0][i].xor(w3[i]);
        }
        
        for (int i=5; i<words.length; i++){
            for (int j=0; j<words[i].length; j++){
                words[i][j] = words[i-1][j].xor(words[i-4][j]);
            }
        }

        for (int i=0; i<fKey.length; i++){
            key[i] = words[(i/4)+4][i%4];
        }

        round++;
        return key;
    }

    private static String command(String cmd){
        switch (cmd){
            case "sbox":
                for (int i=0; i<SBOX.length; i++) {
                    for (int j=0; j<SBOX[i].length; j++) {
                        System.out.printf("%4s", SBOX[i][j]);
                    }
                    System.out.println();
                }
                return command(scan.nextLine());
            case "round":
                System.out.println(round);
                return command(scan.nextLine());
            case "state":
                printMatrix(state);
                return command(scan.nextLine());
            case "":
                return "null";
            default:
                return command(scan.nextLine());
        }
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