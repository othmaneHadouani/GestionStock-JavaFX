package fournisseur.view;


public class menu extends personne.view.menu {


    public menu(double spacing) {
        super(spacing);
        lister.setOnMouseClicked(event -> {
            Container.getContainer().setCenter(ListFournisseur.getListFournisseur());
        });
        ajout.setOnMouseClicked(event -> {
            Ajout.getAjout().vider();
            Ajout.getAjout().changeToAjouter();
            Container.getContainer().setCenter(Ajout.getAjout());
        });


    }
}
