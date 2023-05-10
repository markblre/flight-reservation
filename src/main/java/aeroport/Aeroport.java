package aeroport;

import java.util.Map;

/**
 * Classe representant un aeroport
 */
public class Aeroport {

    /**
     * Le nom de l'aeroport
     */
    private String nom;

    /**
     * La ville de l'aeroport
     */
    private final Ville ville;

    /**
     * La liste des villes desservies par l'aeroport avec le nombre de vol
     */
    private final Map<Ville, Integer> villesDesservies = new java.util.HashMap<>(); // Integer = nombre de vol vers la ville

    /**
     * Constructeur de la classe Aeroport
     *
     * @param nom le nom de l'aeroport
     * @param ville la ville de l'aeroport
     * @exception IllegalArgumentException si le nom ou la ville est null
     */
    public Aeroport(String nom, Ville ville) {
        if (nom == null || ville == null) {
            throw new IllegalArgumentException("nom and ville cannot be null");
        }

        this.nom = nom;
        this.ville = ville;

        ville.addAeroportWithoutBidirectional(this);
    }

    /**
     * Retourne le nom de l'aeroport
     *
     * @return le nom de l'aeroport
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de l'aeroport
     *
     * @param nom le nouveau nom de l'aeroport
     * @exception IllegalArgumentException si le nom est null
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la ville de l'aeroport
     *
     * @return la ville de l'aeroport
     */
    public Ville getVille() {
        return ville;
    }

    /**
     * Retourne la liste des villes desservies par l'aeroport
     *
     * @return la liste des villes desservies par l'aeroport
     */
    public Map<Ville, Integer> getVillesDesservies() {
        return villesDesservies;
    }

    /**
     * Comptabilise un vol de plus de l'aeroport vers la ville
     *
     * @param ville la ville d'arrivée du vol
     * @exception IllegalArgumentException si la ville est null
     */
    protected void addVolVers(Ville ville) {
        if (this.villesDesservies.containsKey(ville)) {
            this.villesDesservies.put(ville, villesDesservies.get(ville) + 1);
        } else {
            this.villesDesservies.put(ville, 1);
        }
        ville.addVolDepuisWithoutBidirectional(this);
    }

    /**
     * Comptabilise un vol de moins de l'aeroport vers la ville
     *
     * @param ville la ville d'arrivée du vol
     * @exception IllegalArgumentException si la ville est null
     */
    protected void removeVolVers(Ville ville) {
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
