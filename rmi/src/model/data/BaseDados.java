package model.data;

import model.Product;
import model.Logger;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/*
 * Classe para armazenar os dados dos endereços de outros servidores e seus respectivos arquivos
 */
public class BaseDados {
    private Map<Integer, Product> products;
    private List<InetAddress> addresses;

    public BaseDados() {
        products = new TreeMap<>();
        addresses = new LinkedList<>();
        // Leitura dos arquivos de texto
        try {
            Files.lines(Paths.get("database.txt")).forEach(line -> {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                int amount = Integer.parseInt(fields[2]);
                products.put(id, new Product(name, new Logger(id, amount)));
            });

            Files.lines(Paths.get("addresses.txt")).forEach(line -> {
                try {
                    addresses.add(InetAddress.getByName(line));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * Metodo para pegar os endereços do servidor
     */
    public synchronized InetAddress[] getAddresses() {
        return addresses.toArray(new InetAddress[0]);
    }
    /*
     * Retorna os produtos do estoque
     */
    public synchronized Product[] getProducts() {
        return products.values().toArray(new Product[0]);
    }
    /*
     * Atualiza produto pelo registro, checando se no registro enviado, o produto existe e se possui a quantidade desejada
     */
    public synchronized boolean updateProduct(Logger logger) {
        if (logger.getAmount() > 0 && products.containsKey(logger.getId())) {
            Product product = products.get(logger.getId());
            Integer amount = product.getLogger().getAmount();

            if (amount >= logger.getAmount()) {
                product.getLogger().setAmount(amount - logger.getAmount());
                System.out.println(logger);
                return true;
            }
        }

        return false;
    }
}
