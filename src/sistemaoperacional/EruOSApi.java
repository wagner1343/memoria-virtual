package sistemaoperacional;

public class EruOSApi implements OSInterface {
    int pid;
    EruOs os;

    public EruOSApi(EruOs os,int pid){
        this.pid = pid;
        this.os = os;
    }

    @Override
    public Ponteiro malloc(int addr, int tam) {
        return null;
    }
}
