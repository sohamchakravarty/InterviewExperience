using SwiggyInterview.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace SwiggyInterview.Contracts
{
    public interface IParkingLot
    {
        ParkingSlot ParkVehicle(Vehicle vehicle, ParkingSlot entryPoint);

        void UnparkVehicle(string vehicleRegNo);

        IList<string> GetAllVehicles(string vehicleColour);

        ParkingSlot GetBookedSlot(string vehicleRegNo);

        IList<BookingSlotQueryModel> GetAllSlots(string vehicleColour);
    }
}
