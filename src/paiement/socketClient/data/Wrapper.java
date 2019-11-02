package paiement.socketClient.data;

import paiement.socketClient.actions.Actions;

import java.io.Serializable;

public class Wrapper implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private Actions actions;
    private Object data;
    private double aPyer;

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public double getaPyer() {
        return aPyer;
    }

    public void setaPyer(double aPyer) {
        this.aPyer = aPyer;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "actions=" + actions +
                ", data=" + data +
                ", aPyer=" + aPyer +
                '}';
    }
}
