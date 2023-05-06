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

    public void removeAeroport(Aeroport aeroport) {
        if (!aeroport.getVillesDesservies().isEmpty()) {
            throw new IllegalStateException("L'aeroport est toujours desservi par des vols.");
        }
        this.aeroports.remove(aeroport);
        // inutile de modifier l'attribut ville de l'aeroport car il n'existe plus
    }

    protected void addAeroportWithoutBidirectional(Aeroport aeroport) {
        this.aeroports.add(aeroport);
    }

    public Map<Aeroport, Integer> getAeroportsDesservant() {
        return aeroportsDesservant;
    }

    protected void addVolDepuisWithoutBidirectional(Aeroport aeroport) {
        if (this.aeroportsDesservant.containsKey(aeroport)) {
            this.aeroportsDesservant.put(aeroport, this.aeroportsDesservant.get(aeroport) + 1);
        } else {
            this.aeroportsDesservant.put(aeroport, 1);
        }
    }

    protected void removeVolDepuisWithoutBidirectional(Aeroport aeroport) {
        if (this.aeroportsDesservant.containsKey(aeroport)) {
            if (this.aeroportsDesservant.get(aeroport) == 1) {
                this.aeroportsDesservant.remove(aeroport);
            } else {
                this.aeroportsDesservant.put(aeroport, this.aeroportsDesservant.get(aeroport) - 1);
            }
        }
    }
}
