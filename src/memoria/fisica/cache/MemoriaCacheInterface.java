package memoria.fisica.cache;

import memoria.fisica.MemoriaFisicaInterface;

public interface MemoriaCacheInterface extends MemoriaFisicaInterface {
    CacheLine readLine(int numLinha);
    void writeLine(int numLinha, CacheLine line);

    MemoriaFisicaInterface getPai();
}
