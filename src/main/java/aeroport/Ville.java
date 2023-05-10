package aeroport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
    private final Collection<Aeroport> aeroports = new ArrayList<>();

    /**
     * La liste des aeroports qui desservent la ville avec le nombre de vol
     */
    private final Map<Aeroport, Integer> aeroportsDesservant = new java.util.HashMap<>(); // Integer = nombre de vol provenant de l'aeroport

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
    public Collection<Aeroport> getAeroports() {
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
     * @param aeroport l'aeroport à ajouter
     * @exception IllegalArgumentException si l'aeroport est toujours desservi par des vols
     */
    public void removeAeroport(Aeroport aeroport) {
        if (!aeroport.getVillesDesservies().isEmpty()) {
            throw new IllegalStateException("L'aeroport est toujours desservi par des vols.");
        }
        this.aeroports.remove(aeroport);
        // inutile de modifier l'attribut ville de l'aeroport car un aeroport ne peut pas changer de ville (objet "abandonné")
    }

    /**
     * Retourne la liste des aeroports qui desservent la ville avec le nombre de vol
     *
     * @return une map qui contient les aeroports qui desservent la ville avec le nombre de vol
     */
    public Map<Aeroport, Integer> getAeroportsDesservant() {
        return aeroportsDesservant;
    }

    /**
     * Comptabilise un vol de plus de l'eroport vers la ville
     *
     * @param aeroport l'aeroport depuis lequel le vol est ajoute
     */
    protected void addVolDepuisWithoutBidirectional(Aeroport aeroport) {
        if (this.aeroportsDesservant.containsKey(aeroport)) {
            this.aeroportsDesservant.put(aeroport, this.aeroportsDesservant.get(aeroport) + 1);
        } else {
            this.aeroportsDesservant.put(aeroport, 1);
        }
    }

    /**
     * Comptabilise un vol de moins de l'aeroport vers la ville
     *
     * @param aeroport l'aeroport depuis lequel le vol est supprime
     */
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
