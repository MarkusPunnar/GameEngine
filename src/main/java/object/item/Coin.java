package object.item;

import java.io.IOException;

public class Coin extends Item {

    private int amount;

    public Coin(int amount) throws IOException {
        super(new Icon("coin"));
        this.amount = amount;
    }

    public boolean isStackable() {
        return true;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public float getPaddingX() {
        return 0.01f;
    }

    @Override
    public float getPaddingY() {
        return 0.025f;
    }

}
