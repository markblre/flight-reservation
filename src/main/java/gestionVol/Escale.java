package gestionVol;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Classe representant une escale
 */
public class Escale implements Comparable<Escale> {

    /**
     *  Le vol concerné par l'escale
     */
    private Vol vol;

    /**
     * L'aéroport où le vol fait escale
     */
    private Aeroport aeroport;

    /**
     *  La date d'arrivée à l'escale
     */
    private ZonedDateTime dateArrivee;

    /**
     *  La date de départ de l'escale
     */
    private ZonedDateTime dateDepart;

    /**
     * Constructeur de la classe Escale
     *
     * @param vol le vol concerné par l'escale
     * @param aeroport l'aéroport où le vol fait escale
     * @param dateArrivee la date d'arrivée à l'escale
     * @param dateDepart la date de départ de l'escale
     * @exception IllegalArgumentException si le vol, l'aéroport, la date d'arrivée ou la date de départ est null
     * @exception IllegalArgumentException si la date d'arrivée est après la date de départ
     */
    protected Escale(Vol vol, Aeroport aeroport, ZonedDateTime dateArrivee, ZonedDateTime dateDepart) {
        if (vol == null || aeroport == null || dateArrivee == null || dateDepart == null) {
            throw new IllegalArgumentException("vol, aeroport, dateArrivee and dateDepart cannot be null");
        }

        if (dateArrivee.isAfter(dateDepart) || dateArrivee.isEqual(dateDepart)) {
            throw new IllegalArgumentException("dateArrivee doit être avant dateDepart");
        }

        this.vol = vol;

        this.aeroport = aeroport;
        this.aeroport.addEscaleWithoutBidirectional(this);

        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
    }

    /**
     * Retourne le vol concerné par l'escale
     *
     * @return le vol concerné par l'escale
     */
    public Vol getVol() {
        return vol;
    }

    /**
     * Retourne l'aéroport où le vol fait escale
     *
     * @return l'aéroport où le vol fait escale
     */
    public Aeroport getAeroport() {
        return aeroport;
    }

    /**
     * Modifie l'aéroport où le vol fait escale
     *
     * @param aeroport le nouvel aéroport où le vol fait escale
     * @exception IllegalArgumentException si l'aéroport est null
     */
    public void setAeroport(Aeroport aeroport) {
        if (aeroport == null) {
            throw new IllegalArgumentException("aeroport cannot be null");
        }

        this.aeroport.removeEscaleWithoutBidirectional(this);
        this.aeroport = aeroport;
        this.aeroport.addEscaleWithoutBidirectional(this);
    }

    /**
     * Retourne la date d'arrivée à l'escale
     *
     * @return la date d'arrivée à l'escale
     */
    public ZonedDateTime getDateArrivee() {
        return dateArrivee;
    }

    /**
     * Modifie la date d'arrivée à l'escale
     *
     * @param dateArrivee la nouvelle date d'arrivée à l'escale
     * @exception IllegalArgumentException si la date d'arrivée est après la date de départ
     * @exception IllegalArgumentException si la date d'arrivée est avant la date de départ de l'escale précédente
     * @exception IllegalArgumentException si la date d'arrivée est avant la date de départ du vol
     */
    public void setDateArrivee(ZonedDateTime dateArrivee) {
        if (dateArrivee == null) {
            throw new IllegalArgumentException("dateArrivee cannot be null");
        }

        if (dateArrivee.isAfter(this.dateDepart) || dateArrivee.isEqual(this.dateDepart)) {
            throw new IllegalArgumentException("dateArrivee doit être avant dateDepart");
        }

        Escale escalePrecedente = this.vol.getEscales().lower(this);

        if (escalePrecedente != null) {
            if (escalePrecedente.getDateDepart().isAfter(dateArrivee)
                    || (escalePrecedente.getDateDepart().isEqual(dateArrivee))) {
                throw new IllegalArgumentException("dateArrivee doit être après la date de départ de l'escale précédente");
            }
        } else if (this.vol.getDateDepart().isAfter(dateArrivee) || this.vol.getDateDepart().isEqual(dateArrivee)) {
            throw new IllegalArgumentException("dateArrivee doit être après la date de départ du vol");
        }

        this.dateArrivee = dateArrivee;
    }

    /**
     * Retourne la date de départ de l'escale
     *
     * @return la date de départ de l'escale
     */
    public ZonedDateTime getDateDepart() {
        return dateDepart;
    }

    /**
     * Modifie la date de départ de l'escale
     *
     * @param dateDepart la nouvelle date de départ de l'escale
     * @exception IllegalArgumentException si la date de départ est avant la date d'arrivée
     * @exception IllegalArgumentException si la date de départ est après la date d'arrivée à l'escale suivante
     * @exception IllegalArgumentException si la date de départ est après la date d'arrivée du vol
     */
    public void setDateDepart(ZonedDateTime dateDepart) {
        if (dateDepart == null) {
            throw new IllegalArgumentException("dateDepart cannot be null");
        }

        if (dateDepart.isBefore(this.dateArrivee) || dateDepart.isEqual(this.dateArrivee)) {
            throw new IllegalArgumentException("dateDepart doit être après dateArrivee");
        }

        Escale escaleSuivante = this.vol.getEscales().higher(this);

        if (escaleSuivante != null) {
            if (escaleSuivante.getDateArrivee().isBefore(dateDepart)
                    || (escaleSuivante.getDateArrivee().isEqual(dateDepart))) {
                throw new IllegalArgumentException("dateDepart doit être avant la date d'arrivée de l'escale suivante");
            }
        } else if (this.vol.getDateArrivee().isBefore(dateDepart) || this.vol.getDateArrivee().isEqual(dateDepart)) {
            throw new IllegalArgumentException("dateDepart doit être avant la date d'arrivée du vol");
        }

        this.dateDepart = dateDepart;
    }

    /**
     * Retourne la durée de l'escale
     *
     * @return la durée de l'escale
     */
    public Duration getDuree() {
        return Duration.between(dateArrivee, dateDepart);
    }

    /**
     * Supprime l'escale et met à jour l'aéroport
     */
    protected void removeEscale() {
        this.aeroport.removeEscaleWithoutBidirectional(this);
        this.aeroport = null;
        this.vol = null;
    }

    @Override
    public int compareTo(Escale escale) {
        return dateArrivee.compareTo(escale.dateArrivee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Escale)) return false;
        Escale escale = (Escale) o;
        return Objects.equals(getVol(), escale.getVol()) &&
                Objects.equals(getAeroport(), escale.getAeroport()) &&
                Objects.equals(getDateArrivee(), escale.getDateArrivee()) &&
                Objects.equals(getDateDepart(), escale.getDateDepart());
    }
}
