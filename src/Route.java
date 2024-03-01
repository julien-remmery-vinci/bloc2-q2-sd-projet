public class Route {
  private int villeDepart;
  private int villeArrivee;

  public Route(int villeDepart, int villeArrivee) {
    this.villeDepart = villeDepart;
    this.villeArrivee = villeArrivee;
  }

  public int getVilleDepart() {
    return villeDepart;
  }

  public int getVilleArrivee() {
    return villeArrivee;
  }

  @Override
  public String toString() {
    return "Route{" +
        "villeDepart=" + villeDepart +
        ", villeArrivee=" + villeArrivee +
        '}';
  }
}
