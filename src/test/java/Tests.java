import gestionVol.*;
import reservation.*;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.ZoneId;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {

    // Tests de aéroport / ville
    @Test
    public void testAeroport() {
        // Création d'un aéroport
        Ville roissyEnFrance = new Ville("Roissy-en-France");
        Aeroport cdg = new Aeroport("CDG", roissyEnFrance);

        assertThat(cdg.getVille(), equalTo(roissyEnFrance));
        assertThat(roissyEnFrance.getAeroports(), hasItem(cdg));

        // Ajout d'une ville desservie
        Ville paris = new Ville("Paris");
        cdg.addVilleDesservie(paris);

        assertThat(cdg.getVillesDesservies(), hasItem(paris));
        assertThat(paris.getAeroportsDesservant(), hasItem(cdg));

        // Suppression d'une ville desservie
        cdg.removeVilleDesservie(paris);
        assertThat(cdg.getVillesDesservies(), not(hasItem(paris)));
        assertThat(paris.getAeroportsDesservant(), not(hasItem(cdg)));

        // Suppression d'un aéroport
        cdg.addVilleDesservie(paris);
        cdg.removeAeroport();
        assertThat(roissyEnFrance.getAeroports(), not(hasItem(cdg)));
        assertThat(paris.getAeroportsDesservant(), not(hasItem(cdg)));
    }

    // Tests de la création d'un vol avec escales
    @Test
    public void testCreationVol() {
        // Init
        Ville RoissyEnFrance = new Ville("Roissy-en-France");
        Aeroport cdg = new Aeroport("CDG", RoissyEnFrance);

        Ville NewYork = new Ville("New York");
        Aeroport jfk = new Aeroport("JFK", NewYork);

        Compagnie airFrance = new Compagnie("Air France");

        ZonedDateTime dateDepart;
        ZonedDateTime dateArrivee;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dd = "21/10/2020 13:00";
        String da = "23/10/2020 02:15";

        try {
            dateDepart = ZonedDateTime.ofInstant(format.parse(dd).toInstant(), ZoneId.systemDefault());
            dateArrivee = ZonedDateTime.ofInstant(format.parse(da).toInstant(), ZoneId.systemDefault());
        } catch (Exception e){
            throw new RuntimeException("Unable to format to date");
        }

        // Tests de création de vol
        Vol vol = new Vol("AF1234", airFrance, cdg, jfk, dateDepart, dateArrivee, 130, 100.0);

        assertThat(vol.getNumero(), equalTo("AF1234"));
        assertThat(vol.getCompagnie(), equalTo(airFrance));
        assertThat(airFrance.getVols(), hasItem(vol));
        assertThat(vol.getDepart(), equalTo(cdg));
        assertThat(cdg.getVolsAuDepart(), hasItem(vol));
        assertThat(vol.getArrivee(), equalTo(jfk));
        assertThat(jfk.getVolsAArrivee(), hasItem(vol));
        assertThat(vol.getDateDepart(), equalTo(dateDepart));
        assertThat(vol.getDateArrivee(), equalTo(dateArrivee));
        assertThat(vol.getPlacesDisponibles(), equalTo(130));
        assertThat(vol.getPrix(), equalTo(100.0));
        assertThat(vol.obtenirDuree(), equalTo(Duration.between(dateDepart, dateArrivee)));

        // Ajout d'escale

        Ville Londres = new Ville("Londres");
        Aeroport lhr = new Aeroport("LHR", Londres);

        ZonedDateTime dateArriveeEscale;
        ZonedDateTime dateDepartEscale;

        String dae = "21/10/2020 14:30";
        String dde = "21/10/2020 17:00";

        try {
            dateArriveeEscale = ZonedDateTime.ofInstant(format.parse(dae).toInstant(), ZoneId.systemDefault());
            dateDepartEscale = ZonedDateTime.ofInstant(format.parse(dde).toInstant(), ZoneId.systemDefault());
        } catch (Exception e){
            throw new RuntimeException("Unable to format to date");
        }

        Escale nouvelleEscale = vol.addEscale(lhr, dateArriveeEscale, dateDepartEscale);

        assertThat(vol.getEscales(), hasItem(nouvelleEscale));
        assertThat(lhr.getEscales(), hasItem(nouvelleEscale));

        assertThat(nouvelleEscale.getDuree(), equalTo(Duration.between(dateArriveeEscale, dateDepartEscale)));

        nouvelleEscale.setDateArrivee(dateArriveeEscale.plusHours(1));
        assertThat(nouvelleEscale.getDateArrivee(), equalTo(dateArriveeEscale.plusHours(1)));

        nouvelleEscale.setDateDepart(dateDepartEscale.plusHours(1));
        assertThat(nouvelleEscale.getDateDepart(), equalTo(dateDepartEscale.plusHours(1)));

        // Ajout d'une seconde escale
        Ville Amsterdam = new Ville("Amsterdam");
        Aeroport ams = new Aeroport("AMS", Amsterdam);

        ZonedDateTime dateArriveeEscale2;
        ZonedDateTime dateDepartEscale2;

        String dae2 = "21/10/2020 18:15";
        String dde2 = "21/10/2020 20:00";

        try {
            dateArriveeEscale2 = ZonedDateTime.ofInstant(format.parse(dae2).toInstant(), ZoneId.systemDefault());
            dateDepartEscale2 = ZonedDateTime.ofInstant(format.parse(dde2).toInstant(), ZoneId.systemDefault());
        } catch (Exception e){
            throw new RuntimeException("Unable to format to date");
        }

        Escale nouvelleEscale2 = vol.addEscale(ams, dateArriveeEscale2, dateDepartEscale2);

        assertThat(vol.getEscales(), hasItem(nouvelleEscale));
        assertThat(ams.getEscales(), hasItem(nouvelleEscale2));

        // Suppression d'escale
        vol.removeEscale(nouvelleEscale2);
        assertThat(vol.getEscales(), not(hasItem(nouvelleEscale2)));
        assertThat(ams.getEscales(), not(hasItem(nouvelleEscale2)));


        // Suppression d'un vol
        vol.removeVol();
        assertThat(airFrance.getVols(), not(hasItem(vol)));
        assertThat(cdg.getVolsAuDepart(), not(hasItem(vol)));
        assertThat(jfk.getVolsAArrivee(), not(hasItem(vol)));
        assertThat(lhr.getEscales(), not(hasItem(nouvelleEscale)));
    }

    // Test système de réservation
    @Test
    public void testReservations() {
        // Init
        Ville RoissyEnFrance = new Ville("Roissy-en-France");
        Aeroport cdg = new Aeroport("CDG", RoissyEnFrance);

        Ville NewYork = new Ville("New York");
        Aeroport jfk = new Aeroport("JFK", NewYork);

        Compagnie airFrance = new Compagnie("Air France");

        ZonedDateTime dateDepart;
        ZonedDateTime dateArrivee;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dd = "21/10/2020 13:00";
        String da = "23/10/2020 02:15";

        try {
            dateDepart = ZonedDateTime.ofInstant(format.parse(dd).toInstant(), ZoneId.systemDefault());
            dateArrivee = ZonedDateTime.ofInstant(format.parse(da).toInstant(), ZoneId.systemDefault());
        } catch (Exception e){
            throw new RuntimeException("Unable to format to date");
        }

        Vol vol = new Vol("AF1234", airFrance, cdg, jfk, dateDepart, dateArrivee, 130, 100.0);
        vol.ouvrir();

        // Tests création d'une réservation
        Client client = new Client("Dupont", "CB", "Dupont@gmail.com");
        Reservation reservation = new Reservation(client, vol);

        assertThat(reservation.getClient(), equalTo(client));
        assertThat(client.getReservations(), hasItem(reservation));

        assertThat(reservation.getEtat(), equalTo(EtatReservation.EN_ATTENTE));

        // Tests de passagers
        Passager p1 = new Passager("Dupont", "Jean");
        Passager p2 = new Passager("Dupont", "Marie");
        Passager p3 = new Passager("Dupont", "Paul");

        reservation.addPassagers(p1, p2, p3);

        assertThat(reservation.getPassagers(), hasItem(p1));
        assertThat(p1.getReservations(), hasItem(reservation));
        assertThat(reservation.getPassagers(), hasItem(p2));
        assertThat(p2.getReservations(), hasItem(reservation));
        assertThat(reservation.getPassagers(), hasItem(p3));
        assertThat(p3.getReservations(), hasItem(reservation));

        // Tests suppression de passagers
        reservation.removePassager(p3);

        assertThat(reservation.getPassagers(), not(hasItem(p3)));
        assertThat(p3.getReservations(), not(hasItem(reservation)));

        // tests changement d'état
        reservation.payer();
        assertThat(reservation.getEtat(), equalTo(EtatReservation.PAYEE));

        reservation.confirmer();
        assertThat(reservation.getEtat(), equalTo(EtatReservation.CONFIRMEE));

        reservation.annuler();
        assertThat(reservation.getEtat(), equalTo(EtatReservation.ANNULEE));

    }

    // Tests d'erreur Aeroport
    @Test
    public void testErreurAeroport() {
        // Init
        Ville RoissyEnFrance = new Ville("Roissy-en-France");
        Aeroport cdg = new Aeroport("CDG", RoissyEnFrance);

        Ville NewYork = new Ville("New York");
        Aeroport jfk = new Aeroport("JFK", NewYork);

        Compagnie airFrance = new Compagnie("Air France");

        ZonedDateTime dateDepart;
        ZonedDateTime dateArrivee;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dd = "21/10/2020 13:00";
        String da = "23/10/2020 02:15";

        try {
            dateDepart = ZonedDateTime.ofInstant(format.parse(dd).toInstant(), ZoneId.systemDefault());
            dateArrivee = ZonedDateTime.ofInstant(format.parse(da).toInstant(), ZoneId.systemDefault());
        } catch (Exception e) {
            throw new RuntimeException("Unable to format to date");
        }

        Vol vol = new Vol("AF1234", airFrance, cdg, jfk, dateDepart, dateArrivee, 130, 100.0);

        // Tests suppression de l'aeroport avec vol au départ et à l'arrivée
        assertThrows(IllegalStateException.class, () -> cdg.removeAeroport());
        assertThrows(IllegalStateException.class, () -> jfk.removeAeroport());
    }

    // Tests d'erreur Vol
    @Test
    public void testErreurVol() {
        // Init
        Ville RoissyEnFrance = new Ville("Roissy-en-France");
        Aeroport cdg = new Aeroport("CDG", RoissyEnFrance);

        Ville NewYork = new Ville("New York");
        Aeroport jfk = new Aeroport("JFK", NewYork);

        Ville Londres = new Ville("Londres");
        Aeroport lhr = new Aeroport("LHR", Londres);

        Compagnie airFrance = new Compagnie("Air France");

        ZonedDateTime dateDepart;
        ZonedDateTime dateArrivee;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dd = "21/10/2020 13:00";
        String da = "23/10/2020 02:15";

        try {
            dateDepart = ZonedDateTime.ofInstant(format.parse(dd).toInstant(), ZoneId.systemDefault());
            dateArrivee = ZonedDateTime.ofInstant(format.parse(da).toInstant(), ZoneId.systemDefault());
        } catch (Exception e) {
            throw new RuntimeException("Unable to format to date");
        }

        // Test création d'un vol avec dateDepart après dateArrivee
        assertThrows(IllegalArgumentException.class,
                () -> new Vol("AF1234", airFrance, cdg, jfk, dateArrivee, dateDepart, 130, 100.0));

        Vol vol = new Vol("AF1234", airFrance, cdg, jfk, dateDepart, dateArrivee, 130, 100.0);

        // Test modification interdite des dates de départ et d'arrivée
        assertThrows(IllegalArgumentException.class, () -> vol.setDateDepart(dateArrivee.plusHours(1)));
        assertThrows(IllegalArgumentException.class, () -> vol.setDateArrivee(dateDepart.minusHours(1)));

        // Test ajout d'une escale incompatible avec les heures de départ et d'arrivé du vol
        assertThrows(IllegalArgumentException.class, () -> vol.addEscale(lhr, dateDepart.minusHours(1), dateArrivee.minusHours(1)));
        assertThrows(IllegalArgumentException.class, () -> vol.addEscale(lhr, dateDepart.plusHours(1), dateArrivee.plusHours(1)));

        // Test ajout d'une escale incompatible avec une autre escale
        ZonedDateTime dateArriveeEscale;
        ZonedDateTime dateDepartEscale;

        String dae = "22/10/2020 12:00";
        String dde = "22/10/2020 14:00";

        try {
            dateArriveeEscale = ZonedDateTime.ofInstant(format.parse(dae).toInstant(), ZoneId.systemDefault());
            dateDepartEscale = ZonedDateTime.ofInstant(format.parse(dde).toInstant(), ZoneId.systemDefault());
        } catch (Exception e) {
            throw new RuntimeException("Unable to format to date");
        }

        vol.addEscale(lhr, dateArriveeEscale, dateDepartEscale);

        Ville Amsterdam = new Ville("Amsterdam");
        Aeroport ams = new Aeroport("AMS", Amsterdam);

        assertThrows(IllegalArgumentException.class, () -> vol.addEscale(ams, dateArriveeEscale.minusHours(2), dateArriveeEscale.plusHours(1)));
        assertThrows(IllegalArgumentException.class, () -> vol.addEscale(ams, dateDepartEscale.minusHours(1), dateDepartEscale.plusHours(2)));
    }

    // Tests d'erreur Reservation
    @Test
    public void testErreurReservation() {
        // Init
        Ville RoissyEnFrance = new Ville("Roissy-en-France");
        Aeroport cdg = new Aeroport("CDG", RoissyEnFrance);

        Ville NewYork = new Ville("New York");
        Aeroport jfk = new Aeroport("JFK", NewYork);

        Compagnie airFrance = new Compagnie("Air France");

        ZonedDateTime dateDepart;
        ZonedDateTime dateArrivee;

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        String dd = "21/10/2020 13:00";
        String da = "23/10/2020 02:15";

        try {
            dateDepart = ZonedDateTime.ofInstant(format.parse(dd).toInstant(), ZoneId.systemDefault());
            dateArrivee = ZonedDateTime.ofInstant(format.parse(da).toInstant(), ZoneId.systemDefault());
        } catch (Exception e){
            throw new RuntimeException("Unable to format to date");
        }

        Vol vol = new Vol("AF1234", airFrance, cdg, jfk, dateDepart, dateArrivee, 130, 100.0);

        // Test création d'une réservation sur un vol fermé à la réservation
        Client client = new Client("Dupont", "CB", "Dupont@gmail.com");
        assertThrows(IllegalStateException.class, () -> new Reservation(client, vol));

        // Test ajout d'un passager déjà présent dans la réservation
        vol.ouvrir();
        Reservation reservation = new Reservation(client, vol);
        Passager p1 = new Passager("Dupont", "Jean");
        Passager p2 = new Passager("Dupont", "Marie");
        reservation.addPassagers(p1);

        assertThrows(IllegalArgumentException.class, () -> reservation.addPassagers(p1));

        reservation.removePassager(p1);

        // Tests opération impossible sur une réservation en attente sans passager
        assertThrows(IllegalStateException.class, () -> reservation.payer());
        assertThrows(IllegalStateException.class, () -> reservation.confirmer());

        reservation.addPassagers(p1);

        // Test opération impossible sur une réservation payée
        reservation.payer();
        assertThrows(IllegalStateException.class, () -> reservation.addPassagers(p2));
        assertThrows(IllegalStateException.class, () -> reservation.removePassager(p1));

        // Tests opération impossible sur une réservation annnulée
        reservation.annuler();
        assertThrows(IllegalStateException.class, () -> reservation.addPassagers(p2));
        assertThrows(IllegalStateException.class, () -> reservation.payer());
        assertThrows(IllegalStateException.class, () -> reservation.confirmer());
    }
}
