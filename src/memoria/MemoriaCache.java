package memoria;

public class MemoriaCache {
    int linhas, colunas;
    CacheLine[] mem;

    public MemoriaCache(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;

        mem = new CacheLine[linhas];
    }

    public Byte read(int addr){
        Byte b = 0b1111;
        return b;
    }
}
