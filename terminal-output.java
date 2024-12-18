package javaproject1;
import java.util.Scanner;

public class JavaProject1 {

    public static void main(String[] args) {
        // Call the currency converter logic
        runCurrencyConverter();
    }

    // Validate currency type method
    private static boolean isValidCurrency(String currencyType) {
        String[] validCurrencies = {"SGD", "USD", "EUR", "JPY", "GBP"};
        for (String validCurrency : validCurrencies) {
            if (validCurrency.equalsIgnoreCase(currencyType)) {
                return true;
            }
        }
        return false;
    }

    // Get exchange rate method
    private static double getExchangeRate(String currencyType) {
        switch (currencyType.toUpperCase()) {
            case "SGD":
                return 3.52;
            case "USD":
                return 4.73;
            case "EUR":
                return 5.15;
            case "JPY":
                return 3.2;
            case "GBP":
                return 6.0;
            default:
                return 0.0; // Invalid currency
        }
    }

    private static void runCurrencyConverter() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for input
        System.out.print("Enter chosen currency type (SGD, USD, EUR, JPY, GBP): ");
        String currencyType = scanner.nextLine();

        // Validate currency type
        if (!isValidCurrency(currencyType)) {
            System.out.println("Invalid currency type. Please choose a valid currency type.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = 0;

        // Validate amount input
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid numeric amount.");
            scanner.next(); // Consume the invalid input
        }
        amount = scanner.nextDouble();

        // Get exchange rate based on the chosen currency
        double exchangeRate = getExchangeRate(currencyType);

        // Calculate result without admin fee
        double result = amount * exchangeRate;
        System.out.printf("Exchange result (MYR): %.2f%n", result);

        // Calculate admin fee based on the provided scheme
        double adminFee = calculateAdminFee(result);

        // Display the result with admin fee
        double totalAmount = result - adminFee;
        System.out.printf("Total after admin fee (MYR): %.2f\n", totalAmount);

        // Close the scanner
        scanner.close();
    }

    // Calculate admin fee method
    private static double calculateAdminFee(double result) {
        if (result <= 2000) {
            return result * 0.0025; // 0.25%
        } else if (result <= 5000) {
            return result * 0.005; // 0.5%
        } else if (result <= 10000) {
            return result * 0.0075; // 0.75%
        } else {
            return result * 0.01; // 1.0%
        }
    }
}
