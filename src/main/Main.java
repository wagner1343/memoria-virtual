package main;

import os.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        Sistema sistema = new Sistema();
        ArrayList<Processo> processos = new ArrayList<>();

        for(int x = 0; x < 10; x++){
            Processo processo = new Processo();
            sistema.inicializarProcesso(processo);
            try {
                sistema.malloc(processo, 10);
                sistema.write(processo, 10, (byte) x);
            } catch (TabelaInexistenteException e) {
                e.printStackTrace();
            } catch (MemoriaIndisponivelException e) {
                e.printStackTrace();
            } catch (EntradaInexistenteException e) {
                e.printStackTrace();
            }
            processos.add(processo);
        }

        for(int x = 0; x < 10; x++){
            Processo processo = processos.get(x);
            try {
                System.out.println("Read p " + x + " : " + sistema.read(processo, 10));
            }catch (EntradaInexistenteException e) {
                e.printStackTrace();
            }
            processos.add(processo);
        }



        Processo p1 = new Processo();
        Processo p2 = new Processo();
        Processo p3 = new Processo();

        sistema.inicializarProcesso(p1);
        sistema.inicializarProcesso(p2);

        try {
            sistema.malloc(p1, 10);
            sistema.malloc(p2, 10);
            sistema.write(p1, 10, (byte) 1);
            sistema.write(p2, 10, (byte) 2);

            System.out.println("Leitura de p1 (10): " + sistema.read(p1, 10));
            System.out.println("Leitura de p2 (10): " + sistema.read(p2, 10));
            sistema.free(p2, 10);
            sistema.free(p1, 10);
        } catch (TabelaInexistenteException e) {
            e.printStackTrace();
        } catch (MemoriaIndisponivelException e) {
            e.printStackTrace();
        } catch (EntradaInexistenteException e) {
            e.printStackTrace();
        }

        try {
            sistema.write(p2, 10, (byte) 2);

            System.out.println(sistema.read(p2, 10));
        }catch (EntradaInexistenteException e) {
            System.out.println("Falha no sistema, entrada inexistende. Provavel falha na alocação de memória para o processo");
        }


        try {
            sistema.malloc(p3, 10);
            sistema.write(p3, 10, (byte) 3);

            System.out.println(sistema.read(p3, 10));
        } catch (TabelaInexistenteException e) {
            System.out.println("Falha no sistema, tabela inexistente. Provavel inicialização incorreta do processo");
        } catch (MemoriaIndisponivelException e) {
            e.printStackTrace();
        } catch (EntradaInexistenteException e) {
            e.printStackTrace();
        }
    }
}
