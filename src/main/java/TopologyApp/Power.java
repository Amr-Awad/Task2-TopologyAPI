package TopologyApp;

public abstract class Power {

    protected double Default;
    protected double min;
    protected double max;

    public Power() {
    }

    public Power(double defaultPower, double minPower, double maxPower) {
        this.Default = defaultPower;
        this.min = minPower;
        this.max = maxPower;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getDefault() {
        return Default;
    }

    public void setDefault(double Default) {
        this.Default = Default;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
