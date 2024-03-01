import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {
  private Map<Integer, Ville> villes;
  private Map<Ville, Set<Route>> routes;
    public Graph(File cities, File roads) {
      routes = new HashMap<>();
      villes = new HashMap<>();
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
        double lng = Double.parseDouble(city[2]);
        double lat = Double.parseDouble(city[3]);
        Ville ville = new Ville(id, nom, lng, lat);
        villes.put(id, ville);
        routes.put(ville, new HashSet<>());
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
      for (Set<Route> value : routes.values()) {
        for (Route route : value) {
          System.out.println(route.getVilleDepart().getId()+","+route.getVilleArrivee().getId());
        };
      }
    }

    public void calculerItineraireMinimisantNombreRoutes(String villeDepart, String villeArrivee){

    }

    public void calculerItineraireMinimisantKm(String villeDepart, String villeArrivee){

    }
}
