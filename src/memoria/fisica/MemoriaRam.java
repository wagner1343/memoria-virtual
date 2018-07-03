package memoria.fisica;

public class MemoriaRam implements MemoriaFisicaInterface {
    private byte[] mem;
    private int latencia;

    public int length(){
        return mem.length;
    }

    public MemoriaRam(int tam, int latencia){
        this.mem = new byte[tam];
        this.latencia = latencia;
    }

    public byte[] read(int addr, int tam){
        System.out.print("MemoriaRam.read");
        System.out.print(" addr = [" + addr + "], tam = [" + tam + "]");
        sleepLat();

        byte[] bytes = new byte[tam];

        for(int x = 0; x < tam; x++){
            bytes[x] = mem[addr+x];
        }


        String read = "";
        for(int x = 0; x < bytes.length; x++){
            read += bytes[x];
        }

        System.out.println(" Leitura = " + read);
        return bytes;
    }

    @Override
    public byte read(int addr) {
        return mem[addr];
    }

    public void write(int addr, byte[] bytes){
        String chars = "";

        for(int x = 0; x < bytes.length; x++){
            chars +=  bytes[x];
        }

        System.out.println("Escrevendo no endereço fisico " + addr + " valores " + chars.toString());
        sleepLat();

        for(int x = 0; x < bytes.length; x++){
            mem[addr + x] = bytes[x];
        }
    }

    @Override
    public void write(int addr, byte b) {
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
