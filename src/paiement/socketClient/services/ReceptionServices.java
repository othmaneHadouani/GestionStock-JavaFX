package paiement.socketClient.services;


import paiement.socketClient.data.Wrapper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Collection;

public class ReceptionServices implements Runnable {

    private ObjectInputStream inputStream;
    private Wrapper wrapper;

    public ReceptionServices(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                wrapper = (Wrapper) inputStream.readObject();
                if (wrapper != null) {
                    switch (wrapper.getActions()) {
                        case Debiter:
//                            System.out.println(managerUser);
//                            managerUser.addUser(wrapper);
                            break;
                        case TestSolde:
//                            managerUser.setUsers((Collection<User>) wrapper.getData());
                            System.out.println(wrapper.getData());
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
               /* try {
                    inputStream.close();
                    break;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

}
