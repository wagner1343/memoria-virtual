package memoria.virtual.paginacao;

public class TabelaDePaginas {

    private Pagina[] paginas;
    private int tamPagina;
    int numPaginas;


    public TabelaDePaginas(int numPaginas, int tamPagina){
        paginas = new Pagina[numPaginas];
        this.numPaginas = numPaginas;
        this.tamPagina = tamPagina;
    }

    public int getNumPaginas(){
        return this.numPaginas;
    }

    public Pagina getPagina(int addr){
        return paginas[addr];
    }

    public void setPagina(int addr, Pagina pagina){
        paginas[addr] = pagina;
    }

    public boolean isAlocado(int numPagina){
        return paginas[numPagina] != null;
    }

    public void liberarPagina(int numPagina){
        paginas[numPagina] = null;
    }

    public String toString(){
        String s = "vAddr\t\tfAddr\t\tdisco\t\tcount\n";

        for(int x = 0; x < paginas.length; x++) {
            Pagina e = paginas[x];
            s += (e == null) ? "" : x * tamPagina + "\t\t\t" + e.toString() + "\n";
        }
        return s;
    }
}
