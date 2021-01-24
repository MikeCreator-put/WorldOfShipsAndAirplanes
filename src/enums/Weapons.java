package enums;

/**
 * Includes names of armament which are later used to create <i>MilitaryShips<i/> and <i>MilitaryAirplanes<i/>.
 */
public enum Weapons {
    SparrowMissiles("sparrow missiles"),
    PhoenixMissiles("phoenix missiles"),
    BrowningGun("browning gun"),
    GatlingGun("gatling gun"),
    NuclearWarhead("nuclear warhead");

    private final String text;

    Weapons(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
