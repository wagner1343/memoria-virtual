package memoria.fisica.cache;

import memoria.fisica.MemoriaFisicaInterface;

public class MemoriaCache implements MemoriaCacheInterface {
    MemoriaFisicaInterface pai;
    private int latencia;
    CacheLine[] linhas;
    int tamLinha;
    int numBitsOffset;
    int numBitsLine;
    int numBitsTotal;
    int numBitsTag;

    public MemoriaCache(int numBitsTotal, int numLinhas,int numBitsOffset, MemoriaFisicaInterface pai, int latencia){
        this.latencia = latencia;
        this.tamLinha = (int) Math.pow(2, numBitsOffset);
        this.linhas = new CacheLine[numLinhas];
        this.pai = pai;
        this.numBitsTotal = numBitsTotal;
        this.numBitsOffset = numBitsOffset;
        numBitsLine = (int) (Math.log(numLinhas) / Math.log(2));
        numBitsTag = numBitsTotal - (numBitsOffset + numBitsLine);
        System.out.println("numBitsOffset: " + numBitsOffset + " numbitsline: " + numBitsLine + " numbitstag: " + numBitsTag);
    }
    @Override
    public int length() {
        return linhas.length;
    }

    @Override
    public void write(int addr, byte[] bytes) {
        for(int x = 0; x < bytes.length; x++){
            write(addr + x, bytes[x]);
        }
    }

    public void sleep(){
        try {
            Thread.sleep(latencia);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(int addr, byte b) {
        sleep();
        int numLinha = getLineNum(addr);
        int offset = getOffset(addr);
        int tag = getTagNum(addr);

        if(linhas[numLinha] != null && linhas[numLinha].getTag() == tag){
            linhas[numLinha].write(offset, b);
            pai.write(addr, b);
        }
        else {
            System.out.println("Cache miss");
            linhas[numLinha] = new CacheLine(tamLinha, tag);
            linhas[numLinha].write(0, pai.read(addr - offset, tamLinha));

            write(addr, b);
        }
    }

    @Override
    public byte[] read(int addr, int tam) {
        byte[] bytes = new byte[tam];

        for(int x = 0; x < tam; x++){
            bytes[x]  = read(addr + x);
        }

        return bytes;
    }

    @Override
    public byte read(int addr) {
        sleep();
        int numLinha = getLineNum(addr);
        int offset = getOffset(addr);
        int tag = getTagNum(addr);

        if(linhas[numLinha] != null && linhas[numLinha].getTag() == tag){
            return linhas[numLinha].read(offset);
        }
        else{
            System.out.println("Cache miss");
            linhas[numLinha] = new CacheLine(tamLinha, tag);
            linhas[numLinha].write(0, pai.read(addr - offset, tamLinha));

            return read(addr);
        }
    }

    @Override
    public CacheLine readLine(int numLinha) {
        return null;
    }

    @Override
    public void writeLine(int numLinha, CacheLine line) {

    }

    @Override
    public MemoriaFisicaInterface getPai() {
        return pai;
    }

    private int maxAddr(){
        return tamLinha * linhas.length;
    }

    public int getTagNum(int addr){
        String addrString = addrToString(addr);
        int tag;

        if(numBitsTag < 1)
            tag = 0;
        else{
            tag = Integer.parseInt(addrString.substring(0, numBitsTag), 2);
        }

        return tag;
    }

    public String addrToString(int addr){
        String addrString = Integer.toBinaryString(addr);

        while(addrString.length() < numBitsTotal)
            addrString = "0" + addrString;

        return  addrString;
    }

    public int getLineNum(int addr){
        String addrString = addrToString(addr);

        int lineNum = Integer.parseInt(addrString.substring(addrString.length() - (numBitsLine + numBitsOffset), addrString.length() - numBitsOffset), 2);
        return  lineNum;
    }

    public int getOffset(int addr){
        String addrString = addrToString(addr);

        int offset = Integer.parseInt(addrString.substring(addrString.length() - numBitsOffset), 2);

        return offset;
    }

    public String toString(){
        String s = "";

        for(int x = 0; x < linhas.length; x++){
            s += "Linha " + x +": " + (linhas[x] == null ? "nula" : linhas[x].toString())  + "\n";
        }
        return  s;
    }
}
