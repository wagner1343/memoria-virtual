package memoria.virtual.paginacao;

import util.serialization.SerializationHelper;
import util.serialization.Serializavel;

import java.util.InputMismatchException;

public class EntradaDePagina implements Serializavel {
    public static final int TAMANHO_BYTES = 5;
    private byte[] bytes;

    public EntradaDePagina(int baseFAddr, boolean disco){
        byte[] baseFAddrBytes = SerializationHelper.toByteArray(baseFAddr);
        byte discoByte = disco ? (byte) 1 : 0;

        bytes = new byte[TAMANHO_BYTES];
        for(int x = 0; x < 4; x++){
            bytes[x] = baseFAddrBytes[x];
        }
        bytes[4] = discoByte;
    }

    public EntradaDePagina(byte[] bytes){
        if(bytes.length !=  TAMANHO_BYTES)
            throw new InputMismatchException();
        this.bytes = bytes;
    }

    public int getBaseFAddr() {
        byte[] baseFAddrBytes = new byte[4];
        for(int x = 0; x < 4; x++){
            baseFAddrBytes[x] = bytes[x];
        }
        int baseFAddr = SerializationHelper.fromByteArray(baseFAddrBytes);

        return baseFAddr;
    }

    public boolean getDisco(){
        return bytes[4] == 0 ? false : true;
    }

    public String toString(){
        return getBaseFAddr() + "\t" + getDisco();
    }

    @Override
    public byte[] serializar() {
        return bytes;
    }
}
