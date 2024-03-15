import java.util.Objects;

public class Ville {
  private int id;
  private String nom;
  private double lat;
  private double lng;

  public Ville(int id) {
    this.id = id;
  }

  public Ville(int id, String nom, double lat, double lng) {
    this.id = id;
    this.nom = nom;
    this.lat = lat;
    this.lng = lng;
  }

  @Override
  public String toString() {
    return "Ville{" +
        "id=" + id +
        ", nom='" + nom + '\'' +
        ", lat=" + lat +
        ", lng=" + lng +
        '}';
  }

  public int getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }
  public double getLat() {
    return lat;
  }
  public double getLng() {
    return lng;
  }
}
