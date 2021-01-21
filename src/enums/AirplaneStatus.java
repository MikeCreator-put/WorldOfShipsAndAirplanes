package enums;

/**
 * Includes various states in which an <i>Airplane<i/> can be at the moment of time.
 * Each one of those states will be handled differently inside the function responsible for moving the airplane.
 */
public enum AirplaneStatus {
    travelling,
    arrivedAtCheckpoint,
    arrivedAtDestination,
    waitingToBeLetIn,
    waitingForDestination,
    emergency,
    askForPermissionToEnterPath
}
