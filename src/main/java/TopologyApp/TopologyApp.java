package TopologyApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

@RestController
public class TopologyApp  {

    @PostMapping("/readJSON/{top}")
    public static boolean readJSON( @RequestBody Topology top) throws IOException
    {
        boolean added = false;
        File file1 = new File("Topology.xlsx");   //creating a new file instance
        File file2 = new File("Component.xlsx");

        FileInputStream fis1 = new FileInputStream(file1);//obtaining bytes from the file
        FileInputStream fis2 = new FileInputStream(file2);//obtaining bytes from the file

        //creating Workbook instance that refers to .xlsx file
        XSSFWorkbook wb = new XSSFWorkbook(fis1);
        XSSFSheet sheet = wb.getSheetAt(0);

        ArrayList<Object> topology =new ArrayList<>();

        topology.add(top.getId());
        for(int i=0;i<top.getComponents().size();i++)
        {
            topology.add(top.getComponents().get(i).getId());
            topology.add(top.getComponents().get(i).getType());
        }
        Iterator<Row> itr = sheet.iterator();
        int rowCount = sheet.getLastRowNum();
        Row row = sheet.createRow(rowCount++);

            int columnCount = 0;
            for (Object field : topology) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        try (FileOutputStream outputStream = new FileOutputStream("Topology.xlsx")) {
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        wb = new XSSFWorkbook(fis2);
        sheet = wb.getSheetAt(0);

        rowCount = sheet.getLastRowNum();
        itr = sheet.iterator();
        for(int i=0;i<top.getComponents().size();i++)
        {
            ArrayList<Object> component = new ArrayList<>();
            component.add(top.getComponents().get(i).getId());
            component.add(top.getComponents().get(i).getType());
            if(top.getComponents().get(i).getType().equalsIgnoreCase("resistor"))
            {
                ResistorComponent.Netlist netlist = (ResistorComponent.Netlist) top.getComponents().get(i).getNetList();
                    component.add(netlist.t1);
                    component.add(netlist.t2);

            }
            else
            {
                nmosComponent.Netlist netlist = (nmosComponent.Netlist) top.getComponents().get(i).getNetList();
                component.add(netlist.drain);
                component.add(netlist.gate);
                component.add(netlist.source);
            }
            row = sheet.createRow(rowCount++);

            columnCount = 0;
            for (Object field : component) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
                else if(field instanceof Double) {
                    cell.setCellValue((Double) field);
                }
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream("Component.xlsx")) {
            wb.write(outputStream);
            added = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return added;
    }

    @GetMapping("writeJSON/{TopologyID}")
    public static Topology writeJSON(@PathVariable String TopologyID)
    {
        Topology top = null;
        try {
            File file1 = new File("Topology.xlsx");   //creating a new file instance
            File file2 = new File("Component.xlsx");

            FileInputStream fis1 = new FileInputStream(file1);
            FileInputStream fis2 = new FileInputStream(file2);
            boolean foundId=false;

            XSSFWorkbook wb = new XSSFWorkbook(fis1);
            XSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> itr = sheet.iterator();
            Row row;
            Iterator<Cell> cellIterator;
            ArrayList<String> componentId = new ArrayList<>();
            ArrayList<String> componentType = new ArrayList<>();
            while (itr.hasNext()) {
                row = itr.next();
                cellIterator = row.cellIterator();   //iterating over each column
                for (int i = 0; cellIterator.hasNext(); i++) {
                    Cell cell = cellIterator.next();
                    if (i == 0) {
                        if(TopologyID.equalsIgnoreCase(cell.getStringCellValue()))
                            continue;
                        else
                            break;

                    }
                        componentId.add( cell.getStringCellValue());
                        cell = cellIterator.next();
                        componentType.add(cell.getStringCellValue());

                }
            }
            ArrayList<TopologyComponent> components = new ArrayList<>();
            XSSFWorkbook wb1 = new XSSFWorkbook(fis2);
            XSSFSheet sheet1 = wb1.getSheetAt(0);
            for (int i = 0; i < componentId.size(); i++)
            {
                Iterator<Row> itr1 = sheet1.iterator();
                while (itr1.hasNext()) {
                    row = itr1.next();
                    cellIterator = row.cellIterator();
                    ArrayList<String> netlist = new ArrayList<>();
                    for (int j= 0; cellIterator.hasNext(); j++) {
                        Cell cell = cellIterator.next();
                        if (j == 0) {
                            if(componentId.get(i).equalsIgnoreCase(cell.getStringCellValue()))
                                continue;
                            else
                                break;
                        }
                        else if(j==1)
                        {
                            if(componentType.get(i).equalsIgnoreCase(cell.getStringCellValue())) {
                                foundId = true;
                                continue;
                            }
                            else
                                break;
                        }
                        else
                        {
                            netlist.add(cell.getStringCellValue());
                        }

                    }
                    if(foundId==true)
                    {
                        if (componentType.get(i).equalsIgnoreCase("resistor"))
                            components.add(new ResistorComponent(componentId.get(i), netlist));
                        else
                            components.add(new nmosComponent(componentId.get(i), netlist));
                        foundId=false;
                        break;
                    }
                }
            }
            top = new Topology(TopologyID,components);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return top;
    }

    @GetMapping("queryTopologies")
    public static ArrayList<Topology> queryTopologies() throws IOException
    {
        File file1 = new File("Topology.xlsx");   //creating a new file instance
        File file2 = new File("Component.xlsx");

        FileInputStream fis1 = new FileInputStream(file1);//obtaining bytes from the file

        ArrayList<Topology> topologies = new ArrayList<>();

        //creating Workbook instance that refers to .xlsx file
        XSSFWorkbook wb = new XSSFWorkbook(fis1);
        XSSFSheet sheet = wb.getSheetAt(0);
        String topID;
        Iterator<Row> itr = sheet.iterator();
        while (itr.hasNext())
        {
            Row row = itr.next();
            Iterator<Cell> cellIterator = row.cellIterator();;
            Cell cell = cellIterator.next();
            if( !cell.getStringCellValue().equalsIgnoreCase(""))
            {
                topID= cell.getStringCellValue();
                topologies.add(writeJSON(topID));
            }
        }
        return topologies;
    }

    @PostMapping("deleteTopology/{TopologyID}")
    public static boolean deleteTopology(@PathVariable String TopologyID) throws IOException
    {
        File file1 = new File("Topology.xlsx");   //creating a new file instance
        File file2 = new File("Component.xlsx");

        boolean deleted = false;

        FileInputStream fis1 = new FileInputStream(file1);
        FileInputStream fis2 = new FileInputStream(file2);
        boolean foundId = false;
        boolean compfound = false;

        XSSFWorkbook wb = new XSSFWorkbook(fis1);
        XSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> itr = sheet.iterator();
        Row row = null;
        Iterator<Cell> cellIterator;
        ArrayList<String> componentId = new ArrayList<>();
        ArrayList<String> componentType = new ArrayList<>();
        while (itr.hasNext() && !foundId) {
            row = itr.next();
            cellIterator = row.cellIterator();   //iterating over each column
            for (int i = 0; cellIterator.hasNext(); i++) {
                Cell cell = cellIterator.next();
                if (i == 0) {
                    if (TopologyID.equalsIgnoreCase(cell.getStringCellValue())) {
                        foundId = true;
                        continue;
                    } else
                        break;
                }
                if (i % 2 == 1) {
                    componentId.add( cell.getStringCellValue());
                } else if (i % 2 == 0) {
                    componentType.add(cell.getStringCellValue());
                }
            }
        }
        if (foundId) {
            sheet.removeRow(row);
        }

        try (FileOutputStream outputStream = new FileOutputStream("Topology.xlsx")) {
            wb.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        wb = new XSSFWorkbook(fis2);
        sheet = wb.getSheetAt(0);
        for (int i = 0; i < componentId.size(); i++) {
            itr = sheet.iterator();
            row = itr.next();
            while (true)
            {
                cellIterator = row.cellIterator();
                Cell cell = cellIterator.next();
                if (!componentId.get(i).equalsIgnoreCase(cell.getStringCellValue())) {
                    if (itr.hasNext())
                    {
                        row = itr.next();
                        continue;
                    }
                    break;
                }
                cell = cellIterator.next();
                if(componentType.get(i).equalsIgnoreCase(cell.getStringCellValue()))
                {
                    sheet.removeRow(row);
                    break;
                }
                else
                {
                    if (itr.hasNext())
                    {
                        row = itr.next();
                        continue;
                    }
                    break;
                }
            }

            }
            try (FileOutputStream outputStream = new FileOutputStream("Component.xlsx"))
            {
                wb.write(outputStream);
                deleted=true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return deleted;
    }

    @GetMapping("queryDevices/{TopologyID}")
    public static ArrayList<TopologyComponent>  queryDevices(@PathVariable String TopologyID)
    {
        return (writeJSON(TopologyID).getComponents());
    }

    @GetMapping("queryDevicesWithNetlistNode/{TopologyID}/{NetlistNodeName}")
    static ArrayList<TopologyComponent> queryDevicesWithNetlistNode(@PathVariable String TopologyID, @PathVariable String NetlistNodeName)
    {
        ArrayList<TopologyComponent> componentsWithNetlistNode = new ArrayList<>();
        ArrayList<TopologyComponent> components = queryDevices(TopologyID);
        for(TopologyComponent comp:components)
        {
            if(comp.getType().equalsIgnoreCase("resistor"))
            {
                ResistorComponent.Netlist netlist = (ResistorComponent.Netlist) comp.getNetList();
                if(netlist.t1.equalsIgnoreCase(NetlistNodeName)||netlist.t2.equalsIgnoreCase(NetlistNodeName))
                {
                    componentsWithNetlistNode.add(comp);
                }
            }
            else if(comp.getType().equalsIgnoreCase("nmos"))
            {
                nmosComponent.Netlist netlist = (nmosComponent.Netlist) comp.getNetList();
                if(netlist.drain.equalsIgnoreCase(NetlistNodeName)||netlist.gate.equalsIgnoreCase(NetlistNodeName)||netlist.source.equalsIgnoreCase(NetlistNodeName))
                {
                    componentsWithNetlistNode.add(comp);
                }
            }
        }
        return componentsWithNetlistNode;
    }
}
