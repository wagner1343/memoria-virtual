package mv.tabeladepaginas;

import mv.tabeladepaginas.entrada.Entrada;

public class TabelaSistema {
    private EntradaSistema[] entradas;

    public TabelaSistema(int tam){
        entradas = new EntradaSistema[tam];
    }

    public EntradaSistema getEntrada(int addr){
        return entradas[addr];
    }

    public void setEntrada(int addr, EntradaSistema entrada){
        entradas[addr] = entrada;
    }

    public EntradaSistema[] getEntradas() {
        return entradas;
    }

    public String toString(){
        String s = "";
        for(EntradaSistema e : entradas){
            s += (e == null) ? "" : e.toString() + '\n';
        }
        return s;
    }
}
