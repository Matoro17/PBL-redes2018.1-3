package model.network;

import model.Register;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Sentinel extends Thread {
    private InetAddress address;
    private Queue<Register> registers;

    public Sentinel(InetAddress address) {
        this.setDaemon(true);
        this.address = address;
        this.registers = new ConcurrentLinkedQueue<>();
    }

    public synchronized void setRegister(Register register) {
        registers.add(register);
    }

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
