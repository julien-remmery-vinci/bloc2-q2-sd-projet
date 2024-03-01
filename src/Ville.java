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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ville ville = (Ville) o;
    return id == ville.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
