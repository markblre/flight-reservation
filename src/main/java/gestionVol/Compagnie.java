package gestionVol;


import java.util.HashSet;
import java.util.Set;

/**
 * Classe representant une compagnie
 */
public class Compagnie {

    /**
     * Le nom de la compagnie
     */
    private String nom;

    /**
     * La liste des vols de la compagnie
     */
    private final Set<Vol> vols = new HashSet<>();

    /**
     * Constructeur de la classe Compagnie
     *
     * @param nom le nom de la compagnie
     * @exception IllegalArgumentException si le nom est null
     */
    public Compagnie(String nom) {
        if (nom == null) {
            throw new IllegalArgumentException("nom cannot be null");
        }
        this.nom = nom;
    }

    /**
     * Retourne le nom de la compagnie
     *
     * @return le nom de la compagnie
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la compagnie
     *
     * @param nom le nouveau nom de la compagnie
     * @exception IllegalArgumentException si le nom est null
     */
    public void setNom(String nom) {
        if(nom == null){
            throw new IllegalArgumentException("nom cannot be null");
        }
        this.nom = nom;
    }

    /**
     * Retourne la liste des vols de la compagnie
     *
     * @return la liste des vols de la compagnie
     */
    public Set<Vol> getVols() {
        return vols;
    }

    /**
     * Ajoute un vol à la liste des vols de la compagnie
     *
     * @param vol le vol à ajouter
     * @exception IllegalArgumentException si le vol est null
     */
    protected void addVolWithoutBidirectional(Vol vol){
        this.vols.add(vol);
    }

    /**
     * Supprime un vol de la liste des vols de la compagnie
     *
     * @param vol le vol à supprimer
     */
    protected void removeVolWithoutBidirectional(Vol vol){
        this.vols.remove(vol);
    }
}
