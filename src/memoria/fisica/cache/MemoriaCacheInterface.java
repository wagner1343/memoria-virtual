package memoria.fisica.cache;

import memoria.fisica.MemoriaFisicaInterface;

public interface MemoriaCacheInterface extends MemoriaFisicaInterface {
    CacheLine readLine();
    void writeLine(CacheLine line);

    MemoriaFisicaInterface getPai();
}
