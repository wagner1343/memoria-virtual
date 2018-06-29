package memoria.fisica.cache;

import memoria.fisica.MemoriaFisicaInterface;

public class CacheLine implements MemoriaFisicaInterface {
    int tag;
    Byte[] mem;

    public CacheLine(int tam, int tag){
        this.mem = new Byte[tam];
        this.tag = tag;
    }

    @Override
    public int length() {
        return mem.length;
    }

    @Override
    public void write(int offset, Byte[] bytes) {
        for(int x = 0; x < bytes.length; x++){
            mem[offset + x] = bytes[x];
        }
    }

    @Override
    public void write(int offset, Byte b) {
        mem[offset] = b;
    }

    @Override
    public Byte[] read(int offset, int tam) {
        Byte[] bytes = new Byte[tam];

        for(int x = 0; x < tam; x++){
            bytes[x] = mem[offset + x];
        }
        return bytes;
    }

    @Override
    public Byte read(int offset) {
        return mem[offset];
    }
}
