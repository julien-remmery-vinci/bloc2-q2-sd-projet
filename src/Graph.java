import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
//      for (Set<Route> value : routes.values()) {
//        for (Route route : value) {
//          System.out.println(route.getVilleDepart().getId()+","+route.getVilleArrivee().getId());
//        };
//      }
    }

    public void calculerItineraireMinimisantNombreRoutes(String villeDepart, String villeArrivee){
      Ville depart = villesNom.get(villeDepart);
      Ville arrivee = villesNom.get(villeArrivee);
      if (depart == null || arrivee == null) {
        throw new RuntimeException("Ville non trouvée");
      }
      Deque<Ville> aVisiter = new java.util.LinkedList<>();
      Set<Ville> villeVisitees = new HashSet<>();
      Map<Ville, Route> routesPrecedentes = new HashMap<>();
      aVisiter.add(depart);
      villeVisitees.add(depart);
      while(!aVisiter.isEmpty()) {
        Ville ville = aVisiter.poll();
        if (ville.equals(arrivee)) {
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
      Ville v = arrivee;
      Deque<Route> itineraireInverse = new LinkedList<>();
      while(v != depart) {
        Route route = routesPrecedentes.get(v);
        itineraireInverse.addFirst(route);
        v = route.getVilleDepart();
      }

      System.out.println("Trajet de "+villeDepart+" à "+villeArrivee+": "+itineraireInverse.size()+" routes et "+itineraireInverse.stream().mapToDouble(Route::getDistance).sum()+" km");
      while(!itineraireInverse.isEmpty()) {
        Route route = itineraireInverse.poll();
        System.out.println(route.getVilleDepart().getNom()+" -> "+route.getVilleArrivee().getNom() + " ("+Math.round(route.getDistance())+" km)");
      }
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

    Deque<Route> cheminLePlusCourt = new ArrayDeque<>();
    for (Ville v = arrivee; v != depart; v = routesPrecedentes.get(v).getVilleDepart()) {
      cheminLePlusCourt.addFirst(routesPrecedentes.get(v));
    }

    System.out.println("Trajet de "+villeDepart+" à "+villeArrivee+": "+cheminLePlusCourt.size()+" routes et "+distancesMinimales.get(arrivee)+" km");
    for (Route route : cheminLePlusCourt) {
      System.out.println(route.getVilleDepart().getNom()+" -> "+route.getVilleArrivee().getNom() + " ("+Math.round(route.getDistance())+" km)");
    }
  }
}
