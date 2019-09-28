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

    public void println(){
        System.out.println(value);
    }
}