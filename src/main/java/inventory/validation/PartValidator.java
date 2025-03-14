package inventory.validation;

public class PartValidator {

    private PartValidator() {
        throw new IllegalStateException("Utility class");
    }
    public static String validate(String name, double price, int stock, int min, int max) {
        String errorMessage = "";

        if (name == null || name.trim().isEmpty()) {
            errorMessage += "Numele nu poate fi gol.\n";
        }
        if (price <= 0) {
            errorMessage += "Prețul trebuie să fie mai mare decât 0.\n";
        }
        if (stock < min || stock > max) {
            errorMessage += "Stocul trebuie să fie între minim și maxim.\n";
        }
        if (min > max) {
            errorMessage += "Minimul nu poate fi mai mare decât maximul.\n";
        }

        return errorMessage;
    }
}
