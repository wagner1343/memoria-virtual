package memoria.fisica;

public interface MemoriaFisicaInterface {
    int length();
    void write(int addr, byte[] bytes);
    void write(int addr, byte b);
    byte[] read(int addr, int tam);
    byte read(int addr);
}
