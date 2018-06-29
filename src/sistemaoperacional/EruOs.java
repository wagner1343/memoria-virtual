package sistemaoperacional;

import memoria.fisica.MemoriaFisicaInterface;
import memoria.virtual.MemoriaVirtual;

public class EruOs extends OS {
    private static final int totalVAddr = 100;
    private static final int tamPagina = 10;
    private static final int numPaginas = totalVAddr / tamPagina;
    private static final int numTabelas = 10;

    MemoriaVirtual memoriaVirtual;

    protected EruOs(MemoriaFisicaInterface memoriaFisicaInterface) {
        super(memoriaFisicaInterface);
        memoriaVirtual = new MemoriaVirtual(memoriaFisicaInterface, numTabelas, numPaginas, totalVAddr);
    }

    @Override
    public Ponteiro malloc(int tid, int addr, int tam) {
        for(int x = 0; x < tam; x += tamPagina)
            if(!memoriaVirtual.alocarPagina(tid, addr + x))
                return null;

        return new Ponteiro(addr, tam);
    }

    @Override
    public void free(int tid, Ponteiro ponteiro) {
        for(int x = 0; x < ponteiro.getTam(); x += tamPagina){
            memoriaVirtual.liberarPagina(tid, ponteiro.getAddr() + x);
        }
    }

    @Override
    public Byte[] read(int tid, Ponteiro ponteiro) {
        return memoriaVirtual.read(tid, ponteiro.getAddr(), ponteiro.getTam());
    }

    @Override
    public void write(int tid, Ponteiro ponteiro, Byte[] bytes) {
        memoriaVirtual.write(tid, ponteiro.getAddr(), bytes);
    }
}
