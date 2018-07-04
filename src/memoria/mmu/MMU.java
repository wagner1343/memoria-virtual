package memoria.mmu;

import memoria.MemoriaIndisponivelException;
import memoria.fisica.MemoriaFisicaInterface;
import memoria.mmu.tlb.TLB;
import memoria.mmu.tlb.TLBMissException;
import memoria.virtual.paginacao.EntradaDePagina;
import memoria.virtual.paginacao.TabelaDePaginas;
import util.serialization.SerializationHelper;

public class MMU {

    public static final int PAGEOFFSETBITS = 4;
    public static final int ENTRYOFFSETBITS = 2;
    public static final int TAM_PAGINA = (int) Math.pow(2, PAGEOFFSETBITS);
    public static final int NUM_PAGINAS = (int) Math.pow(2, ENTRYOFFSETBITS);
    public static final int TAM_FRAME = TAM_PAGINA ;
    private byte[] frameOcupado;
    private TLB tlb;

    private MemoriaFisicaInterface memoriaFisica;

    public MMU(MemoriaFisicaInterface memoriaFisica){
        this.memoriaFisica = memoriaFisica;
        tlb = new TLB();
        frameOcupado = new byte[memoriaFisica.length() / TAM_FRAME];
    }

    public int calcularFramesNecessarios(int tam){
        int framesPorPagina = 0;
        while(framesPorPagina * TAM_FRAME < tam)
            framesPorPagina++;

        return framesPorPagina;
    }

    public void alocarPagina(int tabelaFAddr, int numeroDePagina) throws MemoriaIndisponivelException {
        System.out.print("MMU.alocarPagina");
        System.out.println(" tabelaFAddr = [" + tabelaFAddr + "], numeroDePagina = [" + numeroDePagina + "]");
        int framesPorPagina = calcularFramesNecessarios(TAM_PAGINA);
        int fAddr = buscarFramesLivres(framesPorPagina);

        EntradaDePagina entradaDePagina = new EntradaDePagina(fAddr, false);
        salvarEntradaNaMemoria(tabelaFAddr, numeroDePagina, entradaDePagina);
    }

    public void liberarFrames(int frameIndex, int count){

        System.out.println("Marcando frames de " + frameIndex + " até " + (frameIndex + count -1 ) + " como livres");
        for(int x = 0; x < count; x++)
            frameOcupado[frameIndex + x] = 0;
    }

    public void ocuparFrames(int frameIndex, int count){
        System.out.println("Marcando frames de " + frameIndex + " até " + (int) (frameIndex + count -1) + " como ocupados");
        for(int x = 0; x < count; x++)
            frameOcupado[frameIndex + x] = 1;
    }

    public int buscarFramesLivres(int numFrames) throws MemoriaIndisponivelException {
        System.out.print("MMU.buscarFramesLivres");
        System.out.println(" numFrames = [" + numFrames + "]");

        int count = 0;
        for(int x = 0; x < frameOcupado.length; x++){
            if(count == numFrames){
                ocuparFrames(x - numFrames, numFrames);
                return  (x - numFrames) * TAM_FRAME;
            }
            if(frameOcupado[x] == 0){
                System.out.println("Frame " + x + " livre");
                count++;
            }
            else {
                System.out.println("Frame " + x + " ocupado");
                count = 0;
            }
        }

        throw new MemoriaIndisponivelException();
    }
    public int traduzir(int tabelaFAddr, int pid, int vAddr) throws FaltaDePaginaException {
        System.out.print("Traduzindo endereço " + vAddr + " ");
        String vAddrStringBinary = parseAddr(vAddr);
        int numeroDePagina = getNumPagina(vAddrStringBinary);
        EntradaDePagina entrada;

        try {
            entrada = tlb.buscarEntrada(pid, numeroDePagina);
        } catch (TLBMissException e) {
            System.out.println("TLB miss");
            onTLBMissException(tabelaFAddr, pid, numeroDePagina);
            return traduzir(tabelaFAddr, pid, vAddr);
        }

        if(entrada.getBaseFAddr() < 0)
            try {
                throw new FaltaDeMapeamentoException();
            } catch (FaltaDeMapeamentoException e) {
                System.out.println("Falta de mapeamento");
                try {
                    alocarPagina(tabelaFAddr,numeroDePagina);
                    tlb.removeEntrada(pid, numeroDePagina);
                    return traduzir(tabelaFAddr, pid, vAddr);
                } catch (MemoriaIndisponivelException e1) {
                    System.out.println("Memoria indisponivel");
                }
            }


        if(entrada.getDisco()) {
            throw new FaltaDePaginaException();
        }
        System.out.println(" traduzido para " + (int) (entrada.getBaseFAddr() + getOffset(vAddrStringBinary)));
        return entrada.getBaseFAddr() + getOffset(vAddrStringBinary);
    }



    public void carregarEntradaNaTLB(int pid, int numeroDePagina, EntradaDePagina entradaDePagina){
        tlb.gravarEntrada(pid, numeroDePagina, entradaDePagina);
    }

    public int getOffset(String vAddrStringBinary){
        String offsetStringBinary = vAddrStringBinary.substring(vAddrStringBinary.length() - PAGEOFFSETBITS);

        return Integer.parseInt(offsetStringBinary, 2);
    }

    public int getNumPagina(String vAddrStringBinary){
        String paginaStringBinary = vAddrStringBinary.substring(vAddrStringBinary.length() - (PAGEOFFSETBITS + ENTRYOFFSETBITS),
                vAddrStringBinary.length() - PAGEOFFSETBITS);

        int numPagina = Integer.parseInt(paginaStringBinary, 2);
        return numPagina;
    }

    public String parseAddr(int vAddr){
        String vAddrString = Integer.toBinaryString(vAddr);

        while(vAddrString.length() < PAGEOFFSETBITS + ENTRYOFFSETBITS)
            vAddrString = "0" + vAddrString;

        return vAddrString;
    }

    public void onTLBMissException(int tabelaFAddr, int pid, int numeroDePagina){
        carregarEntradaNaTLB(pid, numeroDePagina, buscarEntradaNaMemoria(tabelaFAddr, numeroDePagina));
    }

    public EntradaDePagina buscarEntradaNaMemoria(int tabelaFAddr, int numeroDePagina){
        TabelaDePaginas tabela = buscarTabelaNaMemoria(tabelaFAddr);
        EntradaDePagina entradaDePagina = tabela.getPagina(numeroDePagina);

        return entradaDePagina;
    }

    public TabelaDePaginas buscarTabelaNaMemoria(int tabelaFAddr){

        byte[] tabelaBytes = memoriaFisica.read(tabelaFAddr, TabelaDePaginas.TAMANHO_BYTES);
        TabelaDePaginas tabela = new TabelaDePaginas(tabelaBytes);

        return  tabela;
    }

    public void salvarEntradaNaMemoria(int tabelaFAddr, int numeroDePagina, EntradaDePagina entradaDePagina){
        int entradaFAddr = tabelaFAddr + (numeroDePagina * EntradaDePagina.TAMANHO_BYTES);
        memoriaFisica.write(entradaFAddr, entradaDePagina.serializar());
    }

    public void salvarTabelaNaMemoria(int tabelaFAddr, TabelaDePaginas tabelaDePaginas){
        byte[] tabelaBytes = tabelaDePaginas.serializar();

        memoriaFisica.write(tabelaFAddr, tabelaBytes);
    }

    public TLB getTlb() {
        return tlb;
    }

    public void setTlb(TLB tlb) {
        this.tlb = tlb;
    }
}
