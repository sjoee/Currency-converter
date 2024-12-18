package javaproject1;
import javax.swing.JOptionPane;

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
        String currencyType;

        // Loop until valid currency type is entered
        do {
            // Prompt user for input using JOptionPane
            currencyType = JOptionPane.showInputDialog(null, "Enter chosen currency type (SGD, USD, EUR, JPY, GBP):", "KAWAGUCHI BANK", JOptionPane.INFORMATION_MESSAGE);

            // Check if user clicked "Cancel" or closed the dialog
            if (currencyType == null) {
                return;
            }

            // Display error message if the currency is invalid
            if (!isValidCurrency(currencyType)) {
                int option = JOptionPane.showOptionDialog(
                        null,
                        "Invalid currency type. Please choose a valid currency type.",
                        "Kawaguchi Bank",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        new Object[]{"Try Again", "Cancel"},
                        "Try Again"
                );

                // Check if user clicked "Cancel" or closed the dialog
                if (option != 0) {
                    return;
                }
            }
        } while (!isValidCurrency(currencyType));

        double amount;

        // Loop until valid amount is entered
        do {
            // Prompt user for amount using JOptionPane
            String amountStr = JOptionPane.showInputDialog(null, "Enter amount: ", "KAWAGUCHI BANK", JOptionPane.INFORMATION_MESSAGE);

            // Check if user clicked "Cancel" or closed the dialog
            if (amountStr == null) {
                return;
            }

            try {
                amount = Double.parseDouble(amountStr);
            } catch (NumberFormatException e) {
                int option = JOptionPane.showOptionDialog(
                        null,
                        "Invalid input. Please enter a valid numeric amount.",
                        "KAWAGUCHI BANK",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        new Object[]{"Try Again", "Cancel"},
                        "Try Again"
                );

                // Check if user clicked "Cancel" or closed the dialog
                if (option != 0) {
                    return;
                }
                // Reset amount to enter the loop again
                amount = -1;
            }
        } while (amount == -1);

        // Get exchange rate based on the chosen currency
        double exchangeRate = getExchangeRate(currencyType);

        // Calculate result without admin fee
        double result = amount * exchangeRate;
        JOptionPane.showMessageDialog(null, String.format("Exchange result (MYR): %.2f", result), "KAWAGUCHI BANK", JOptionPane.INFORMATION_MESSAGE);

        // Calculate admin fee based on the provided scheme
        double adminFee = calculateAdminFee(result);

        // Display the result with admin fee using JOptionPane
        double totalAmount = result - adminFee;
        JOptionPane.showMessageDialog(null, String.format("Total after admin fee (MYR): %.2f", totalAmount), "KAWAGUCHI BANK", JOptionPane.INFORMATION_MESSAGE);

        // Ask if the user wants a receipt
        int option = JOptionPane.showConfirmDialog(null, "Do you want a receipt?", "Receipt Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        // Check if user clicked "Cancel" or closed the dialog
        if (option == JOptionPane.CLOSED_OPTION) {
            return;
        }

        if (option == JOptionPane.YES_OPTION) {
            // Construct and display the receipt
            StringBuilder receipt = new StringBuilder();
            receipt.append("Currency: ").append(currencyType).append("\n");
            receipt.append("Amount: ").append(String.format("%.2f", amount)).append("\n");
            receipt.append("Exchange Result (MYR): ").append(String.format("%.2f", result)).append("\n");
            receipt.append("Admin Fee: ").append(String.format("%.2f", adminFee)).append("\n");
            receipt.append("Total Amount (MYR): ").append(String.format("%.2f", totalAmount)).append("\n");
            JOptionPane.showMessageDialog(null, receipt.toString(), "KAWAGUCHI BANK Receipt", JOptionPane.INFORMATION_MESSAGE);
        }
        JOptionPane.showMessageDialog(null, "Thank you for using our services!", "KAWAGUCHI BANK", JOptionPane.INFORMATION_MESSAGE);
    }

    // Calculate admin fee method
    private static double calculateAdminFee(double result) {
        if (result >= 0 && result <= 2000) {
            return result * 0.0025; // 0.25%
        } else if (result >= 2000.01 && result <= 5000) {
            return result * 0.005; // 0.5%
        } else if (result >= 5000.01 && result <= 10000) {
            return result * 0.0075; // 0.75%
        } else {
            return result * 0.01; // 1.0%
        }
    }
}
