package memoria.fisica;

public interface MemoriaFisicaInterface {
    int length();
    void write(int addr, Byte[] bytes);
    void write(int addr, Byte b);
    Byte[] read(int addr, int tam);
    Byte read(int addr);
}
