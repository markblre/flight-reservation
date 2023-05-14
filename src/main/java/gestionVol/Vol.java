package gestionVol;

import reservation.Reservation;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Classe représentant un vol
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
     * Les escales du vol dans l'ordre
     */
    private final TreeSet<Escale> escales = new TreeSet<>();

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
     * Le nombre de places disponibles
     */
    private int placesDisponibles;

    /**
     * Le prix d'une place
     */
    private double prix;

    /**
     * Les réservations du vol
     */
    private final Set<Reservation> reservations = new HashSet<>();

    /**
     * Constructeur de la classe Vol
     *
     * @param numero le numéro du vol
     * @param compagnie la compagnie du vol
     * @param depart l'aéroport de depart du vol
     * @param arrivee l'aéroport d'arrivée du vol
     * @param dateDepart la date de depart du vol
     * @param dateArrivee la date d'arrivée du vol
     * @param placesDisponibles le nombre de places disponibles
     * @param prix le prix d'une place
     * @exception IllegalArgumentException si le numero, la compagnie, l'aéroport de depart, l'aéroport d'arrivée, la date de depart ou la date d'arrivée est null
     * @exception IllegalArgumentException si la date de depart est après la date d'arrivée
     * @exception IllegalArgumentException si le nombre de places disponibles est négatif
     * @exception IllegalArgumentException si le prix est négatif
     */
    public Vol(String numero, Compagnie compagnie, Aeroport depart, Aeroport arrivee, ZonedDateTime dateDepart, ZonedDateTime dateArrivee, int placesDisponibles, double prix) {
        if(numero == null || compagnie == null || depart == null || arrivee == null || dateDepart == null || dateArrivee == null) {
            throw new IllegalArgumentException("numero, compagnie, depart, arrivée, dateDepart and dateArrivee cannot be null");
        }
        if(dateDepart.isAfter(dateArrivee) || dateDepart.isEqual(dateArrivee)) {
            throw new IllegalArgumentException("dateDepart doit être avant dateArrivee");
        }
        if(placesDisponibles < 0) {
            throw new IllegalArgumentException("placesDisponibles doit être positif");
        }
        if(prix < 0) {
            throw new IllegalArgumentException("prix doit être positif");
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

        this.placesDisponibles = placesDisponibles;
        this.prix = prix;
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
     * Retourne les escales du vol
     *
     * @return les escales du vol
     */
    public TreeSet<Escale> getEscales() {
        return escales;
    }

    /**
     * Ajoute une escale au vol
     *
     * @param aeroport l'aéroport de l'escale
     * @param heureArrivee l'heure d'arrivée de l'escale
     * @param heureDepart l'heure de départ de l'escale
     * @exception IllegalArgumentException si l'escale n'est pas compatible avec une autre escale ou l'arrivée du vol
     *
     * @return l'escale ajoutée
     */
    public Escale addEscale(Aeroport aeroport, ZonedDateTime heureArrivee, ZonedDateTime heureDepart) {
        Escale nouvelleEscale = new Escale(this, aeroport, heureArrivee, heureDepart);
        this.escales.add(nouvelleEscale);

        // On vérifie si l'escale est possible, sinon on l'enlève
        Escale escaleSuivante = this.escales.higher(nouvelleEscale);

        if(escaleSuivante != null) {
            if(escaleSuivante.getDateArrivee().isBefore(heureDepart) || escaleSuivante.getDateArrivee().isEqual(heureDepart)) {
                nouvelleEscale.removeEscale();
                this.escales.remove(nouvelleEscale);
                throw new IllegalArgumentException("L'escale doit se terminer avant la prochaine escale");
            }
        } else if (this.dateArrivee.isBefore(heureDepart) || this.dateArrivee.isEqual(heureDepart)) {
            nouvelleEscale.removeEscale();
            this.escales.remove(nouvelleEscale);
            throw new IllegalArgumentException("L'escale doit se terminer avant la date d'arrivee");
        }

        return nouvelleEscale;
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
     * Retourne le nombre de places disponibles du vol
     *
     * @return le nombre de places disponibles du vol
     */
    public int getPlacesDisponibles() {
        return placesDisponibles;
    }

    /**
     * Modifie le nombre de places disponibles du vol
     *
     * @param placesDisponibles le nouveau nombre de places disponibles du vol
     * @exception IllegalArgumentException si le nombre de places disponibles est négatif
     */
    public void setPlacesDisponibles(int placesDisponibles) {
        if(placesDisponibles < 0) {
            throw new IllegalArgumentException("placesDisponibles doit être positif");
        }
        this.placesDisponibles = placesDisponibles;
    }

    /**
     * Retourne le prix d'une place du vol
     *
     * @return le prix d'une place du vol
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Modifie le prix d'une place du vol
     *
     * @param prix le nouveau prix d'une place du vol
     * @exception IllegalArgumentException si le prix est négatif
     */
    public void setPrix(double prix) {
        if(prix < 0) {
            throw new IllegalArgumentException("prix doit être positif");
        }

        this.prix = prix;
    }

    /**
     * Retourne les réservations du vol
     *
     * @return les réservations du vol
     */
    public Set<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Ajoute une réservation au vol
     *
     * @param reservation la réservation à ajouter au vol
     * @exception IllegalArgumentException si la réservation est null
     * @exception IllegalArgumentException si la réservation n'est pas compatible avec le vol
     */
    public void addReservationWithoutBidirectional(Reservation reservation) {
        if(reservation == null) {
            throw new IllegalArgumentException("reservation cannot be null");
        }
        if(!reservation.getVol().equals(this)) {
            throw new IllegalArgumentException("La reservation doit être compatible avec le vol");
        }

        this.reservations.add(reservation);
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
        for (Reservation reservation : this.reservations) {
            reservation.annulerParCompagnie();
        }
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
