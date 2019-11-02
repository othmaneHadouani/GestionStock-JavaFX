package paiement.socketClient.data;

import java.io.Serializable;

public class  Compte implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;

    private String numeroCarte;
    private String code;
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "Compte{" +
                ", numeroCarte='" + numeroCarte + '\'' +
                ", code='" + code + '\'' +
                ", client=" + client +
                '}';
    }
}
