package memoria.mmu.tlb;

import memoria.MemoriaIndisponivelException;
import memoria.virtual.paginacao.EntradaDePagina;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TLB {
    private static final Logger LOGGER = Logger.getLogger(TLB.class.getName());
    public static final int LINHAS = 1;
    public static final int TAM_LINHAS = 3;
    LinhaTLB[] linhas;

    public TLB(){
        linhas = new LinhaTLB[LINHAS];
        for(int x = 0; x < linhas.length; x++){
            linhas[x] = new LinhaTLB(TAM_LINHAS);
        }
    }

    public EntradaDePagina buscarEntrada(int pid, int numeroDePagina) throws TLBMissException {
        for(LinhaTLB linha:linhas){
            EntradaDePagina entrada;
            entrada = linha.buscarEntrada(pid, numeroDePagina);

            if(entrada != null)
                return entrada;
        }

        throw new TLBMissException();
    }

    public void removeEntrada(int pid, int numeroDePagina){
        for(LinhaTLB linha:linhas){
            linha.removeEntrada(pid, numeroDePagina);
        }
    }

    public void gravarEntrada(int pid, int numeroDePagina, EntradaDePagina entradaDePagina){
        for(int x = 0; x < linhas.length; x++)
            if(linhas[x].getCelulas().size() < TAM_LINHAS) {
                linhas[x].getCelulas().add(new CelulaTLB(pid, numeroDePagina, entradaDePagina));
                return;
            }
        try {
            throw new MemoriaIndisponivelException();
        } catch (MemoriaIndisponivelException e) {
            removerEntradaMenosUtilizada();
            gravarEntrada(pid, numeroDePagina, entradaDePagina);
        }
    }

    public void removerEntradaMenosUtilizada(){
        System.out.println("TLB.removerEntradaMenosUtilizada");
        int velhoX = -1;
        int velhoY = -1;
        for(int x = 0; x < linhas.length; x++){
            for(int y = 0; y < linhas[x].getCelulas().size(); y++){
                if(velhoX == -1 && velhoY == -1
                        || linhas[x].getCelulas().get(y).getIdade() > linhas[velhoX].getCelulas().get(y).getIdade()){
                    velhoX = x;
                    velhoY = y;
                }
            }
        }
        System.out.println("Linha " + velhoX + " celula " + velhoY);
        linhas[velhoX].getCelulas().remove(velhoY);
    }

    public String toString(){
        String s = "";

        for(int x = 0; x < linhas.length; x++)
            s += "Linha " + x + "\n" + linhas[x].toString() + "\n";

        return s;
    }

}
