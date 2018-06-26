package model;

import java.io.Serializable;

public class Logger implements Serializable {
    private Integer id;
    private Integer amount;

    public Logger(int id, int amount) {
        this.setId(id);
        this.setAmount(amount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("Id: %-5d\tAmount: %-5d",
                getId(), getAmount());
    }
}
