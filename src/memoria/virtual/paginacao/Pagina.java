package memoria.virtual.paginacao;

public class Pagina {
    private int baseFAddr;
    private boolean disco;
    private int count;

    public Pagina(int baseFAddr, boolean disco){
        this.baseFAddr = baseFAddr;
        this.disco = disco;
        this.count = 1;
    }

    public int getBaseFAddr() {
        return baseFAddr;
    }

    public void setBaseFAddr(int baseFAddr) {
        this.baseFAddr = baseFAddr;
    }

    public boolean isDisco() {
        return disco;
    }

    public void setDisco(boolean disco) {
        this.disco = disco;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString(){
        return baseFAddr + "\t\t\t" + disco + "\t\t\t" + count;
    }
}
