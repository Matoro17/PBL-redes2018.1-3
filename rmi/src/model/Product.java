package model;

import java.io.Serializable;
/*
    Classe do produto carregado no sistema
 */
public class Product implements Serializable {
    private String name;
    private Register register;

    public Product(String name, Register register) {
        this.setName(name);
        this.setRegister(register);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return String.format("Id: %-5d\tName: %-15s\tAmount: %-5d",
                getRegister().getId(), getName(), getRegister().getAmount());
    }
}
