package reservation;

import gestionVol.Vol;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Classe représentant une réservation
 */
public class Reservation {

    /**
     * L'etat de la réservation
     */
    private EtatReservation etat;

    /**
     * L'identifiant unique de la réservation
     */
    private final UUID numero;

    /**
     * La date de la réservation
     */
    private final ZonedDateTime date;

    /**
     * Le client ayant effectue la réservation
     */
    private final Client client;

    /**
     * Le vol reservé
     */
    private final Vol vol;

    /**
     * Les passagers de la réservation
     */
    private final Set<Passager> passagers = new HashSet<>();

    /**
     * Constructeur de la classe Reservation
     *
     * @param client le client ayant effectué la réservation
     * @param vol le vol réservé
     * @exception IllegalArgumentException si le client ou le vol sont null
     * @exception IllegalStateException si les réservations pour ce vol sont fermées
     */
    public Reservation(Client client, Vol vol) {
        if(!vol.isReservationOuverte()) {
            throw new IllegalStateException("Les réservations pour ce vol sont actuellement fermées");
        }

        this.etat = EtatReservation.EN_ATTENTE;

        this.numero = UUID.randomUUID();

        this.client = client;
        this.client.addReservationWithoutBidirectional(this);

        this.vol = vol;
        this.vol.addReservationWithoutBidirectional(this);

        this.date = ZonedDateTime.now();
    }

    /**
     * Retourne l'état de la réservation
     *
     * @return l'état de la réservation
     */
    public EtatReservation getEtat() {
        return etat;
    }

    /**
     * Retourne l'identifiant unique de la réservation
     *
     * @return l'identifiant unique de la réservation
     */
    public UUID getNumero() {
        return numero;
    }

    /**
     * Retourne la date de la réservation
     *
     * @return la date de la réservation
     */
    public ZonedDateTime getDate() {
        return date;
    }

    /**
     * Retourne le client ayant effectué la réservation
     *
     * @return le client ayant effectué la réservation
     */
    public Client getClient() {
        return client;
    }

    /**
     * Retourne le vol concerné par la réservation
     *
     * @return le vol concerné par la réservation
     */
    public Vol getVol() {
        return vol;
    }

    /**
     * Retourne les passagers de la réservation
     *
     * @return les passagers de la réservation
     */
    public Set<Passager> getPassagers() {
        return passagers;
    }

    /**
     * Ajoute un ou plusieurs passagers à la réservation et met à jour les réservations de chaque passager
     *
     * @param passagers le ou les passagers à ajouter
     * @exception IllegalArgumentException si il n'y a aucun passager à ajouter
     * @exception IllegalStateException si la réservation est payée, confirmée ou annulée
     */
    public void addPassagers(Passager... passagers) {
        if(passagers == null){
            throw new IllegalArgumentException("passagers cannot be null");
        }

        if (this.etat == EtatReservation.EN_ATTENTE) {
            for (Passager passager : passagers) {
                this.passagers.add(passager);
                passager.addReservationWithoutBidirectional(this);
            }
        } else {
            throw new IllegalStateException("Impossible d'ajouter de nouveaux passagers : la réservation est payée, confirmée ou annulée");
        }
    }

    /**
     * Supprime un passager de la réservation
     *
     * @param passager le passager à supprimer
     * @exception IllegalArgumentException si le passager est null
     * @exception IllegalStateException si la réservation est payée, confirmée ou annulée
     */
    public void removePassager(Passager passager) {
        if(passager == null){
            throw new IllegalArgumentException("passager cannot be null");
        }

        if (this.etat == EtatReservation.EN_ATTENTE) {
            this.passagers.remove(passager);
        } else {
            throw new IllegalStateException("Impossible de supprimer un passager : la réservation est payée, confirmée ou annulée");
        }
    }

    /**
     * Effectue le paiement de la réservation en vérifiant si le vol a assez de places disponibles pour tout les passagers
     *
     * @exception IllegalStateException si la réservation est déjà payée, confirmée ou annulée
     * @exception IllegalStateException si aucun passager n'a été ajouté
     * @exception IllegalStateException si le vol n'a pas assez de places disponibles
     */
    public void payer() {
        switch (this.etat) {
            case EN_ATTENTE -> {
                if (this.passagers.isEmpty()) {
                    throw new IllegalStateException("Impossible de payer la réservation : aucun passager n'a été ajouté");
                }

                if (this.vol.getPlacesDisponibles() < this.passagers.size()) {
                    throw new IllegalStateException("Impossible de payer la réservation : plus assez de places disponibles pour ce vol");
                }

                this.client.debiter(this.vol.getPrix() * this.passagers.size());
                this.etat = EtatReservation.PAYEE;
            }
            case PAYEE, CONFIRMEE -> throw new IllegalStateException("Impossible de payer la réservation : la réservation est déjà payée");
            case ANNULEE -> throw new IllegalStateException("Impossible de payer la réservation : la réservation est annulée");
        }
    }

    /**
     * Annule la réservation.
     * Si la réservation est payée mais non confirmé, le client est remboursé
     */
    public void annuler() {
        switch (this.etat) {
            case EN_ATTENTE, CONFIRMEE -> this.etat = EtatReservation.ANNULEE;
            case PAYEE -> {
                this.client.rembourser(this.vol.getPrix() * this.passagers.size());
                this.etat = EtatReservation.ANNULEE;
            }
        }
    }

    /**
     * À utiliser si le vol est annulé par la compagnie.
     * Tout les passagers ayant payé sont remboursés
     */
    public void annulerParCompagnie() {
        switch (this.etat) {
            case EN_ATTENTE -> this.etat = EtatReservation.ANNULEE;
            case PAYEE, CONFIRMEE -> {
                this.client.rembourser(this.vol.getPrix() * this.passagers.size());
                this.etat = EtatReservation.ANNULEE;
            }
        }
    }

    /**
     * Confirme la réservation
     *
     * @exception IllegalStateException si la réservation n'est pas payée
     * @exception IllegalStateException si la réservation est annulée
     */
    public void confirmer() {
        switch (this.etat) {
            case EN_ATTENTE -> throw new IllegalStateException("Impossible de confirmer la réservation : la réservation n'est pas encore payée");
            case PAYEE -> this.etat = EtatReservation.CONFIRMEE;
            case ANNULEE -> throw new IllegalStateException("Impossible de confirmer la réservation : la réservation est annulée");
        }
    }
}
