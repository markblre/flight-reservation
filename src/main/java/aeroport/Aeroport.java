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
     * La liste des vols au depart de l'aeroport
     */
    private final Set<Vol> volsAuDepart = new HashSet<>();

    /**
     * La liste des vols a l'arrivee de l'aeroport
     */
    private final Set<Vol> volsAArrivee = new HashSet<>();

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
     * Retourne la liste des vols au départ de l'aeroport
     *
     * @return la liste des vols au départ de l'aeroport
     */
    public Set<Vol> getVolsAuDepart() {
        return volsAuDepart;
    }

    /**
     * Ajoute un vol au départ de l'aeroport
     *
     * @param vol le vol à ajouter aux vols au départ de l'aeroport
     */
    protected void addVolAuDepartWithoutBidirectional(Vol vol) {
        if (vol == null) {
            throw new IllegalArgumentException("vol ne peut pas être null");
        }

        this.volsAuDepart.add(vol);
    }

    /**
     * Supprime un vol au départ de l'aéroport
     *
     * @param vol le vol à supprimer des vols au départ de l'aéroport
     */
    protected void removeVolAuDepartWithoutBidirectional(Vol vol) {
        if (vol == null) {
            throw new IllegalArgumentException("vol ne peut pas être null");
        }

        this.volsAuDepart.remove(vol);
    }

    /**
     * Retourne la liste des vols à l'arrivée de l'aéroport
     *
     * @return la liste des vols à l'arrivée de l'aéroport
     */
    public Set<Vol> getVolsAArrivee() {
        return volsAArrivee;
    }

    /**
     * Ajoute un vol à l'arrivée de l'aéroport
     *
     * @param vol le vol à ajouter aux vols à l'arrivée de l'aéroport
     */
    protected void addVolAArriveeWithoutBidirectional(Vol vol) {
        if (vol == null) {
            throw new IllegalArgumentException("vol ne peut pas être null");
        }

        this.volsAArrivee.add(vol);
    }

    /**
     * Supprime un vol à l'arrivée de l'aeroport
     *
     * @param vol le vol à supprimer des vols à l'arrivée de l'aeroport
     */
    protected void removeVolAArriveeWithoutBidirectional(Vol vol) {
        if (vol == null) {
            throw new IllegalArgumentException("vol ne peut pas être null");
        }

        this.volsAArrivee.remove(vol);
    }

    /**
     * Supprime l'aeroport et met à jour la ville
     *
     * @exception IllegalStateException si des vols au départ ou à l'arrivée sont encore présents
     */
    public void removeAeroport() {
        if(!this.volsAuDepart.isEmpty() || !this.volsAArrivee.isEmpty()) {
            throw new IllegalStateException("L'aeroport ne peut pas être supprimé car des vols au départ ou à l'arrivée sont encore présents");
        }

        this.ville.removeAeroportWithoutBidirectional(this);
        for(Ville ville : this.villesDesservies) {
            ville.removeAeroportDesservantWithoutBidirectional(this);
            this.villesDesservies.remove(ville);
        }
        this.ville = null;
    }
}
