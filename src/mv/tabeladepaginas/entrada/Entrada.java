package mv.tabeladepaginas.entrada;

import mv.tabeladepaginas.EntradaSistema;

public class Entrada {
    private EntradaSistema entradaSistema;

    public Entrada(EntradaSistema entradaSistema){
        this.entradaSistema = entradaSistema;
    }

    public EntradaSistema getEntradaSistema() {
        return entradaSistema;
    }

    public void setEntradaSistema(EntradaSistema entradaSistema) {
        this.entradaSistema = entradaSistema;
    }

    public String toString(){
        return "entradaSistema: " + entradaSistema.toString();
    }
}
