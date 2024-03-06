package code.model;

public class CartModel {

    private static int cartSpeed = 1;

    private static boolean gate = true;


    public static int getCartSpeed() {
        return cartSpeed;
    }

    public static void setCartSpeed(int cartSpeed) {
        CartModel.cartSpeed = cartSpeed;
    }

    public static boolean isGate() {
        return gate;
    }

    public static void setGate(boolean gate) {
        CartModel.gate = gate;
    }
}
