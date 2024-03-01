import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Graph {
  private HashMap<Ville, Set<Route>> routes;
    public Graph(File cities, File roads) {
      routes = new HashMap<>();
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
        routes.put(ville, new HashSet<>());
      }
      while(roadsScanner.hasNextLine()) {
        String[] road = roadsScanner.nextLine().split(",");
        int idVilleDepart = Integer.parseInt(road[0]);
        int idVilleArrivee = Integer.parseInt(road[1]);

        Route r = new Route(idVilleDepart, idVilleArrivee);
        Ville cmpVilleDepart = new Ville(idVilleDepart);
        Set<Route> s = routes.get(cmpVilleDepart);
        s.add(r);
        routes.put(cmpVilleDepart, s);

        Route r2 = new Route(idVilleArrivee, idVilleDepart);
        Ville cmpVilleArrivee = new Ville(idVilleArrivee);
        Set<Route> s2 = routes.get(cmpVilleArrivee);
        s2.add(r2);
        routes.put(cmpVilleArrivee, s2);
      }
      for (Set<Route> value : routes.values()) {
        for (Route route : value) {
          System.out.println(route.getVilleDepart()+","+route.getVilleArrivee());
        };
      }
    }

    public void calculerItineraireMinimisantNombreRoutes(String villeDepart, String villeArrivee){

    }

    public void calculerItineraireMinimisantKm(String villeDepart, String villeArrivee){

    }
}
