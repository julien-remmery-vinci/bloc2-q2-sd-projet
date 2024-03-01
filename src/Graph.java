import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Graph {
    public Graph(File cities, File roads) {
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
        System.out.println(ville);
      }
      while(roadsScanner.hasNextLine()) {
        String[] road = roadsScanner.nextLine().split(",");
        int idVilleDepart = Integer.parseInt(road[0]);
        int idVilleArrivee = Integer.parseInt(road[1]);
        Route route = new Route(idVilleDepart, idVilleArrivee);
        System.out.println(route);
      }
    }

    public void calculerItineraireMinimisantNombreRoutes(String villeDepart, String villeArrivee){
    }

    public void calculerItineraireMinimisantKm(String villeDepart, String villeArrivee){
    }
}
