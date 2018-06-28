package model.network;

import model.Register;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
/*
    Classe responsável pela chcagem dos servidores
 */
public class Sentinel extends Thread {
    private InetAddress address;
    private Queue<Register> registers;

    public Sentinel(InetAddress address) {
        this.setDaemon(true);
        this.address = address;
        this.registers = new ConcurrentLinkedQueue<>();
    }
    /*
        Adiciona registros a fila de registros
     */
    public synchronized void setRegister(Register register) {
        registers.add(register);
    }
    /*
        Metodo iniciado pela thread, no qual checa se existem informações para enviar para outros servidores, e também checa se é possível alcançar o ip, através do ping
        Além de enviar os registros que faltam para os endereços que entram na rede
     */
    @Override
    public void run() {
        while (true) {
            try {
                if (!registers.isEmpty() && address.isReachable(500)) {
                    Registry registry = LocateRegistry.getRegistry(address.getHostAddress());
                    Connection connection = (Connection) registry.lookup("Connection");

                    while (!registers.isEmpty()) {
                        if (connection.updateRegister(registers.peek())) {
                            registers.remove();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
