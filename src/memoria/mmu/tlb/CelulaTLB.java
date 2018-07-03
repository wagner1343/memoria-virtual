package memoria.mmu.tlb;

import memoria.virtual.paginacao.EntradaDePagina;

public class CelulaTLB {
    private int idade;
    private int pid;
    private int numeroDePagina;
    private EntradaDePagina entradaDePagina;

    public CelulaTLB(int pid,int numeroDePagina, EntradaDePagina entradaDePagina){
        this.idade = 0;
        this.pid = pid;
        this.numeroDePagina = numeroDePagina;
        this.entradaDePagina = entradaDePagina;
    }

    public int getPid() {
        return pid;
    }

    public EntradaDePagina getEntradaDePagina() {
        return entradaDePagina;
    }

    public int getNumeroDePagina() {
        return numeroDePagina;
    }

    public int getIdade() {
        return idade;
    }

    public void addIdade(){
        this.idade++;
    }

    public void remIdade(){
        this.idade--;
    }

    public String toString(){
        return "pid:" + pid + " numeroDePagina:" + getNumeroDePagina()+  " baseFAddr:" + entradaDePagina.getBaseFAddr() + " idade:" + idade;
    }
}
