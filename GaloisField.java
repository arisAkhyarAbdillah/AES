public class GaloisField {
    public static int product(int a, int b){
        char[] bA = decToBinary(a).toCharArray();
        char[] bB = decToBinary(b).toCharArray();
        char[] res = new String("000000000000000").toCharArray();

        for (int i=0; i<bA.length; i++){
            for (int j=0; j<bB.length; j++){
                res[i+j] ^= '1';
            }
        }

        String result = new String(res);
        System.out.println(result);
        return binaryToDec(result);
    }

    public static String decToBinary(int num){
        return Integer.toBinaryString(num);
    }

    public static int binaryToDec(String num){
        return Integer.parseInt(num, 2);
    }
} 