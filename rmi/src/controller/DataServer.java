package controller;

import model.Register;
import model.data.DataBase;
import model.network.Connection;

import java.io.IOException;
import java.rmi.RemoteException;
/*
    Classe que adminstra as conexões entre servidores
 */
public class DataServer implements Connection {
    private DataBase db;

    public DataServer(DataBase db) {
        this.db = db;
    }
    /*
        Atualiza os registros para outros servidores
     */
    @Override
    public boolean updateRegister(Register register) throws RemoteException {
        try {
            return db.updateProduct(register);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
