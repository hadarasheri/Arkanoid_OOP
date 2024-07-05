package GameAssets;

/**
 * Defines a Counter using an int variable.
 */
public class Counter {
    private int count;

    /**
     * Contructor - sets the count to 0.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Adds number to current count.
     *
     * @param number int variable
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * subtract number from current count.
     *
     * @param number int variable
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * get current count.
     *
     * @return int variable
     */
    public int getValue() {
        return this.count;
    }
}
