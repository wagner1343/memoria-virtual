package memoria.virtual.paginacao;

import memoria.mmu.MMU;
import util.serialization.Serializavel;

import java.util.Arrays;

public class TabelaDePaginas implements Serializavel{
    public static final int TAMANHO_BYTES = MMU.NUM_PAGINAS * EntradaDePagina.TAMANHO_BYTES;
    byte[] bytes;

    public TabelaDePaginas(){
        bytes = new byte[MMU.NUM_PAGINAS * EntradaDePagina.TAMANHO_BYTES];
        Arrays.fill(bytes, (byte) -1);
    }

    public TabelaDePaginas(byte[] bytes){
        this.bytes = bytes;
    }

    public EntradaDePagina getPagina(int numeroDePagina){
        byte[] entradaDePaginaBytes = new byte[EntradaDePagina.TAMANHO_BYTES];

        int baseAddr = numeroDePagina * EntradaDePagina.TAMANHO_BYTES;
        for(int x = 0; x < EntradaDePagina.TAMANHO_BYTES; x++)
            entradaDePaginaBytes[x] = bytes[baseAddr + x];

        return new EntradaDePagina(entradaDePaginaBytes);
    }

    public void setPagina(int numeroDePagina, EntradaDePagina entradaDePagina){
        int baseAddr = numeroDePagina * EntradaDePagina.TAMANHO_BYTES;

        byte[] entradaDePaginaBytes = entradaDePagina.serializar();

        for(int x = 0; x < EntradaDePagina.TAMANHO_BYTES; x++){
            bytes[baseAddr + x] = entradaDePaginaBytes[x];
        }
    }

    public boolean isAlocado(int numPagina){
        return getPagina(numPagina).getBaseFAddr() < 0;
    }

    public void liberarPagina(int numPagina){
        setPagina(numPagina, new EntradaDePagina(-1, false));
    }

    public int getNumPaginas(){
        return bytes.length / EntradaDePagina.TAMANHO_BYTES;
    }

    public String toString(){
        String s = "vAddr\t\tfAddr\t\tdisco\t\tcount\n";

        int numPaginas = getNumPaginas();
        for(int x = 0; x < numPaginas; x++) {
            EntradaDePagina e = getPagina(x);
            s += (e == null) ? "" : x * MMU.TAM_PAGINA + "\t\t\t" + e.toString() + "\n";
        }
        return s;
    }

    @Override
    public byte[] serializar() {
        return bytes;
    }
}
