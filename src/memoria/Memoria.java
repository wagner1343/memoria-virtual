package memoria;

public class Memoria {
    Byte[] mem;

    public Memoria(int tam){
        this.mem = new Byte[tam];
    }

    public Byte read(int addr){
        return mem[addr];
    }

    public void write(int addr, Byte b){
        mem[addr] = b;
    }
}
