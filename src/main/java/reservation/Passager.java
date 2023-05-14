package reservation;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant un client
 */
public class Passager {

    /**
     * Le nom du passager
     */
    private String nom;

    /**
     * Le prénom du passager
     */
    private String prenom;

    private final Set<Reservation> reservations = new HashSet<>();

    /**
     * Constructeur de la classe Passager
     *
     * @param nom le nom du passager
     * @param prenom le prénom du passager
     * @exception IllegalArgumentException si le nom ou le prénom sont null
     */
    public Passager(String nom, String prenom) {
        if(nom == null || prenom == null){
            throw new IllegalArgumentException("nom and prenom cannot be null");
        }

        this.nom = nom;
        this.prenom = prenom;
    }

    /**
     * Retourne le nom du passager
     *
     * @return le nom du passager
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom du passager
     *
     * @param nom le nouveau nom du passager
     * @exception IllegalArgumentException si le nom est null
     */
    public void setNom(String nom) {
        if(nom == null){
            throw new IllegalArgumentException("nom cannot be null");
        }

        this.nom = nom;
    }

    /**
     * Retourne le prénom du passager
     *
     * @return le prénom du passager
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Modifie le prénom du passager
     *
     * @param prenom le nouveau prénom du passager
     * @exception IllegalArgumentException si le prénom est null
     */
    public void setPrenom(String prenom) {
        if (prenom == null) {
            throw new IllegalArgumentException("prenom cannot be null");
        }

        this.prenom = prenom;
    }

    /**
     * Retourne la liste des réservations du passager
     *
     * @return la liste des réservations du passager
     */
    public Set<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Ajoute une réservation à la liste des réservations du passager
     *
     * @param reservation la réservation à ajouter
     * @exception IllegalArgumentException si la réservation est null
     */
    protected void addReservationWithoutBidirectional(Reservation reservation) {
        if(reservation == null) {
            throw new IllegalArgumentException("reservation cannot be null");
        }

        this.reservations.add(reservation);
    }

    /**
     * Supprime une réservation de la liste des réservations du passager
     *
     * @param reservation la réservation à supprimer
     * @exception IllegalArgumentException si la réservation est null
     */
    protected void removeReservationWithoutBidirectional(Reservation reservation) {
        if(reservation == null) {
            throw new IllegalArgumentException("reservation cannot be null");
        }

        this.reservations.remove(reservation);
    }


}
