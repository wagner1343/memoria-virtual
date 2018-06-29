package sistemaoperacional;

public class Processo {
    private int pid;
    private int tid;
    private String name;

    public Processo(String name){
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String toString(){
        return String.valueOf(pid);
    }

    public String getName() {
        return name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
