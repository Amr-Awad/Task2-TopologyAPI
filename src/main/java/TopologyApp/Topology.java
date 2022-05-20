package TopologyApp;

import java.util.ArrayList;

public class Topology {
    private String id;
    private ArrayList<TopologyComponent> components;



    public Topology()
    {
    }

    public Topology(String id) {
        this.id = id;
    }

    public Topology(String id, ArrayList<TopologyComponent> components) {
        this.id = id;
        this.components = components;
    }

    public ArrayList<TopologyComponent> getComponents() {
        return components;
    }

    public void AddComponent(TopologyComponent component)
    {
        components.add(component);
    }

    public boolean DeleteComponent(String type,String id)
    {
        TopologyComponent component = searchComponent(type,id);
        if (component==null)
            return false;
        components.remove(component);
        return true;

    }

    public TopologyComponent searchComponent(String type, String id)
    {
        for(int i=0 ;i<components.size();i++)
        {
            if(components.get(i).getId().equalsIgnoreCase(id)&&components.get(i).getType().equalsIgnoreCase(type))
            {
                return components.get(i);
            }
        }
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
}
