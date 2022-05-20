package TopologyApp;

import java.util.ArrayList;

public class nmosComponent extends TopologyComponent {

    public static class Netlist implements NetList {

        public String drain;
        public String gate;
        public String source;
    }

    private String type;
    private String id;
    private M m;
    private nmosComponent.Netlist netlist;

    public nmosComponent(String id)
    {
        super(id,"nmos");
        m = new M();
        netlist = new nmosComponent.Netlist();
    }

    public nmosComponent(String id,  ArrayList<String>net)
    {
        super(id,"nmos");
        netlist = new nmosComponent.Netlist();
        m = new M();
        netlist.drain = net.get(0);
        netlist.gate = net.get(1);
        netlist.source = net.get(2);
    }


    public Power getM()
    {
        return m;
    }

    @Override
    public nmosComponent.Netlist getNetList() {
        return netlist;
    }
}
