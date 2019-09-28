public class GaloisField {
    public static int dotProduct(int a, int b){
        char[] bA = decToBinary(a).toCharArray();
        char[] bB = decToBinary(b).toCharArray();
        char[] res = new String("000000000000000").toCharArray();

        for (int i=0; i<bA.length; i++){
            if (bA[bA.length-1-i]=='1'){
                for (int j=0; j<bB.length; j++){
                    if (bB[bB.length-1-j]=='1'){
                        res[14-(i+j)] = Character.forDigit(res[14-(i+j)]^'1', 2);
                    }
                }
            }
        }

        String result = new String(res);
        
        return binaryToDec(result);
    }
    
    public static String reductionMod(String str){
        String reductor = "";
        while (str.length() >= (reductor = "100011011").length()){
            while (str.length() > reductor.length()){
                reductor += "0";
            }
            int iRes = Integer.parseInt(str, 2);
            int iDiv = Integer.parseInt(reductor, 2);
            iRes ^= iDiv;
            str = Integer.toBinaryString(iRes);
            // System.out.println("revRes="+result);
        }

        // System.out.println("result="+result);
        return str;
    }
    
    public static String decToBinary(int num){
        return Integer.toBinaryString(num);
    }

    public static int binaryToDec(String num){
        return Integer.parseInt(num, 2);
    }
} 