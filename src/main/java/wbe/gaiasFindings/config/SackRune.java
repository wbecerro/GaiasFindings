package wbe.gaiasFindings.config;

import wbe.gaiasFindings.items.MenuRune;

public class SackRune {

    private Rune rune;

    private int amount;

    public SackRune(Rune rune, int amount) {
        this.rune = rune;
        this.amount = amount;
    }

    public Rune getRune() {
        return rune;
    }

    public void setRune(Rune rune) {
        this.rune = rune;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MenuRune getMenuItem() {
        return new MenuRune(this);
    }
}
