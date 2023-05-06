package aeroport;

import java.util.Map;

public class Aeroport {

    private String nom;

    private final Ville ville;

    private final Map<Ville, Integer> villesDesservies = new java.util.HashMap<>(); // Integer = nombre de vol vers la ville

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

    public Map<Ville, Integer> getVillesDesservies() {
        return villesDesservies;
    }

    public void addVolVers(Ville ville) {
        if (this.villesDesservies.containsKey(ville)) {
            this.villesDesservies.put(ville, villesDesservies.get(ville) + 1);
        } else {
            this.villesDesservies.put(ville, 1);
        }
        ville.addVolDepuisWithoutBidirectional(this);
    }

    public void removeVolVers(Ville ville) {
        if (this.villesDesservies.containsKey(ville)) {
            if (this.villesDesservies.get(ville) == 1) {
                this.villesDesservies.remove(ville);
            } else {
                this.villesDesservies.put(ville, villesDesservies.get(ville) - 1);
            }
            ville.removeVolDepuisWithoutBidirectional(this);
        }
    }
}
