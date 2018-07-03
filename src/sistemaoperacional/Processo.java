package sistemaoperacional;

public class Processo {
    private int pid;
    private String name;
    private int tabelaFAddr;

    public Processo(String name, int pid, int tabelaFAddr){
        this.name = name;
        this.pid = pid;
        this.tabelaFAddr = tabelaFAddr;
    }

    public int getPid() {
        return pid;
    }

    public String toString(){
        return String.valueOf(pid);
    }

    public String getName() {
        return name;
    }

    public int getTabelaFAddr() {
        return tabelaFAddr;
    }

    public void setTabelaFAddr(int tabelaFAddr) {
        this.tabelaFAddr = tabelaFAddr;
    }
}
