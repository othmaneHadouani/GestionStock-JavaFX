package paiement.socketClient.services;

import paiement.socketClient.actions.Actions;
import paiement.socketClient.data.Compte;
import paiement.socketClient.data.Wrapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

public class EmissionServices {
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public EmissionServices(ObjectOutputStream outputStream, ObjectInputStream inputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    public Compte debiter(Compte compte, double apayer) {
        Wrapper wrapper = new Wrapper();
        wrapper.setData(compte);
        wrapper.setaPyer(apayer);
        wrapper.setActions(Actions.Debiter);
        try {
            outputStream.writeObject(wrapper);
            outputStream.flush();
            try {
                Wrapper wrapper2 = (Wrapper) inputStream.readObject();
                return (Compte) wrapper2.getData();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Compte Tester(Compte compte) {
        Wrapper wrapper = new Wrapper();
        wrapper.setData(compte);
        wrapper.setActions(Actions.TestSolde);
        try {
            outputStream.writeObject(wrapper);
            outputStream.flush();
            try {
                Wrapper wrapper2 = (Wrapper) inputStream.readObject();
                return (Compte) wrapper2.getData();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
