public class Route {
  private Ville villeDepart;
  private Ville villeArrivee;
  private double distance;

  public Route(Ville villeDepart, Ville villeArrivee) {
    this.villeDepart = villeDepart;
    this.villeArrivee = villeArrivee;
    this.distance = Util.distance(villeDepart.getLat(), villeDepart.getLng(), villeArrivee.getLat(), villeArrivee.getLng());
  }

  public Ville getVilleDepart() {
    return villeDepart;
  }

  public Ville getVilleArrivee() {
    return villeArrivee;
  }

  public double getDistance() {
    return distance;
  }

  @Override
  public String toString() {
    return "Route{" +
        "villeDepart=" + villeDepart +
        ", villeArrivee=" + villeArrivee +
        '}';
  }
}
