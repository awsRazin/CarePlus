package hospital.util;

import java.util.Scanner;

public final class Input {
    private static final Scanner SCANNER = new Scanner(System.in);
    private Input() { }
    public static String text(String label) { System.out.print(label); return SCANNER.nextLine().trim(); }
    public static int number(String label) {
        while (true) try { return Integer.parseInt(text(label)); }
        catch (NumberFormatException e) { System.out.println("Please enter a whole number."); }
    }
    public static double amount(String label) {
        while (true) try { return Double.parseDouble(text(label)); }
        catch (NumberFormatException e) { System.out.println("Please enter a valid number."); }
    }
    public static void pause() { text("Press Enter to continue..."); }
}
