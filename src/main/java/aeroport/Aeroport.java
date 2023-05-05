package aeroport;

public class Aeroport {

    private String nom;

    private final Ville ville;

    public Aeroport(String nom, Ville ville) {
        if (nom == null || ville == null) {
            throw new IllegalArgumentException("nom and ville cannot be null");
        }

        this.nom = nom;
        this.ville = ville;

        ville.addAeroportWithoutBidirectional(this);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Ville getVille() {
        return ville;
    }
}
