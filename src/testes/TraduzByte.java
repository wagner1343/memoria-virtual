package testes;

public class TraduzByte {
    public static void main(String[] args){
        byte[] bytes = {119 ,97 ,103 ,110 ,101 ,114 ,32 ,115};
        char[] chars = new char[bytes.length];

        for(int x = 0; x < bytes.length; x++){
            chars[x] = (char) bytes[x];
        }

        System.out.println(String.valueOf(chars));
    }
}
