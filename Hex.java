public class Hex {
    public static final int BASIS = 16;
    private String value;

    public Hex(String value){
        this.value = value;
    }

    public String get(){
        return value;
    }

    public int length(){
        return value.length();
    }

    public static Hex valueOf(int number) {
        return new Hex(Integer.toHexString(number).toUpperCase());
    }

    public int toDec() {
        return Integer.parseInt(get(), BASIS);
    }

    public char charAt(int i){
        return value.charAt(i);
    }

    public static Hex[] hexArrayOf(String str){
        char[] chr = str.toCharArray();
        Hex[] hex = new Hex[chr.length];
        for (int i=0; i<chr.length; i++){
            hex[i] = new Hex(Hex.valueOf((int) chr[i]).get());
        }
        return hex;
    }

    public Hex xor(Hex x){
        int dV = Integer.parseInt(value, BASIS);
        int dX = Integer.parseInt(x.get(), BASIS);
        int xor = dV^dX;
        return valueOf(xor);
    }

    public void println(){
        System.out.println(value);
    }
}