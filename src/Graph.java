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

        Ville cmpVilleArrivee = new Ville(idVilleArrivee);
        Set<Route> s1 = routes.get(cmpVilleArrivee);
        s1.add(r);
        routes.put(cmpVilleArrivee, s1);
      }
      System.out.println(routes);
    }

    public void calculerItineraireMinimisantNombreRoutes(String villeDepart, String villeArrivee){
    }

    public void calculerItineraireMinimisantKm(String villeDepart, String villeArrivee){
    }
}
