using System;
using System.Collections.Generic;
using UdaanMachineCoding.Models;

namespace UdaanMachineCoding
{
    public interface IParkingLotService
    {
        void RegisterParkingLot(ParkingLot parkingLot);

        Booking ParkVehicle(string parkingLotLocation, string vehicleRegNo, string vehicleCategory, DateTime startTime);

        void UnParkVehicle(string vehicleRegNo, DateTime endTime);

        VehicleParkingHistory GetParkingHistory(string vehicleRegNo);
    }
}
