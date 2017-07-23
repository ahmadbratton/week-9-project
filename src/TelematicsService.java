import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.lang.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;




public class TelematicsService {
    String json;
    String name;
    int line =1;
    VehicleInfo theInfo;
    public static ArrayList<VehicleInfo> createdInfo = new ArrayList<VehicleInfo>();
    double totalMiles;
    double totalConsumption;
    double totalOil;
    double totalEngine;
    double averageMiles;
    double averageConsumption;
    double averageOil;
    double averageEngine;
    boolean tabledata = false;






    void report(VehicleInfo vehicleInfo){

        ObjectMapper mapper = new ObjectMapper();
        try {
            name = vehicleInfo.getVIN() + ".json";
             mapper.writeValue(new File(name), vehicleInfo );
        } catch (IOException ex) {
            ex.printStackTrace();
        }





        File file = new File(".");
        for (File f : file.listFiles()) {
            if (f.getName().endsWith(".json")) {
                try {
                    ObjectMapper getmapper = new ObjectMapper();
                    theInfo = getmapper.readValue(f, VehicleInfo.class);
                    createdInfo.add(theInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }



                try {
                    Scanner fileScanner = new Scanner(f);
                    List<String> fileContents = new ArrayList();
                    while (fileScanner.hasNext()) {
                        fileContents.add(fileScanner.nextLine());
                    }
                    json =  fileContents.toArray(new String[0])[0]; //Converts the list to an array
                    System.out.println("successfully created json here: " + json);
                }catch (FileNotFoundException ex) {
                    System.out.println("Could not find file *" + json + "*");
                    ex.printStackTrace();
                }
            }
        }




        for (VehicleInfo info : createdInfo ){
            totalMiles += info.getOdometerMiles();
            totalConsumption += info.getConsumption();
            totalOil += info.getOdometerOil();
            totalEngine += info.getEngine();
            averageMiles = totalMiles / createdInfo.size();
            averageConsumption = totalConsumption / createdInfo.size();
            averageOil = totalOil / createdInfo.size();
            averageEngine = totalEngine / createdInfo.size();
        }

        String averageMilesForm = String.format("%.1f", averageMiles);
        String averageConsForm = String.format("%.1f", averageConsumption);
        String averageOilForm = String.format("%.1f", averageOil);
        String averageEngineForm = String.format("%.1f", averageEngine);

        System.out.println("the average miles is: " + averageMilesForm);
        System.out.println("the average consumption is: " + averageConsForm);
        System.out.println("the average oil is: " + averageOilForm);
        System.out.println("the average engine size  is: " + averageEngineForm);


        File htmlFile = new File("dashboard.html");
            String[] htmlArray = null;
            try {
                Scanner htmlScanner = new Scanner(htmlFile);
                List<String> htmlContents = new ArrayList();
                while(htmlScanner.hasNext()){
                    htmlContents.add(htmlScanner.nextLine());
                }
                htmlArray = htmlContents.toArray(new String[0]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }










        Document htmlPage = null;

        try {
            htmlPage = Jsoup.parse(new File("dashboard.html"), "UTF-8");

        }

     catch (IOException e) {
        e.printStackTrace();
    }
        Element mileavg = htmlPage.select("#miles").first();
        Element consavg = htmlPage.select("#consumption").first();
        Element oilavg = htmlPage.select("#oil").first();
        Element engineavg = htmlPage.select("#engine").first();
        Element historyTable = htmlPage.select("#history").first();



                historyTable.html("");
        Element headerRow = historyTable.appendElement("tr");
        Element vinHeader = headerRow.appendElement("th");
        Element milesHeader = headerRow.appendElement("th");
        Element consHeader = headerRow.appendElement("th");
        Element oilHeader = headerRow.appendElement("th");
        Element engineHeader = headerRow.appendElement("th");
                vinHeader.html("<th>VIN</th>");
                milesHeader.html("<th>Odometer (miles)</th>");
                consHeader.html("<th>Consumption (gallons)</th>");
                oilHeader.html("<th>Last Oil Change</th>");
                engineHeader.html("<th>Engine Size (liters)</th>");

                for (int i = 0; i < createdInfo.size(); i++) {

                    Element tableRow = historyTable.appendElement("tr");
                    Element vinData = tableRow.appendElement("td");
                    Element milesData = tableRow.appendElement("td");
                    Element consData = tableRow.appendElement("td");
                    Element oilData = tableRow.appendElement("td");
                    Element engineData = tableRow.appendElement("td");
                    vinData.html("<td align=\"center\">" + createdInfo.get(i).getVIN() + "</td>");
                    milesData.html("<td align=\"center\">" + String.format("%.1f", createdInfo.get(i).getOdometerMiles()) + "</td>");
                    consData.html("<td align=\"center\">" + String.format("%.1f", createdInfo.get(i).getConsumption()) + "</td>");
                    oilData.html("<td align=\"center\">" + String.format("%.1f", createdInfo.get(i).getOdometerOil()) + "</td>");
                    engineData.html("<td align=\"center\">" + String.format("%.1f", createdInfo.get(i).getEngine()) + "</td>");
                }










        mileavg.html("<td id=\"miles\" align=\"center\">" + averageMilesForm + "</td>");
        consavg.html("<td id=\"consumption\" align=\"center\">" + averageConsForm + "</td>");
        oilavg.html("<td id=\"oil\" align=\"center\">" + averageOilForm + "</td>");
        engineavg.html("<td id=\"engine\" align=\"center\">" + averageEngineForm + "</td>");

        System.out.println("this is mile averge line " + mileavg);
        String html = htmlPage.html();

//        String title = htmlPage.title();
//        System.out.println("title : " + title);



        try {
            FileWriter htmlWriter = new FileWriter(htmlFile);
            htmlWriter.write(html);
            htmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(htmlArray == null){
            System.out.println("OHHH no the array is null");
        }
        else{
            for(int i = 0 ; i < htmlArray.length ; i++){
                System.out.print("this is line"+ line++);
                System.out.println(htmlArray[i]);
            }
        }






    }


    }







