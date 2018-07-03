package testes;

import memoria.fisica.MemoriaRam;
import memoria.fisica.cache.MemoriaCache;
import memoria.mmu.MMU;
import processador.Processador;
import sistemaoperacional.OS;

public class TestaOS {
    public static void main(String[] args){
        int totalBits = MMU.PAGEOFFSETBITS + MMU.ENTRYOFFSETBITS;
        MemoriaRam ram = new MemoriaRam(1000, 0);
        MemoriaCache cache = new MemoriaCache(totalBits, 8 , 3, ram, 00);

        System.out.println(cache.getOffset(104));
        Processador processador = new Processador(ram, cache);
        OS os = new OS();
        os.setProcessador(processador);

        GerenciadorDeProcessos gerenciadorDeProcessos = new GerenciadorDeProcessos(os);
        gerenciadorDeProcessos.explorar();
    }
}
