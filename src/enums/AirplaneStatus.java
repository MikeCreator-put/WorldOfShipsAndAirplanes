package enums;

/**
 * Includes various states in which an <i>Airplane<i/> can be at the moment of time.
 * Each one of those states will be handled differently inside the function responsible for moving the airplane.
 */
public enum AirplaneStatus {
    travelling("travelling"),
    arrivedAtCheckpoint("arrived at checkpoint"),
    arrivedAtDestination("arrived at destination"),
    waitingToBeLetIn("waiting to be let in"),
    waitingForDestination("waiting for destination"),
    emergency("during emergency"),
    askForPermissionToEnterPath("asking for permission to enter path");

    private final String text;

    AirplaneStatus(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
