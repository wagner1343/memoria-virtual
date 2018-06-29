package memoria.fisica;

public class MemoriaRam implements MemoriaFisicaInterface {
    private Byte[] mem;
    private int latencia;

    public int length(){
        return mem.length;
    }

    public MemoriaRam(int tam, int latencia){
        this.mem = new Byte[tam];
        this.latencia = latencia;
    }

    public Byte[] read(int addr, int tam){
        sleepLat();

        Byte[] bytes = new Byte[tam];

        for(int x = 0; x < tam; x++){
            bytes[x] = mem[addr+x];
        }

        return bytes;
    }

    @Override
    public Byte read(int addr) {
        return mem[addr];
    }

    public void write(int addr, Byte[] bytes){
        sleepLat();

        for(int x = 0; x < bytes.length; x++){
            mem[addr + x] = bytes[x];
        }
    }

    @Override
    public void write(int addr, Byte b) {
        mem[addr] = b;
    }

    private void sleepLat(){
        try {
            Thread.sleep(latencia);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
