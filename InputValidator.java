import java.util.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InputValidator {

    public static Optional<Integer> parseInteger(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Double> parseDouble(String value) {
        try {
            return Optional.of(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> parseString(String value) {
        return Optional.ofNullable(value);
    }

    public static Optional<java.util.Date> parseDate(String value, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return Optional.of(sdf.parse(value));
        } catch (ParseException e) {
            return Optional.empty();
        }
    }
}