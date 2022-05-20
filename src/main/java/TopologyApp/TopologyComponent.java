package TopologyApp;

import java.util.ArrayList;

public class TopologyComponent {
    String type;
    String id;
    ResistorComponent.Netlist netlist1 = new ResistorComponent.Netlist();
    nmosComponent.Netlist netlist2 = new nmosComponent.Netlist();
    TopologyComponent()
    {

    }
    TopologyComponent(String id,String type)
    {
        this.id = id;
        this.type = type;
    }
    TopologyComponent(String id,String type,NetList netlist)
    {
        this.id = id;
        this.type = type;
        if(type.equalsIgnoreCase("resistor"))
        {
            netlist1 = (ResistorComponent.Netlist) netlist;
        }
        else
        {
            netlist2 = (nmosComponent.Netlist) netlist;
        }
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNetlist(NetList netlist)
    {
        if(type.equalsIgnoreCase("resistor"))
        {
            netlist1 = (ResistorComponent.Netlist) netlist;
        }
        else
        {
            netlist2 = (nmosComponent.Netlist) netlist;
        }
    }
    public NetList getNetList(){
        if(type.equalsIgnoreCase("resistor"))
        {
            return netlist1;
        }
        else
        {
            return netlist2;
        }
    }

}
