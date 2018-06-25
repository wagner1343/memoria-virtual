package mv.tabeladepaginas;

import mv.tabeladepaginas.entrada.Entrada;

public class Tabela {
    private int pid;
    private Entrada[] entradas;

    public Tabela(int tam, int pid){
        this.pid = pid;
        entradas = new Entrada[tam];
    }

    public Entrada getEntrada(int addr){
        return entradas[addr];
    }

    public void setEntrada(int addr, Entrada entrada){
        entradas[addr] = entrada;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String toString(){
        String s = "";

        for(Entrada e : entradas){
            s += (e == null) ? "" : e.toString() + ", " ;
        }

        return pid + " " +  s;
    }
}
