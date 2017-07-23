import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);



        System.out.println("input vin");
        int vinInput = scanner.nextInt();

        System.out.println("input miles");
        double milesInput = scanner.nextDouble();

        System.out.println("input consuption");
        double consuptionInput = scanner.nextDouble();

        System.out.println("input oil");
        double oilInput = scanner.nextDouble();

        System.out.println("input engine");
        double engineInput = scanner.nextDouble();

        VehicleInfo vehicleInfo = new VehicleInfo();

        vehicleInfo.setVIN(vinInput);

        vehicleInfo.setOdometerMiles(milesInput);

        vehicleInfo.setConsumption(consuptionInput);

        vehicleInfo.setOdometerOil(oilInput);

        vehicleInfo.setEngine(engineInput);

        TelematicsService telematicsService = new TelematicsService();
        telematicsService.report(vehicleInfo);










    }







}
