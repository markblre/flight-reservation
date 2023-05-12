package gestionVol;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Classe representant un vol
 */
public class Vol {

    /**
     * Le numéro du vol
     */
    private String numero;

    /**
     * La compagnie du vol
     */
    private Compagnie compagnie;

    /**
     * L'aéroport de depart du vol
     */
    private Aeroport depart;

    /**
     * L'aéroport d'arrivée du vol
     */
    private Aeroport arrivee;

    /**
     * La date de départ du vol
     */
    private ZonedDateTime dateDepart;

    /**
     * La date d'arrivée du vol
     */
    private ZonedDateTime dateArrivee;

    /**
     * Si le vol est ouvert à la réservation ou non
     */
    private boolean reservationOuverte;

    /**
     * Constructeur de la classe Vol
     *
     * @param numero le numéro du vol
     * @param compagnie la compagnie du vol
     * @param depart l'aéroport de depart du vol
     * @param arrivee l'aéroport d'arrivée du vol
     * @param dateDepart la date de depart du vol
     * @param dateArrivee la date d'arrivée du vol
     * @exception IllegalArgumentException si le numero, la compagnie, l'aéroport de depart, l'aéroport d'arrivée, la date de depart ou la date d'arrivée est null
     * @exception IllegalArgumentException si la date de depart est après la date d'arrivée
     */
    public Vol(String numero, Compagnie compagnie, Aeroport depart, Aeroport arrivee, ZonedDateTime dateDepart, ZonedDateTime dateArrivee) {
        if(numero == null || compagnie == null || depart == null || arrivee == null || dateDepart == null || dateArrivee == null) {
            throw new IllegalArgumentException("numero, compagnie, depart, arrivée, dateDepart and dateArrivee cannot be null");
        }
        if(dateDepart.isAfter(dateArrivee) || dateDepart.isEqual(dateArrivee)) {
            throw new IllegalArgumentException("dateDepart doit être avant dateArrivee");
        }

        this.numero = numero;

        this.compagnie = compagnie;
        this.compagnie.addVolWithoutBidirectional(this);

        this.depart = depart;
        this.arrivee = arrivee;
        this.depart.addVolAuDepartWithoutBidirectional(this);
        this.arrivee.addVolAArriveeWithoutBidirectional(this);

        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;

        this.reservationOuverte = false;
    }

    /**
     * Retourne le numéro du vol
     *
     * @return le numéro du vol
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Modifie le numero du vol
     *
     * @param numero le nouveau numero du vol
     * @exception IllegalArgumentException si le numero est null
     */
    public void setNumero(String numero) {
        if(numero == null) {
            throw new IllegalArgumentException("numero cannot be null");
        }
        this.numero = numero;
    }

    /**
     * Retourne la compagnie du vol
     *
     * @return la compagnie du vol
     */
    public Compagnie getCompagnie() {
        return compagnie;
    }

    /**
     * Modifie la compagnie du vol et met à jour l'ancienne compagnie et la nouvelle
     *
     * @param compagnie la nouvelle compagnie du vol
     * @exception IllegalArgumentException si la compagnie est null
     */
    public void setCompagnie(Compagnie compagnie) {
        if(compagnie == null) {
            throw new IllegalArgumentException("compagnie cannot be null");
        }
        this.compagnie.removeVolWithoutBidirectional(this);
        compagnie.addVolWithoutBidirectional(this);
        this.compagnie = compagnie;
    }

    /**
     * Retourne l'aéroport de départ du vol
     *
     * @return l'aéroport de départ du vol
     */
    public Aeroport getDepart() {
        return depart;
    }

    /**
     * Modifie l'aéroport de départ du vol et met à jour l'ancien aéroport de depart et le nouveau
     *
     * @param depart le nouvel aéroport de départ du vol
     * @exception IllegalArgumentException si l'aeroport de depart est null
     */
    public void setDepart(Aeroport depart) {
        if(depart == null) {
            throw new IllegalArgumentException("depart cannot be null");
        }
        this.depart.removeVolAuDepartWithoutBidirectional(this);
        this.depart = depart;
        this.depart.addVolAuDepartWithoutBidirectional(this);
    }

