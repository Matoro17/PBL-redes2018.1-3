package controller;

import model.Logger;
import model.data.BaseDados;
import model.network.Connection;

import java.rmi.RemoteException;

public class DataServer implements Connection {
    private BaseDados db;

    public DataServer(BaseDados db) {
        this.db = db;
    }
    /*
     * Atualizar produtos na base de dados através do registro
     */
    @Override
    public boolean updateRegister(Logger logger) throws RemoteException {
        return db.updateProduct(logger);
    }
}
