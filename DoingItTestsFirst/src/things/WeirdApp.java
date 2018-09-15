package things;

/**
 * Just providing a main method for manual testing
 */
public class WeirdApp {
    public static void main(String[] args) {
        int[] integersToSum = new int[] {2, 5, -4, 10};
        try {
            System.out.println(WeirdClass.sumUnderConditions("Please", integersToSum, "Thanks"));
        } catch (Exception e) {
            System.out.println("Oops, exception occurred! " + e.getMessage());
            e.printStackTrace();
        }
    }
}
