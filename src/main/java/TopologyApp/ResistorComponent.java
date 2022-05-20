package TopologyApp;

import java.util.ArrayList;

public class ResistorComponent extends TopologyComponent {

    public static class Netlist implements NetList {
        public String t1;
        public String t2;
    }


    private Power resistance;
    private ResistorComponent.Netlist netlist;


    public ResistorComponent(String id, ArrayList<String>net) {
        super(id,"resistor");
        netlist = new ResistorComponent.Netlist();
        resistance = new Resistance();
        netlist.t1 = net.get(0);
        netlist.t2 = net.get(1);
    }

    public Power getResistance()
    {
        return resistance;
    }

    @Override
    public ResistorComponent.Netlist getNetList() {
        return netlist;
    }
}
