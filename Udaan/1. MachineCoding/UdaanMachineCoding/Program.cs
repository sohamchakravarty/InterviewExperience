using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using UdaanMachineCoding.Models;

namespace UdaanMachineCoding
{
    class Program
    {
        private static IServiceProvider serviceProvider;

        static void Main(string[] args)
        {
            ConfigureServices();

            var parkingLotService = serviceProvider.GetRequiredService<IParkingLotService>();

            var rateCard1 = new List<VehicleRate>()
            {
                new VehicleRate(2, 20),
                new VehicleRate(6, 50),
                new VehicleRate(24, 80)
            };
            var rateCard2 = new List<VehicleRate>()
            {
                new VehicleRate(1, 30),
                new VehicleRate(6, 70),
                new VehicleRate(24, 100)
            };
            var rateCard3 = new List<VehicleRate>()
            {
                new VehicleRate(1, 40),
                new VehicleRate(6, 60),
                new VehicleRate(24, 120)
            };

            var vt1 = new List<VehicleType>() 
            {
                new VehicleType("bike", rateCard1, 2),
                new VehicleType("car", rateCard2, 1)
            };

            var vt2 = new List<VehicleType>()
            {
                new VehicleType("bike", rateCard1, 5),
                new VehicleType("car", rateCard3, 2)
            };

            var p1 = new ParkingLot("Madhapur", vt1);
            var p2 = new ParkingLot("Koramangala", vt2);

            parkingLotService.RegisterParkingLot(p1);
            parkingLotService.RegisterParkingLot(p2);

            var currentTime = DateTime.UtcNow;
            var b1 = parkingLotService.ParkVehicle("Madhapur", "TS-02-0001", "bike", currentTime);
            var b2 = parkingLotService.ParkVehicle("Madhapur", "TS-02-0002", "bike", currentTime.AddHours(1));
            //parkingLotService.ParkVehicle("Madhapur", "TS-02-0003", "bike", currentTime.AddHours(2)); //error
            var b3 = parkingLotService.ParkVehicle("Madhapur", "TS-02-0004", "car", currentTime.AddHours(3));

            parkingLotService.UnParkVehicle("TS-02-0001", currentTime.AddHours(6));
            parkingLotService.UnParkVehicle("TS-02-0002", currentTime.AddDays(2));
        }

        static void ConfigureServices()
        {
            var collection = new ServiceCollection()
                .AddScoped<IParkingLotService, ParkingLotService>();

            serviceProvider = collection.BuildServiceProvider();
        }
    }
}
