package os;

public class Processo {
    private int pid;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String toString(){
        return String.valueOf(pid);
    }
}
