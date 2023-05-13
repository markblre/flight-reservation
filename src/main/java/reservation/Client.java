package reservation;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Classe représentant un client
 */
public class Client {

    /**
     * Le nom du client
     */
    private String nom;

    /**
     * La reference unique du client
     */
    private final UUID reference;

    /**
     * Les informations de paiement du client
     */
    private String paiement;

    /**
     * Le contact du client
     */
    private String contact;

    private final Set<Reservation> reservations = new HashSet<>();

    /**
     * Constructeur de la classe Client
     *
     * @param nom le nom du client
     * @exception IllegalArgumentException si le nom, les informations de paiement ou le contact sont null
     */
    public Client(String nom, String paiement, String contact) {
        if(nom == null || paiement == null || contact == null){
            throw new IllegalArgumentException("nom, paiement and contact cannot be null");
        }

        this.reference = UUID.randomUUID();

        this.nom = nom;
        this.paiement = paiement;
        this.contact = contact;
    }

    /**
     * Retourne le nom du client
     *
     * @return le nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom du client
     *
     * @param nom le nouveau nom du client
     * @exception IllegalArgumentException si le nom est null
     */
    public String setNom(String nom) {
        if(nom == null){
            throw new IllegalArgumentException("nom cannot be null");
        }

        return this.nom = nom;
    }

    /**
     * Retourne la reference du client
     *
     * @return la reference du client
     */
    public String getReference() {
        return reference.toString();
    }

    /**
     * Retourne les informations de paiement du client
     *
     * @return les informations de paiement du client
     */
    public String getPaiement() {
        return paiement;
    }

    /**
     * Modifie les informations de paiement du client
     *
     * @param paiement les nouvelles informations de paiement du client
     * @exception IllegalArgumentException si les informations de paiement sont null
     */
    public String setPaiement(String paiement) {
        if(paiement == null){
            throw new IllegalArgumentException("paiement cannot be null");
        }

        return this.paiement = paiement;
    }

    /**
     * Retourne le contact du client
     *
     * @return le contact du client
     */
    public String getContact() {
        return contact;
    }

    /**
     * Modifie le contact du client
     *
     * @param contact le nouveau contact du client
     * @exception IllegalArgumentException si le contact est null
     */
    public String setContact(String contact) {
        if(contact == null){
            throw new IllegalArgumentException("contact cannot be null");
        }

        return this.contact = contact;
    }

    /**
     * Retourne les réservations du client
     *
     * @return les réservations du client
     */
    public Set<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Ajoute une réservation au client
     *
     * @param reservation la réservation à ajouter
     * @exception IllegalArgumentException si la reservation est null
     */
    protected void addReservationWithoutBidirectional(Reservation reservation) {
        if(reservation == null){
            throw new IllegalArgumentException("reservation cannot be null");
        }

        reservations.add(reservation);
    }

    /**
     * Débite le compte bancaire du client
     *
     * @param montant le montant à débiter
     */
    protected void debiter(double montant) {
        // Débiter le compte bancaire du client à partir des informations de paiement
    }

    /**
     * Rembourse le compte bancaire du client
     *
     * @param montant le montant à rembourser
     */
    protected void rembourser(double montant) {
        // Rembourser le compte bancaire du client à partir des informations de paiement
    }
}
