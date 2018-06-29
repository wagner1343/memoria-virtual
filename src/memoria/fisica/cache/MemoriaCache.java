package memoria.fisica.cache;

import memoria.fisica.MemoriaFisicaInterface;

public class MemoriaCache implements MemoriaCacheInterface {
    MemoriaFisicaInterface pai;
    CacheLine[] linhas;
    int tamLinha;
    int numBitsOffset;
    int numBitsLine;
    int numBitsTotal;
    int numBitsTag;

    public MemoriaCache(int numBitsTotal, int numLinhas,int tamLinha, MemoriaFisicaInterface pai){
        this.tamLinha = tamLinha;
        this.linhas = new CacheLine[numLinhas];
        this.pai = pai;
        this.numBitsTotal = numBitsTotal;
        numBitsOffset = (int) (Math.log(tamLinha) / Math.log(2));
        numBitsLine = (int) (Math.log(numLinhas) / Math.log(2));
        numBitsTag = numBitsTotal - (numBitsOffset + numBitsLine);
        System.out.println("numBitsOffset: " + numBitsOffset + " numbitsline: " + numBitsLine + " numbitstag: " + numBitsTag);
    }
    @Override
    public int length() {
        return linhas.length;
    }

    @Override
    public void write(int addr, Byte[] bytes) {

    }

    @Override
    public void write(int addr, Byte b) {

    }

    @Override
    public Byte[] read(int addr, int tam) {
        return new Byte[0];
    }

    @Override
    public Byte read(int addr) {
        return null;
    }

    @Override
    public CacheLine readLine() {
        return null;
    }

    @Override
    public void writeLine(CacheLine line) {

    }

    @Override
    public MemoriaFisicaInterface getPai() {
        return null;
    }

    private int maxAddr(){
        return tamLinha * linhas.length;
    }

    public int getTagNum(int addr){
        String addrString = addrToString(addr);
        System.out.println("adrtostring : " + addrString);
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
        String addrString = Integer.toBinaryString(addr);

        int lineNum = Integer.parseInt(addrString.substring(addrString.length() - (numBitsLine + numBitsOffset), addrString.length() - numBitsOffset), 2);
        return  lineNum;
    }

    public int getOffset(int addr){
        String addrString = Integer.toBinaryString(addr);

        int offset = Integer.parseInt(addrString.substring(numBitsTotal - numBitsOffset), 2);

        return offset;
    }
}
