package aeroport;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe representant une ville
 */
public class Ville {
    /**
     * Le nom de la ville
     */
    private String nom;

    /**
     * La liste des aeroports de la ville
     */
    private final Set<Aeroport> aeroports = new HashSet<>();

    /**
     * La liste des aeroports qui desservent la ville
     */
    private final Set<Aeroport> aeroportsDesservant = new HashSet<>();

    /**
     * Constructeur de la classe Ville
     *
     * @param nom le nom de la ville
     * @exception IllegalArgumentException si le nom est null
     */
    public Ville(String nom) {
        if (nom == null) {
            throw new IllegalArgumentException("nom cannot be null");
        }

        this.nom = nom;
    }

    /**
     * Retourne le nom de la ville
     *
     * @return le nom de la ville
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la ville
     *
     * @param nom le nouveau nom de la ville
     * @exception IllegalArgumentException si le nom est null
     */
    public void setNom(String nom) {
        if(nom == null){
            throw new IllegalArgumentException("nom cannot be null");
        }
        this.nom = nom;
    }

    /**
     * Retourne la liste des aeroports de la ville
     *
     * @return la liste des aeroports de la ville
     */
    public Set<Aeroport> getAeroports() {
        return aeroports;
    }

    /**
     * Ajoute un aeroport à la liste des aeroports de la ville
     *
     * @param aeroport l'aeroport à ajouter
     */
    protected void addAeroportWithoutBidirectional(Aeroport aeroport) {
        this.aeroports.add(aeroport);
    }

    /**
     * Supprime l'aeroport de la ville
     *
     * @param aeroport l'aeroport à supprimer
     */
    protected void removeAeroportWithoutBidirectional(Aeroport aeroport) {
        this.aeroports.remove(aeroport);
    }

    /**
     * Retourne la liste des aeroports qui desservent la ville
     *
     * @return les aeroports qui desservent la ville
     */
    public Set<Aeroport> getAeroportsDesservant() {
        return aeroportsDesservant;
    }

    /**
     * Ajoute l'aeroport à la liste des aeroports qui desservent la ville
     *
     * @param aeroport l'aeroport à ajouter
     */
    protected void addAeroportDesservantWithoutBidirectional(Aeroport aeroport) {
        this.aeroportsDesservant.add(aeroport);
    }

    /**
     * Supprime l'aeroport de la liste des aeroports qui desservent la ville
     *
     * @param aeroport l'aeroport à supprimer
     */
    protected void removeAeroportDesservantWithoutBidirectional(Aeroport aeroport) {
        this.aeroportsDesservant.remove(aeroport);
    }
}
