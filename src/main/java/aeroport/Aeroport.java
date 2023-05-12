package aeroport;

import java.util.HashSet;
import java.util.Set;

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
    private Ville ville;

    /**
     * La liste des villes desservies par l'aeroport
     */
    private final Set<Ville> villesDesservies = new HashSet<>();

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
    public Set<Ville> getVillesDesservies() {
        return villesDesservies;
    }

    /**
     * Ajoute la ville aux villes desservies par l'aéroport et met à jour la ville
     *
     * @param ville la ville desservie par l'aéroport
     * @exception IllegalArgumentException si la ville est null
     */
    public void addVilleDesservie(Ville ville) {
        if (ville == null) {
            throw new IllegalArgumentException("ville ne peut pas être null");
        }

        this.villesDesservies.add(ville);
        ville.addAeroportDesservantWithoutBidirectional(this);
    }

    /**
     * Supprime la ville des villes desservies par l'aéroport et met à jour la ville
     *
     * @param ville la ville qui n'est plu desservie par l'aéroport
     * @exception IllegalArgumentException si la ville est null
     */
    public void RemoveVilleDesservie(Ville ville) {
        if (ville == null) {
            throw new IllegalArgumentException("ville ne peut pas être null");
        }

        this.villesDesservies.remove(ville);
        ville.removeAeroportDesservantWithoutBidirectional(this);
    }

    /**
     * Supprime l'aeroport et met à jour la ville
     */
    public void removeAeroport() {
        // TODO : Vérifier que plus aucun vol ne part ou n'arrive à cet aéroport
        this.ville.removeAeroportWithoutBidirectional(this);
        for(Ville ville : this.villesDesservies) {
            ville.removeAeroportDesservantWithoutBidirectional(this);
        }
        this.ville = null;
    }
}
