import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Graph {
  private Map<Integer, Ville> villes;
  private Map<String, Ville> villesNom;
  private Map<Ville, Set<Route>> routes;
    public Graph(File cities, File roads) {
      routes = new HashMap<>();
      villes = new HashMap<>();
      villesNom = new HashMap<>();
      Scanner citiesScanner;
      Scanner roadsScanner;
      try {
        citiesScanner = new Scanner(cities);
        roadsScanner = new Scanner(roads);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      while(citiesScanner.hasNextLine()) {
        String[] city = citiesScanner.nextLine().split(",");
        int id = Integer.parseInt(city[0]);
        String nom = city[1];
        double lat = Double.parseDouble(city[2]);
        double lng = Double.parseDouble(city[3]);
        Ville ville = new Ville(id, nom, lat, lng);
        villes.put(id, ville);
        routes.put(ville, new HashSet<>());
        villesNom.put(nom, ville);
      }
      while(roadsScanner.hasNextLine()) {
        String[] road = roadsScanner.nextLine().split(",");
        int idVilleDepart = Integer.parseInt(road[0]);
        int idVilleArrivee = Integer.parseInt(road[1]);

        Route r = new Route(villes.get(idVilleDepart), villes.get(idVilleArrivee));
        Set<Route> s = routes.get(villes.get(idVilleDepart));
        s.add(r);
        routes.put(villes.get(idVilleDepart), s);

        Route r2 = new Route(villes.get(idVilleArrivee), villes.get(idVilleDepart));
        Set<Route> s2 = routes.get(villes.get(idVilleArrivee));
        s2.add(r2);
        routes.put(villes.get(idVilleArrivee), s2);
      }
    }

    public void calculerItineraireMinimisantNombreRoutes(String villeDepart, String villeArrivee){
      Ville depart = villesNom.get(villeDepart);
      Ville arrivee = villesNom.get(villeArrivee);
      if (depart == null || arrivee == null) {
        throw new RuntimeException("Ville non trouvée");
      }
      Deque<Ville> aVisiter = new LinkedList<>();
      Set<Ville> villeVisitees = new HashSet<>();
      Map<Ville, Route> routesPrecedentes = new HashMap<>();
      aVisiter.add(depart);
      villeVisitees.add(depart);
      boolean trouve = false;
      while(!aVisiter.isEmpty()) {
        Ville ville = aVisiter.poll();
        if (ville.equals(arrivee)) {
          trouve = true;
          break;
        }
        Set<Route> routes = this.routes.get(ville);
        for (Route route : routes) {
          Ville vArrivee = route.getVilleArrivee();
          if (!villeVisitees.contains(vArrivee)) {
            villeVisitees.add(vArrivee);
            aVisiter.add(vArrivee);
            routesPrecedentes.put(vArrivee, route);
          }
        }
      }
      if (!trouve) {
        throw new RuntimeException("Aucun chemin trouvé");
      }
      afficherTrajet(villeDepart, villeArrivee, routesPrecedentes);
    }

  public void calculerItineraireMinimisantKm(String villeDepart, String villeArrivee){
    Ville depart = villesNom.get(villeDepart);
    Ville arrivee = villesNom.get(villeArrivee);
    if (depart == null || arrivee == null) {
      throw new RuntimeException("Ville non trouvée");
    }

    Map<Ville, Double> distancesMinimales = new HashMap<>();
    Map<Ville, Route> routesPrecedentes = new HashMap<>();
    PriorityQueue<Ville> file = new PriorityQueue<>(Comparator.comparingDouble(distancesMinimales::get));

    distancesMinimales.put(depart, 0.0);
    file.add(depart);

    while (!file.isEmpty()) {
      Ville actuelle = file.poll();
      double distanceActuelle = distancesMinimales.get(actuelle);

      for (Route route : routes.get(actuelle)) {
        Ville voisine = route.getVilleArrivee();
        double distanceViaActuelle = distanceActuelle + route.getDistance();

        if (!distancesMinimales.containsKey(voisine) || distanceViaActuelle < distancesMinimales.get(voisine)) {
          distancesMinimales.put(voisine, distanceViaActuelle);
          routesPrecedentes.put(voisine, route);
          file.add(voisine);
        }
      }
    }

    if (!distancesMinimales.containsKey(arrivee)) {
      throw new RuntimeException("Aucun chemin trouvé");
    }

    afficherTrajet(villeDepart, villeArrivee, routesPrecedentes);
  }

  private void afficherTrajet(String villeDepart, String villeArrivee, Map<Ville, Route> routesPrecedentes) {
    Ville depart = villesNom.get(villeDepart);
    Ville v = villesNom.get(villeArrivee);
    Deque<Route> itineraireInverse = new LinkedList<>();
    int totalRoutes = 0;
    double distanceTotale = 0;
    while(v != depart) {
      Route route = routesPrecedentes.get(v);
      itineraireInverse.addFirst(route);
      totalRoutes++;
      distanceTotale += route.getDistance();
      v = route.getVilleDepart();
    }

    System.out.println("Trajet de "+ villeDepart +" à "+ villeArrivee +": "+totalRoutes+" routes et "+distanceTotale+" km");
    while(!itineraireInverse.isEmpty()) {
      Route route = itineraireInverse.poll();
      System.out.println(route.getVilleDepart().getNom()+" -> "+route.getVilleArrivee().getNom() + " ("+Math.round(route.getDistance())+" km)");
    }
  }

}
