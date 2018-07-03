package sistemaoperacional;

import memoria.MemoriaIndisponivelException;
import memoria.mmu.FaltaDePaginaException;
import memoria.mmu.MMU;
import memoria.virtual.paginacao.TabelaDePaginas;
import processador.Processador;

import java.util.ArrayList;

public class OS{
    private static int GLOBAL_PID = 1;
    private ArrayList<Processo> processos;
    private Processador processador;

    public OS(){
        processos = new ArrayList<>();
    }

    public void criarProcesso(String nome){
        System.out.print("OS.criarProcesso");
        System.out.println(" nome = [" + nome + "]");
        MMU mmu = processador.getMmu();

        try {
            Processo processo = new Processo(nome, gerarPid(),
                    mmu.buscarFramesLivres(mmu.calcularFramesNecessarios(TabelaDePaginas.TAMANHO_BYTES+4)));

            mmu.salvarTabelaNaMemoria(processo.getTabelaFAddr(), new TabelaDePaginas());
            processos.add(processo);
        } catch (MemoriaIndisponivelException e) {
            e.printStackTrace();
        }
    }

    public Processo buscarProcesso(int pid) throws ProcessoInexistenteException {
        for(Processo processo:processos){
            if(processo.getPid() == pid)
                return processo;
        }

        throw new ProcessoInexistenteException();
    }

    public int gerarPid(){
        return GLOBAL_PID++;
    }

    public void finalizarProcesso(int pid){
        Processo processo;
        MMU mmu = processador.getMmu();

        try {
            processo = buscarProcesso(pid);
            TabelaDePaginas tabelaDePaginas = mmu.buscarTabelaNaMemoria(processo.getTabelaFAddr());

            int frameIndex;
            int frameCount;
            for(int x = 0; x  < MMU.NUM_PAGINAS; x++){
                if(tabelaDePaginas.isAlocado(x)){
                    frameIndex = tabelaDePaginas.getPagina(x).getBaseFAddr() / MMU.TAM_FRAME;
                    frameCount = mmu.calcularFramesNecessarios(MMU.TAM_PAGINA);

                    mmu.liberarFrames(frameIndex, frameCount);
                }
            }

            frameIndex = processo.getTabelaFAddr() / MMU.TAM_FRAME;
            frameCount = mmu.calcularFramesNecessarios(TabelaDePaginas.TAMANHO_BYTES + 4);

            mmu.liberarFrames(frameIndex, frameCount);

            processos.remove(processo);
        } catch (ProcessoInexistenteException e) {
            e.printStackTrace();
        }
    }

    public Processador getProcessador() {
        return processador;
    }

    public void setProcessador(Processador processador) {
        this.processador = processador;
    }

    public ArrayList<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(ArrayList<Processo> processos) {
        this.processos = processos;
    }
}
