package memoria.virtual;

import memoria.virtual.paginacao.TabelaDePaginas;
import memoria.virtual.paginacao.Pagina;
import memoria.fisica.MemoriaFisicaInterface;

public class MemoriaVirtual {
    private int totalVAddr;
    private int tamPagina;

    MemoriaFisicaInterface memoriaFisica;
    TabelaDePaginas[] tabelas;
    boolean mapeamentoDeMemoriaFisica[];

    public MemoriaVirtual(MemoriaFisicaInterface memoriaFisica, int numTabelas, int tamPagina, int totalVAddr){
        this.memoriaFisica = memoriaFisica;
        tabelas = new TabelaDePaginas[numTabelas];
        mapeamentoDeMemoriaFisica = new boolean[totalVAddr / tamPagina];
        this.tamPagina = tamPagina;
        this.totalVAddr = totalVAddr;
    }

    public void mapearMemoriaFisica(){
        for(int x = 0; x < memoriaFisica.length() / tamPagina; x++){
        }
    }

    public Byte[] read(int tid, int vAddr, int tam){
        int fAddr = traduzirAddr(tid, vAddr);

        Byte[] dados = memoriaFisica.read(fAddr, tam);
        return dados;
    }

    public void write(int tid, int vAddr, Byte[] bytes){
        int fAddr = traduzirAddr(tid, vAddr);

        memoriaFisica.write(fAddr, bytes);
    }

    private int traduzirAddr(int tid, int vAddr){
        TabelaDePaginas tabela = tabelas[tid];
        int numPagina = vAddr / tamPagina;

        int baseAddr = tabela.getPagina(numPagina).getBaseFAddr();
        int offset = vAddr % tamPagina;

        return baseAddr + offset;
    }

    public void deletarTabelaDePaginas(int tid){
        tabelas[tid] = null;
    }

    public int criarTabelaDePaginas(){
        for(int x = 0; x < tabelas.length; x++)
            if (tabelas[x] == null){
                tabelas[x] = new TabelaDePaginas(totalVAddr/tamPagina, tamPagina);
                return x;
            }

        return -1;
    }

    public boolean alocarPagina(int tid, int vAddr){
        int numPagina = getNumPagina(vAddr);
        TabelaDePaginas tabela = getTabela(tid);

        if(!tabela.isAlocado(numPagina)){
            int baseFAddr = buscarPaginaLivreMemoriaFisica();

            if(baseFAddr == -1)
                return false;

            tabela.setPagina(numPagina, new Pagina(baseFAddr, false));
            return true;
        }
        else{
            Pagina pagina = tabela.getPagina(numPagina);
            pagina.setCount(pagina.getCount()+1);
        }

        return  true;
    }

    public void liberarPagina(int tid, int vAddr){
        int numPagina = getNumPagina(vAddr);
        TabelaDePaginas tabela = getTabela(tid);

        if(tabela.isAlocado(numPagina)){
            Pagina pagina = tabela.getPagina(numPagina);
            pagina.setCount(pagina.getCount()-1);

            if(pagina.getCount() == 0) {
                mapeamentoDeMemoriaFisica[pagina.getBaseFAddr() / tamPagina] = false;
                tabela.liberarPagina(numPagina);
            }
        }
    }

    private int buscarPaginaLivreMemoriaFisica(){
        int count = 0;
        for(int x = 0; x < mapeamentoDeMemoriaFisica.length; x++){
            if(!mapeamentoDeMemoriaFisica[x]){
                mapeamentoDeMemoriaFisica[x] = true;
                return x * tamPagina;
            }
        }

        return -1;
    }

    private int getNumPagina(int vAddr){
        return vAddr / tamPagina;
    }

    public TabelaDePaginas getTabela(int tid){
        return tabelas[tid];
    }
}
