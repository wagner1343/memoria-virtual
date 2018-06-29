package main;

import memoria.fisica.MemoriaRam;
import memoria.virtual.MemoriaVirtual;

import java.util.Random;

public class TestaTabelaPaginas {
    public static void main(String[] args){
        int numProcessos = 10;
        int tamPagina = 8;
        int totalVAddr = 2048;
        int totalFAddr = 1024;

        MemoriaVirtual memoriaVirtual = new MemoriaVirtual(new MemoriaRam(totalFAddr, 0), numProcessos,
                tamPagina, totalVAddr);

        int chrome = memoriaVirtual.criarTabelaDePaginas();
        int lol = memoriaVirtual.criarTabelaDePaginas();
        int dotinha = memoriaVirtual.criarTabelaDePaginas();

        memoriaVirtual.alocarPagina(chrome, 800);

        Random random = new Random();

        int[] tids = {chrome, lol, dotinha};
        int count = 0;
        for(int tid : tids){
            for(int x = 0; x < 3; x++) {
                int vAddr = random.nextInt(totalVAddr);
                memoriaVirtual.alocarPagina(tid, vAddr);
                memoriaVirtual.write(tid, vAddr, new Byte[]{(byte) (count * 10 + x)});
            }
            count++;
        }

        memoriaVirtual.liberarPagina(chrome, 800);

        memoriaVirtual.alocarPagina(dotinha, 800);

        for(int tid : tids){
            System.out.println(memoriaVirtual.getTabela(tid).toString());
        }

    }
}
