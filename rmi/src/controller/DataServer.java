package controller;

import model.Register;
import model.data.DataBase;
import model.network.Connection;

import java.rmi.RemoteException;

public class DataServer implements Connection {
    private DataBase db;

    public DataServer(DataBase db) {
        this.db = db;
    }

    @Override
    public boolean updateRegister(Register register) throws RemoteException {
        return db.updateProduct(register);
    }
}
