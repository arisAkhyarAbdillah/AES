public class Main {
    public static void main(String[] args) {
        // String plainText = "Saya Kangen Kuis";
        // String key = "kriptografi";
        String plainText = "Thats my Kung Fu";
        String key = "Two One Nine Two";

        for (int i = 0; i < plainText.length(); i++) {
            System.out.print(plainText.charAt(i) + "\t");
        }
        System.out.println();
        for (int i = 0; i < plainText.length(); i++) {
            System.out.print(decToHex((int) plainText.charAt(i)) + "\t");
        }
        System.out.println("\n");
        for (int i = 0; i < key.length(); i++) {
            System.out.print(key.charAt(i) + "\t");
        }
        System.out.println();
        for (int i = 0; i < key.length(); i++) {
            System.out.print(decToHex((int) key.charAt(i)) + "\t");
        }


    }

    public static String decToHex(int number) {
        return (Integer.toHexString(number).toUpperCase());
    }

    public static int hexToDec(String number) {
        return (Integer.parseInt(number, 16));
    }

    public static String sBox(String hex) {
        int x = 0;
        int y = 0;
        if (hex.length() < 2) {
            y = hexToDec(hex);
        } else {
            String a = "";
            String b = "";
            a += hex.charAt(0);
            b += hex.charAt(1);
            x = hexToDec(a);
            y = hexToDec(b);
        }
        String sbox[][] = {
            {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"},
            {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "30", "01", "A2", "AF", "9C", "A4", "72", "C0"},
            {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "30", "01", "E5", "F1", "71", "D8", "31", "15"},
            {"04", "C7", "23", "C3", "18", "96", "05", "9A", "30", "01", "80", "E2", "EB", "27", "B2", "75"},
            {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "30", "01", "D6", "B3", "29", "E3", "2F", "84"},
            {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "30", "01", "BE", "39", "4A", "4C", "58", "CF"},
            {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "30", "01", "02", "7F", "50", "3C", "9F", "A8"},
            {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "30", "01", "DA", "21", "10", "FF", "F3", "D2"},
            {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "30", "01", "7E", "3D", "64", "5D", "19", "73"},
            {"60", "81", "4F", "DC", "22", "2A", "90", "88", "30", "01", "B8", "14", "DE", "5E", "0B", "DB"},
            {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "30", "01", "AC", "62", "91", "95", "E4", "79"},
            {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "30", "01", "F4", "EA", "65", "7A", "AE", "08"},
            {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "30", "01", "74", "1F", "4B", "BD", "8B", "8A"},
            {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "30", "01", "57", "B9", "86", "C1", "1D", "9E"},
            {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "30", "01", "87", "E9", "CE", "55", "28", "DF"},
            {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "30", "01", "2D", "0F", "B0", "54", "BB", "16"},
        };

        return sbox[x][y];
    }
}