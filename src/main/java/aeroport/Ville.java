package aeroport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Ville {
    private String nom;

    private final Collection<Aeroport> aeroports = new ArrayList<>();

    private final Map<Aeroport, Integer> aeroportsDesservant = new java.util.HashMap<>(); // Integer = nombre de vol provenant de l'aeroport

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

    public Map<Aeroport, Integer> getAeroportsDesservant() {
        return aeroportsDesservant;
    }

    public void addVolDepuisWithoutBidirectional(Aeroport aeroport) {
        if (this.aeroportsDesservant.containsKey(aeroport)) {
            this.aeroportsDesservant.put(aeroport, this.aeroportsDesservant.get(aeroport) + 1);
        } else {
            this.aeroportsDesservant.put(aeroport, 1);
        }
    }

    public void removeVolDepuisWithoutBidirectional(Aeroport aeroport) {
        if (this.aeroportsDesservant.containsKey(aeroport)) {
            if (this.aeroportsDesservant.get(aeroport) == 1) {
                this.aeroportsDesservant.remove(aeroport);
            } else {
                this.aeroportsDesservant.put(aeroport, this.aeroportsDesservant.get(aeroport) - 1);
            }
        }
    }
}
