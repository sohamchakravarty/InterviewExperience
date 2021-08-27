using SwiggyInterview.Models;
using System;

namespace SwiggyInterview
{
    /**
     * Full Question
     * https://leetcode.com/discuss/interview-question/1422797/swiggy-machine-coding-round-sde-4-parking-lot-system/1059211
     **/
    class Program
    {
        static void Main(string[] args)
        {
            var parkingLot = new ParkingLot(6);

            parkingLot.ParkVehicle(new Vehicle("KA-01-HH-1234", "car", "white"), new ParkingSlot() { X=0, Y=0 });
            parkingLot.ParkVehicle(new Vehicle("KA-01-HH-1454", "car", "white"), new ParkingSlot() { X = 0, Y = 0 });
            parkingLot.UnparkVehicle("KA-01-HH-1454");
            parkingLot.ParkVehicle(new Vehicle("KA-01-HH-9999", "bike", "Green"));
            parkingLot.ParkVehicle(new Vehicle("KA-01-HH-4323", "bike", "black"));
            parkingLot.ParkVehicle(new Vehicle("KA-01-HH-2345", "bus", "White"));
            parkingLot.ParkVehicle(new Vehicle("KA-01-HH-7345", "car", "white"), new ParkingSlot() { X = 5, Y = 5 });
            parkingLot.ParkVehicle(new Vehicle("KA-01-HH-8746", "car", "yellow"), new ParkingSlot() { X = 4, Y = 4 });

            var bookedSlot = parkingLot.GetBookedSlot("KA-01-HH-1234");
            Console.WriteLine($"[{bookedSlot.X}, {bookedSlot.Y}]");

            bookedSlot = parkingLot.GetBookedSlot("KA-01-HH-9999");
            Console.WriteLine($"[{bookedSlot.X}, {bookedSlot.Y}]");

            var vehicles = parkingLot.GetAllVehicles("white");
            Console.WriteLine($"Vehicles of color 'white': {string.Join(", ", vehicles)}");

            var slots = parkingLot.GetAllSlots("white");
            Console.WriteLine($"Slots of color 'white':");
            foreach (var slot in slots)
            {
                Console.WriteLine($"{slot.Vehicle.RegistrationNumber}, {slot.Vehicle.VehicleType}, {slot.Vehicle.VehicleColour} - [{slot.X}, {slot.Y}]");
            }

            Console.ReadLine();
        }
    }
}
