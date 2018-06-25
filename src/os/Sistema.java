package os;

import mv.tabeladepaginas.EntradaSistema;
import mv.tabeladepaginas.Tabela;
import mv.tabeladepaginas.TabelaSistema;
import mv.tabeladepaginas.entrada.Entrada;

import java.util.ArrayList;
import java.util.Random;

public class Sistema {
    Random random;

    int tamMemoria = 100;
    int tamPagina = 10;
    int tamTabela = tamMemoria/tamPagina;

    Byte[] memoria;

    ArrayList<Tabela> tabelas;
    ArrayList<Integer> pids;
    TabelaSistema tabelaSistema;
    private int GLOBAL_PID = 1000;

    public Sistema(){
        memoria = new Byte[tamMemoria];

        tabelaSistema = new TabelaSistema(tamMemoria/tamPagina);
        random = new Random();
        tabelas = new ArrayList<>();
        pids = new ArrayList<>();

        inicializarTabelasSistema();

        System.out.println(tabelaSistema.toString());
    }

    public void inicializarProcesso(Processo processo){
        processo.setPid(gerarIdDeProcesso());

        Tabela tabela = new Tabela(tamTabela, processo.getPid());
        tabelas.add(tabela);
    }

    public boolean malloc(Processo processo, int vAddr) throws TabelaInexistenteException, MemoriaIndisponivelException {
        System.out.println("malloc("+processo.toString()+","+vAddr+")");
        int numPagina = vAddr / tamPagina;
        EntradaSistema entradaDisponivel = buscarMemoriaDisponivel();

        Tabela tabela = buscarTabelaProcesso(processo);

        if(tabela == null)
            throw new TabelaInexistenteException();

        if(entradaDisponivel == null)
            throw new MemoriaIndisponivelException();

        System.out.println("numPagina: " + numPagina + " entradaDisponivel: " + entradaDisponivel.toString() + " tabela: " + tabela.toString());

        tabela.setEntrada(numPagina, new Entrada(entradaDisponivel));
        System.out.println("Malloc ok");
        return true;
    }

    public void free(Processo processo, int vAddr) throws TabelaInexistenteException {
        int numPagina = vAddr / tamPagina;

        Tabela tabela = buscarTabelaProcesso(processo);

        if(tabela == null)
            throw new TabelaInexistenteException();

        tabela.setEntrada(numPagina, null);
    }

    public Byte read(Processo processo, int vAddr) throws EntradaInexistenteException {
        int fAddr = traduzirEnd(processo, vAddr);

        return memoria[fAddr];
    }

    public void write(Processo processo, int vAddr, Byte b) throws EntradaInexistenteException {
        int fAddr = traduzirEnd(processo, vAddr);

        memoria[fAddr] = b;
    }

    private Tabela buscarTabelaProcesso(Processo processo){
        Tabela tabela = null;
        for(Tabela t : tabelas){
            if(t.getPid() == processo.getPid()) {
                tabela = t;
                break;
            }
        }

        return tabela;
    }

    private int traduzirEnd(Processo processo, int vAddr) throws EntradaInexistenteException{
        int numPagina = vAddr / tamPagina;

        Tabela tabela = buscarTabelaProcesso(processo);

        if(tabela == null || tabela.getEntrada(numPagina) == null)
            throw new EntradaInexistenteException();

        return tabela.getEntrada(numPagina).getEntradaSistema().getAddr() + (vAddr%tamPagina);
    }

    private void inicializarTabelasSistema(){
        for(int x = 0; x < tamTabela; x++){
            EntradaSistema entradaSistema =  new EntradaSistema(x * tamPagina, true, false);
            tabelaSistema.setEntrada(x,entradaSistema);
            System.out.println("Entrada " + entradaSistema.toString() + " adicionada as tabelas do sistema");
        }
    }
    
    private EntradaSistema buscarMemoriaDisponivel(){
        for(EntradaSistema e : tabelaSistema.getEntradas()){
            if(e.isDisponivel()){
                e.setDisponivel(false);
                return e;
            }
        }

        return null;
    }

    private int gerarIdDeProcesso(){
        GLOBAL_PID++;

        return GLOBAL_PID;
    }
}
