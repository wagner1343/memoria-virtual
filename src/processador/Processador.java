package processador;

import memoria.fisica.MemoriaFisicaInterface;
import memoria.mmu.FaltaDePaginaException;
import memoria.mmu.MMU;

public class Processador {
    private MMU mmu;
    private MemoriaFisicaInterface ram;
    private MemoriaFisicaInterface cache;
    private int tabelaFAddr;
    private int pid;

    public Processador(MemoriaFisicaInterface ram, MemoriaFisicaInterface cache){
        this.ram = ram;
        this.cache = cache;
        this.mmu = new MMU(ram);
    }

    public int traduzir(int vAddr) throws FaltaDePaginaException {
        return mmu.traduzir(tabelaFAddr, pid, vAddr);
    }
    public byte[] read(int vAddr, int tam) throws FaltaDePaginaException {
        byte[] bytes = new byte[tam];

        for(int x = 0; x < tam; x++){
            bytes[x] = read(vAddr + x);
        }
        return bytes;
    }

    public byte read(int vAddr) throws FaltaDePaginaException {
        int fAddr = mmu.traduzir(tabelaFAddr, pid, vAddr);

        return cache.read(fAddr);
    }

    public void write(int vAddr, byte[] bytes) throws FaltaDePaginaException {
        for(int x = 0; x < bytes.length; x++){
            write(vAddr + x, bytes[x]);
        }
    }

    public void write(int vAddr, byte b) throws FaltaDePaginaException {
        int fAddr = traduzir(vAddr);
        cache.write(fAddr, b);
    }

    public int getTabelaFAddr() {
        return tabelaFAddr;
    }

    public void setTabelaFAddr(int tabelaFAddr) {
        this.tabelaFAddr = tabelaFAddr;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public MMU getMmu() {
        return mmu;
    }

    public void setMmu(MMU mmu) {
        this.mmu = mmu;
    }

    public MemoriaFisicaInterface getRam() {
        return ram;
    }

    public void setRam(MemoriaFisicaInterface ram) {
        this.ram = ram;
    }

    public MemoriaFisicaInterface getCache() {
        return cache;
    }

    public void setCache(MemoriaFisicaInterface cache) {
        this.cache = cache;
    }
}
