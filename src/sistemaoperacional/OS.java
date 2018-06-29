package sistemaoperacional;

import memoria.fisica.MemoriaFisicaInterface;

public abstract class OS {
    private MemoriaFisicaInterface memoriaFisicaInterface;

    protected OS(MemoriaFisicaInterface memoriaFisicaInterface){
        this.memoriaFisicaInterface = memoriaFisicaInterface;
    }

    public MemoriaFisicaInterface getMemoriaFisicaInterface(){
        return this.memoriaFisicaInterface;
    }

    public void setMemoriaFisicaInterface(MemoriaFisicaInterface memoriaFisicaInterface) {
        this.memoriaFisicaInterface = memoriaFisicaInterface;
    }

    public abstract Ponteiro malloc(int pid, int vAddr, int tam);
    public abstract void free(int pid, Ponteiro ponteiro);
    public abstract Byte[] read(int pid, Ponteiro ponteiro);
    public abstract void write(int pid, Ponteiro ponteiro, Byte[] bytes);
}
