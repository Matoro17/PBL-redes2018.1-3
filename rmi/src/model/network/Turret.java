package model.network;

import model.Logger;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Turret extends Thread {
    private InetAddress address;
    private Queue<Logger> loggers;

    public Turret(InetAddress address) {
        this.setDaemon(true);
        this.address = address;
        this.loggers = new ConcurrentLinkedQueue<>();
    }

    public synchronized void setRegister(Logger logger) {
        loggers.add(logger);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (!loggers.isEmpty() && address.isReachable(500)) {
                    Registry registry = LocateRegistry.getRegistry(address.getHostAddress());
                    Connection connection = (Connection) registry.lookup("Connection");

                    while (!loggers.isEmpty()) {
                        if (connection.updateRegister(loggers.peek())) {
                            loggers.remove();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
