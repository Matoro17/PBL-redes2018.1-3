package model;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Log extends Thread {
    private Hashtable<InetAddress, List<Integer[]>> logs;

    public Log() {
        logs = new Hashtable<>();
    }

    public synchronized void adicionarLog(InetAddress host, int id, int quantidade) {
        if (!logs.containsKey(host)) logs.put(host, new LinkedList<>());
        logs.get(host).add(new Integer[] {id, quantidade});
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (InetAddress host : logs.keySet()) {
                    List<Integer[]> log = logs.get(host);

                    if (log.size() > 0 && host.isReachable(300)) {
                        Registry servidor = LocateRegistry.getRegistry(host.getHostAddress());
                        Compra compra = (Compra) servidor.lookup("Compra");

                        while (log.size() > 0) {
                            Integer[] temp = log.get(0);
                            compra.diminuirEstoque(temp[0], temp[1]);
                            log.remove(0);
                        }
                    }
                }
            } catch (IOException | NotBoundException e) { e.printStackTrace(); }
        }
    }
}
