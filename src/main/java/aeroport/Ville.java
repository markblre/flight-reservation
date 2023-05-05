package aeroport;

import java.util.ArrayList;
import java.util.Collection;

public class Ville {
    private String nom;

    private final Collection<Aeroport> aeroports = new ArrayList<>();

    public Ville(String nom) {
        if (nom == null) {
            throw new IllegalArgumentException("nom cannot be null");
        }

        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Aeroport> getAeroports() {
        return aeroports;
    }

    // Si un aeroport et definitivement fermé. (inutile de modifier l'attribut ville de l'aeroport car il n'existe plus)
    public void removeAeroport(Aeroport aeroport) {
        this.aeroports.remove(aeroport);
    }

    public void addAeroportWithoutBidirectional(Aeroport aeroport) {
        this.aeroports.add(aeroport);
    }
}
