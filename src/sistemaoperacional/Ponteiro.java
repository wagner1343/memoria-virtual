package sistemaoperacional;

public class Ponteiro {
    private int addr;
    private int tam;

    public Ponteiro(int addr, int tam){
        this.addr = addr;
        this.tam =  tam;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }
}
