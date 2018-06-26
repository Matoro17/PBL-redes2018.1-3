package model;

import java.io.Serializable;

public class Produto implements Serializable {
    private String name;
    private Logger logger;

    public Produto(String name, Logger logger) {
        this.setName(name);
        this.setLogger(logger);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public String toString() {
        return String.format("Id: %-5d\tName: %-15s\tAmount: %-5d",
                getLogger().getId(), getName(), getLogger().getAmount());
    }
}
