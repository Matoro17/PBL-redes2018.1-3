package model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Servidor implements Compra {
    private List<InetAddress> hosts;
    private Log logs;
    private TreeMap<Integer, Produto> produtos;

    public Servidor() {
        hosts = new LinkedList<>();
        logs = new Log();
        produtos = new TreeMap<>();

        try {
            Files.lines(Paths.get("hosts.txt")).forEach(linha -> {
                try { hosts.add(InetAddress.getByName(linha)); }
                catch (UnknownHostException e) { e.printStackTrace(); }
            });

            Files.lines(Paths.get("produtos.txt")).forEach(linha -> {
                String[] campos = linha.split(",");

                int id = Integer.parseInt(campos[0]);
                String nome = campos[1];
                int quantidade = Integer.parseInt(campos[2]);
                double valor = Double.parseDouble(campos[3]);

                Produto produto = new Produto(id, nome, valor, quantidade);
                produtos.put(id, produto);
            });
        } catch (IOException e) { e.printStackTrace(); }

        logs.setDaemon(true);
        logs.start();
    }

    public Produto[] listarProdutos() {
        return produtos.values().toArray(new Produto[0]);
    }

    public boolean realizarComprar(int id, int quantidade) {
        if (quantidade > 0 && produtos.containsKey(id)) {
            try {
                List<InetAddress> rollback = new LinkedList<>();
                List<InetAddress> log = new LinkedList<>();

                for (InetAddress host : hosts) {
                    if (host.isReachable(300)) {
                        rollback.add(host);

                        Registry registry = LocateRegistry.getRegistry(host.getHostAddress());
                        Compra compra = (Compra) registry.lookup("Compra");

                        if (!compra.diminuirEstoque(id, quantidade)) {
                            for (InetAddress r : rollback) {
                                registry = LocateRegistry.getRegistry(r.getHostAddress());
                                compra = (Compra) registry.lookup("Compra");
                                compra.aumentarEstoque(id, quantidade);
                                return false;
                            }
                        }
                    } else {
                        log.add(host);
                    }
                }

                for (InetAddress l : log)
                    logs.adicionarLog(l, id, quantidade);

                return true;
            } catch (IOException | NotBoundException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean diminuirEstoque(int id, int quantidade) throws RemoteException {
        Produto produto = produtos.get(id);

        if (produto.getQuantidade() >= quantidade) {
            produto.setQuantidade(produto.getQuantidade() - quantidade);
            return true;
        }

        return false;
    }

    @Override
    public void aumentarEstoque(int id, int quantidade) throws RemoteException {
        Produto produto = produtos.get(id);
        produto.setQuantidade(produto.getQuantidade() + quantidade);
    }
}
