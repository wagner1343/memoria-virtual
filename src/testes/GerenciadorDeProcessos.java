package testes;

import memoria.mmu.FaltaDePaginaException;
import processador.Processador;
import sistemaoperacional.OS;
import sistemaoperacional.Processo;
import sistemaoperacional.ProcessoInexistenteException;

import java.util.ArrayList;
import java.util.Scanner;

public class GerenciadorDeProcessos {
    Scanner in;
    OS os;
    Processador processador;
    ArrayList<Processo> processos;

    public GerenciadorDeProcessos(OS os){
        this.os = os;
        processador = os.getProcessador();
        processos = os.getProcessos();
        in = new Scanner(System.in);
    }

    public void explorar(){
        int escolha;

        while(true){
            System.out.println("Ações");
            System.out.println("0 - Sair");
            System.out.println("1 - Novo processo");
            System.out.println("2 - Gerenciar processos existentes");
            System.out.println("3 - Mostrar TLB");
            System.out.println("4 - Mostrar cache");

            escolha = Integer.parseInt(in.nextLine());
            switch (escolha){
                case 0:
                    return;
                case 1:
                    novoProcesso();
                case 2:
                    selecionarProcesso();
                    break;
                case 3:
                    mostrarTLB();
                    break;
                case 4:
                    mostrarCache();
                    break;
                default:
                    System.out.println("Opção invalida");
            }
        }
    }

    private void mostrarCache(){
        System.out.println(processador.getCache().toString());
    }

    private void mostrarTLB(){
        System.out.println(processador.getMmu().getTlb().toString());
    }

    public void novoProcesso(){
        System.out.println("Digite o nome para o novo processo");
        String nome = in.nextLine();

        os.criarProcesso(nome);
    }

    public void selecionarProcesso(){
        int escolha;

        System.out.println("Selecione um processo para gerenciar (id), ou 0 para voltar");
        for(int x = 0; x < processos.size(); x++){
            Processo processo = processos.get(x);
            System.out.println(processo.getPid() + " - " + processo.getName());
        }
        escolha = Integer.parseInt(in.nextLine());

        if(escolha == 0){
            return;
        }
        try {
            gerenciarProcesso(escolha);
        } catch (ProcessoInexistenteException e) {
            System.out.println("Processo inexistente");
            e.printStackTrace();
        }

    }

    public void gerenciarProcesso(int pid) throws ProcessoInexistenteException {
        Processo processo = os.buscarProcesso(pid);

        processador.setTabelaFAddr(processo.getTabelaFAddr());
        processador.setPid(processo.getPid());

        while (true) {
            int escolha;

            System.out.println("Ações: ");
            System.out.println("0 - Voltar");
            System.out.println("1 - Ler");
            System.out.println("2 - Escrever");
            System.out.println("9 - Finalizar");

            escolha = Integer.parseInt(in.nextLine());

            switch (escolha) {
                case 0:
                    return;
                case 1:
                    ler();
                    break;
                case 2:
                    escrever();
                    break;
                case 9:
                    os.finalizarProcesso(pid);
                    break;
                default:
                    System.out.println("Opção invalida");
                    break;
            }
        }
    }

    public void ler(){
        int vAddr;
        int tam;

        System.out.println("Digite o endereço");
        vAddr = Integer.parseInt(in.nextLine());

        System.out.println("Digite o a quantia de bytes para ler a partir do endereço");
        tam = Integer.parseInt(in.nextLine());

        try {
            byte[] bytes = processador.read(vAddr, tam);
            char[] chars = new char[bytes.length];

            for(int x = 0; x < bytes.length; x++){
                chars[x] = (char) bytes[x];
            }
            System.out.println(String.valueOf(chars));
        } catch (FaltaDePaginaException e) {
            e.printStackTrace();
        }
    }

    public void escrever(){
        int vAddr;
        String val;

        System.out.println("Digite o endereço");
        vAddr = Integer.parseInt(in.nextLine());

        System.out.println("Digite uma frase");
        val = in.nextLine();

        char[] chars = val.toCharArray();
        byte[] bytes = new byte[chars.length];

        for(int x=  0; x < chars.length; x++){
            bytes[x] = (byte) chars[x];
        }

        try {
            processador.write(vAddr, bytes);
        } catch (FaltaDePaginaException e) {
            e.printStackTrace();
        }
    }

}
