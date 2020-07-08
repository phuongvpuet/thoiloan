package model.common;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class Resource {
    public static final short GOLD_TYPE = 0;
    public static final short ELIXIR_TYPE = 1;
    public static final short DARK_ELIXIR_TYPE = 2;

    public int resourceType;
    public int quantity;

    public Resource(int resourceType) {
        this.resourceType = resourceType;
        this.quantity = 0;
    }
}
