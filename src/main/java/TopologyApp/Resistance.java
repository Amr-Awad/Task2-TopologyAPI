package TopologyApp;

public class Resistance extends Power {
    public Resistance() {
        super(100,10,1000);
    }

    public double getDefault() {
        return Default;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}
