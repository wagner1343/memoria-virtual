package mv.tabeladepaginas;

public class EntradaSistema {
    private int addr;
    private boolean disponivel;
    private boolean disco;

    public EntradaSistema(int addr, boolean disponivel, boolean disco){
        this.addr = addr;
        this.disponivel = disponivel;
        this.disco = disco;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public String toString()
    {
        return "addr: " + addr + " disp: " + disponivel;
    }

    public boolean isDisco() {
        return disco;
    }

    public void setDisco(boolean disco) {
        this.disco = disco;
    }
}
