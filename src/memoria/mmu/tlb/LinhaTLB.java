package memoria.mmu.tlb;

import memoria.virtual.paginacao.EntradaDePagina;

import java.util.ArrayList;

public class LinhaTLB {
    private ArrayList<CelulaTLB> celulas;

    public LinhaTLB(int tam){
        this.celulas = new ArrayList<>(tam);
    }

    public EntradaDePagina buscarEntrada(int pid, int numeroDePagina){
        for(CelulaTLB celula : celulas){
            celula.addIdade();
            if(celula.getPid() == pid && celula.getNumeroDePagina() == numeroDePagina){
                celula.remIdade();
                return celula.getEntradaDePagina();
            }
        }

        return null;
    }

    public ArrayList<CelulaTLB> getCelulas() {
        return celulas;
    }

    public String toString(){
        int size = celulas.size();
        String s = "";
        for(int x = 0; x < size; x++){
            s += "Celula " + x + ": " + celulas.get(x).toString() + "\n";
        }
        return s;
    }

    public void removeEntrada(int pid, int numeroDePagina) {
        for(int x = 0; x < celulas.size(); x++){
            CelulaTLB celula = celulas.get(x);

            if(celula.getPid() == pid && celula.getNumeroDePagina() == numeroDePagina){
                celulas.remove(x);
            }
        }
    }
}
