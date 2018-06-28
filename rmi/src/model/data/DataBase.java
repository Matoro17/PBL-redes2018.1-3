package model.data;

import model.Product;
import model.Register;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/*
    Classe do banco de dados
 */
public class DataBase {
    private Map<Integer, Product> products;
    private List<InetAddress> addresses;
    /*
        Contrutor no qual lê os arquivos de endereços e produtos
     */
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
    /*
        Recupera os endereços de ip dos sentinelass
     */
    public synchronized InetAddress[] getAddresses() {
        return addresses.toArray(new InetAddress[0]);
    }
    /*
        Retorno dos produtos da base de dados
     */
    public synchronized Product[] getProducts() {
        return products.values().toArray(new Product[0]);
    }
    /*
        Metodo para atualizar o produto
     */
    public synchronized boolean updateProduct(Register register) throws IOException {
        if (register.getAmount() > 0 && products.containsKey(register.getId())) {
            Product product = products.get(register.getId());
            Integer amount = product.getRegister().getAmount();

            if (amount >= register.getAmount()) {
                product.getRegister().setAmount(amount - register.getAmount());
                System.out.println(register);
                changeArchive();
                return true;
            }
        }

        return false;
    }
    /*
        Metodo para atualização do arquivo de texto, o qual apaga o banco de dados e res-escreve com base no map do estoque
     */
    public void changeArchive() throws IOException {
        FileOutputStream buffer = null;
        String texto = "";
        try {
            buffer = new FileOutputStream("database.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Map.Entry<Integer, Product> entry : products.entrySet())
        {
            Product temp = entry.getValue();
            texto = texto + (temp.getRegister().getId()) + "," + temp.getName() + "," + temp.getRegister().getAmount() + "\n";
        }
        String limpa = "";
        buffer.write(limpa.getBytes());
        buffer.write(texto.getBytes());
    }
}
