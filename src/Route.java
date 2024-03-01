public class Route {
  private int villeDepart;
  private int villeArrivee;

  public Route(int villeDepart, int villeArrivee) {
    this.villeDepart = villeDepart;
    this.villeArrivee = villeArrivee;
  }

  @Override
  public String toString() {
    return "Route{" +
        "villeDepart=" + villeDepart +
        ", villeArrivee=" + villeArrivee +
        '}';
  }
}
