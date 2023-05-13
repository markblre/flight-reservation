package gestionVol;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.TreeSet;

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
     *  L'heure d'arrivée à l'escale
     */
    private ZonedDateTime heureArrivee;

    /**
     *  L'heure de départ de l'escale
     */
    private ZonedDateTime heureDepart;

    /**
     * Constructeur de la classe Escale
     *
     * @param vol le vol concerné par l'escale
     * @param aeroport l'aéroport où le vol fait escale
     * @param heureArrivee l'heure d'arrivée à l'escale
     * @param heureDepart l'heure de départ de l'escale
     * @exception IllegalArgumentException si le vol, l'aéroport, l'heure d'arrivée ou l'heure de départ est null
     * @exception IllegalArgumentException si l'heure d'arrivée est après l'heure de départ
     */
    protected Escale(Vol vol, Aeroport aeroport, ZonedDateTime heureArrivee, ZonedDateTime heureDepart) {
        if (vol == null || aeroport == null || heureArrivee == null || heureDepart == null) {
            throw new IllegalArgumentException("vol, aeroport, heureArrivee and heureDepart cannot be null");
        }

        if (heureArrivee.isAfter(heureDepart) || heureArrivee.isEqual(heureDepart)) {
            throw new IllegalArgumentException("heureArrivee doit être avant heureDepart");
        }

        this.vol = vol;

        this.aeroport = aeroport;
        this.aeroport.addEscaleWithoutBidirectional(this);

        this.heureArrivee = heureArrivee;
        this.heureDepart = heureDepart;
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
     * Retourne l'heure d'arrivée à l'escale
     *
     * @return l'heure d'arrivée à l'escale
     */
    public ZonedDateTime getHeureArrivee() {
        return heureArrivee;
    }

    /**
     * Modifie l'heure d'arrivée à l'escale
     *
     * @param heureArrivee la nouvelle heure d'arrivée à l'escale
     * @exception IllegalArgumentException si l'heure d'arrivée est après l'heure de départ
     * @exception IllegalArgumentException si l'heure d'arrivée est avant l'heure de départ de l'escale précédente
     * @exception IllegalArgumentException si l'heure d'arrivée est avant l'heure de départ du vol
     */
    public void setHeureArrivee(ZonedDateTime heureArrivee) {
        if (heureArrivee == null) {
            throw new IllegalArgumentException("heureArrivee cannot be null");
        }

        if (heureArrivee.isAfter(this.heureDepart) || heureArrivee.isEqual(this.heureDepart)) {
            throw new IllegalArgumentException("heureArrivee doit être avant heureDepart");
        }

        Escale escalePrecedente = this.vol.getEscales().lower(this);

        if (escalePrecedente != null) {
            if (escalePrecedente.getHeureDepart().isAfter(heureArrivee)
                    || (escalePrecedente.getHeureDepart().isEqual(heureArrivee))) {
                throw new IllegalArgumentException("heureArrivee doit être après l'heure de départ de l'escale précédente");
            }
        } else if (this.vol.getDateDepart().isAfter(heureArrivee) || this.vol.getDateDepart().isEqual(heureArrivee)) {
            throw new IllegalArgumentException("heureArrivee doit être après l'heure de départ du vol");
        }

        this.heureArrivee = heureArrivee;
    }

    /**
     * Retourne l'heure de départ de l'escale
     *
     * @return l'heure de départ de l'escale
     */
    public ZonedDateTime getHeureDepart() {
        return heureDepart;
    }

    /**
     * Modifie l'heure de départ de l'escale
     *
     * @param heureDepart la nouvelle heure de départ de l'escale
     * @exception IllegalArgumentException si l'heure de départ est avant l'heure d'arrivée
     * @exception IllegalArgumentException si l'heure de départ est après l'heure d'arrivée à l'escale suivante
     * @exception IllegalArgumentException si l'heure de départ est après l'heure d'arrivée du vol
     */
    public void setHeureDepart(ZonedDateTime heureDepart) {
        if (heureDepart == null) {
            throw new IllegalArgumentException("heureDepart cannot be null");
        }

        if (heureDepart.isBefore(this.heureArrivee) || heureDepart.isEqual(this.heureArrivee)) {
            throw new IllegalArgumentException("heureDepart doit être après heureArrivee");
        }

        Escale escaleSuivante = this.vol.getEscales().higher(this);

        if (escaleSuivante != null) {
            if (escaleSuivante.getHeureArrivee().isBefore(heureDepart)
                    || (escaleSuivante.getHeureArrivee().isEqual(heureDepart))) {
                throw new IllegalArgumentException("heureDepart doit être avant l'heure d'arrivée de l'escale suivante");
            }
        } else if (this.vol.getDateArrivee().isBefore(heureDepart) || this.vol.getDateArrivee().isEqual(heureDepart)) {
            throw new IllegalArgumentException("heureDepart doit être avant l'heure d'arrivée du vol");
        }

        this.heureDepart = heureDepart;
    }

    /**
     * Retourne la durée de l'escale
     *
     * @return la durée de l'escale
     */
    public Duration getDuree() {
        return Duration.between(heureArrivee, heureDepart);
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
        return heureArrivee.compareTo(escale.heureArrivee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Escale)) return false;
        Escale escale = (Escale) o;
        return Objects.equals(getVol(), escale.getVol()) &&
                Objects.equals(getAeroport(), escale.getAeroport()) &&
                Objects.equals(getHeureArrivee(), escale.getHeureArrivee()) &&
                Objects.equals(getHeureDepart(), escale.getHeureDepart());
    }
}
