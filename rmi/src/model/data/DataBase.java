package model.data;

import model.Product;
import model.Register;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DataBase {
    private Map<Integer, Product> products;
    private List<InetAddress> addresses;

    public DataBase() {
        products = new TreeMap<>();
        addresses = new LinkedList<>();

        try {
            Files.lines(Paths.get("database.txt")).forEach(line -> {
                String[] fields = line.split(",");
                int id = Integer.parseInt(fields[0]);
                String name = fields[1];
                int amount = Integer.parseInt(fields[2]);
                products.put(id, new Product(name, new Register(id, amount)));
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

    public synchronized InetAddress[] getAddresses() {
        return addresses.toArray(new InetAddress[0]);
    }

    public synchronized Product[] getProducts() {
        return products.values().toArray(new Product[0]);
    }

    public synchronized boolean updateProduct(Register register) {
        if (register.getAmount() > 0 && products.containsKey(register.getId())) {
            Product product = products.get(register.getId());
            Integer amount = product.getRegister().getAmount();

            if (amount >= register.getAmount()) {
                product.getRegister().setAmount(amount - register.getAmount());
                System.out.println(register);
                return true;
            }
        }

        return false;
    }
}
