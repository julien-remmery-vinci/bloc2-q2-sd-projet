import java.util.Objects;

public class Ville {
  private int id;
  private String nom;
  private double lng;
  private double lat;

  public Ville(int id) {
    this.id = id;
  }

  public Ville(int id, String nom, double lng, double lat) {
    this.id = id;
    this.nom = nom;
    this.lng = lng;
    this.lat = lat;
  }

  @Override
  public String toString() {
    return "Ville{" +
        "id=" + id +
        ", nom='" + nom + '\'' +
        ", lng=" + lng +
        ", lat=" + lat +
        '}';
  }

  public int getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }

  public double getLng() {
    return lng;
  }

  public double getLat() {
    return lat;
  }
}