    /**
     * Retourne l'aéroport d'arrivée du vol
     *
     * @return l'aéroport d'arrivée du vol
     */
    public Aeroport getArrivee() {
        return arrivee;
    }

    /**
     * Modifie l'aeroport d'arrivee du vol et met à jour l'ancien aeroport d'arrivee et le nouveau
     *
     * @param arrivee le nouvel aeroport d'arrivee du vol
     * @exception IllegalArgumentException si l'aeroport d'arrivee est null
     */
    public void setArrivee(Aeroport arrivee) {
        if(arrivee == null) {
            throw new IllegalArgumentException("arrivee cannot be null");
        }
        this.arrivee.removeVolAArriveeWithoutBidirectional(this);
        this.arrivee = arrivee;
        this.arrivee.addVolAArriveeWithoutBidirectional(this);
    }

    /**
     * Retourne la date de départ du vol
     *
     * @return la date de départ du vol
     */
    public ZonedDateTime getDateDepart() {
        return dateDepart;
    }

    /**
     * Modifie la date de depart du vol
     *
     * @param dateDepart la nouvelle date de depart du vol
     * @exception IllegalArgumentException si la date de depart est null
     * @exception IllegalArgumentException si la date de depart est après la date d'arrivee
     */
    public void setDateDepart(ZonedDateTime dateDepart) {
        if(dateDepart == null) {
            throw new IllegalArgumentException("dateDepart cannot be null");
        }
        if(dateDepart.isAfter(this.dateArrivee) || dateDepart.isEqual(this.dateArrivee)) {
            throw new IllegalArgumentException("dateDepart doit être avant dateArrivee");
        }
        this.dateDepart = dateDepart;
    }

    /**
     * Retourne la date d'arrivee du vol
     *
     * @return la date d'arrivee du vol
     */
    public ZonedDateTime getDateArrivee() {
        return dateArrivee;
    }

    /**
     * Modifie la date d'arrivee du vol
     *
     * @param dateArrivee la nouvelle date d'arrivee du vol
     * @exception IllegalArgumentException si la date d'arrivee est null
     * @exception IllegalArgumentException si la date de depart est après la date d'arrivee
     */
    public void setDateArrivee(ZonedDateTime dateArrivee) {
        if(dateArrivee == null) {
            throw new IllegalArgumentException("dateArrivee cannot be null");
        }
        if(dateArrivee.isBefore(this.dateDepart) || dateArrivee.isEqual(this.dateDepart)) {
            throw new IllegalArgumentException("dateArrivee doit être après dateDepart");
        }
        this.dateArrivee = dateArrivee;
    }

    /**
     * Retourne la durée du vol
     *
     * @return la durée du vol
     */
    public Duration obtenirDuree() {
        return Duration.between(this.dateDepart, this.dateArrivee);
    }

    /**
     * Retourne si le vol est ouvert à la reservation ou non
     *
     * @return true si le vol est ouvert à la reservation, false sinon
     */
    public boolean isReservationOuverte() {
        return reservationOuverte;
    }

    /**
     * Ouvre le vol à la réservation
     *
     * @exception IllegalStateException si le vol n'a pas de compagnie, d'aéroport de depart ou d'aéroport d'arrivée
     */
    public void ouvrir() {
        if(this.compagnie == null || this.depart == null || this.arrivee == null) {
            throw new IllegalStateException("Le vol doit avoir une compagnie, un aeroport de depart et un aeroport d'arrivee pour être ouvert à la reservation");
        }
        this.reservationOuverte = true;
    }

    /**
     * Ferme le vol à la réservation
     */
    public void fermer() {
        this.reservationOuverte = false;
    }

    /**
     * Supprime le vol de la compagnie, de l'aéroport de départ et de l'aéroport d'arrivée
     */
    public void removeVol() {
        this.reservationOuverte = false;
        this.compagnie.removeVolWithoutBidirectional(this);
        this.compagnie = null;
        this.depart.removeVolAuDepartWithoutBidirectional(this);
        this.depart = null;
        this.arrivee.removeVolAArriveeWithoutBidirectional(this);
        this.arrivee = null;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return ((Vol) obj).getNumero().equals(this.numero);
        } catch (Exception e){
            return false;
        }
    }
}
