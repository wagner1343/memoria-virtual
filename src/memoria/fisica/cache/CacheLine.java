package memoria.fisica.cache;

import memoria.fisica.MemoriaFisicaInterface;

public class CacheLine implements MemoriaFisicaInterface {
    private int tag;
    byte[] mem;

    public CacheLine(int tam, int tag){
        this.mem = new byte[tam];
        this.tag = tag;
    }

    @Override
    public int length() {
        return mem.length;
    }

    @Override
    public void write(int offset, byte[] bytes) {
        for(int x = 0; x < bytes.length; x++){
            mem[offset + x] = bytes[x];
        }
    }

    @Override
    public void write(int offset, byte b) {
        mem[offset] = b;
    }

    @Override
    public byte[] read(int offset, int tam) {
        byte[] bytes = new byte[tam];

        for(int x = 0; x < tam; x++){
            bytes[x] = mem[offset + x];
        }
        return bytes;
    }

    @Override
    public byte read(int offset) {
        return mem[offset];
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String toString(){
        String s = "tag: " + tag;
        for(int x = 0; x < mem.length; x++){
            s += " " + (int) mem[x];
        }
        s += "\n";

        return  s;
    }
}
