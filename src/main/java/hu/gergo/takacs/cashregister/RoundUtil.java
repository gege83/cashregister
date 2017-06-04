package hu.gergo.takacs.cashregister;

public abstract class RoundUtil {
    public static double roundToBusinessDecimal(double value) {
        double roundNumber = 1000.;
        return Math.round(value * roundNumber) / roundNumber;
    }
}
