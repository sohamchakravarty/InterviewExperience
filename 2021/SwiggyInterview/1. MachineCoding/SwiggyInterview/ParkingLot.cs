using SwiggyInterview.Contracts;
using SwiggyInterview.Models;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SwiggyInterview
{
    public class ParkingLot : IParkingLot
    {
        Dictionary<ParkingSlot, ParkingSlotValue> bookingHistory;
        Dictionary<string, ParkingSlot> vehicleParkingSlot;
        int totalStories;

        public ParkingLot(int totalStories)
        {
            bookingHistory = new Dictionary<ParkingSlot, ParkingSlotValue>();
            vehicleParkingSlot = new Dictionary<string, ParkingSlot>();
            this.totalStories = totalStories;
        }

        public ParkingSlot ParkVehicle(Vehicle vehicle, ParkingSlot parkingSlot = null)
        {
            if (vehicleParkingSlot.ContainsKey(vehicle.RegistrationNumber))
            {
                throw new ArgumentException($"Vehicle {vehicle.RegistrationNumber} is already parked at {vehicleParkingSlot[vehicle.RegistrationNumber]}");
            }

            ParkingSlot nearestAvailableSlot;

            if (parkingSlot != null && CheckIfValidParkingSlot(parkingSlot, vehicle.VehicleType))
            {
                nearestAvailableSlot = parkingSlot;
            }
            else
            {
                nearestAvailableSlot = GetNearestAvailableSlot(vehicle.VehicleType, parkingSlot);
                if (nearestAvailableSlot == null)
                {
                    throw new InvalidOperationException("Parking Lot is full");
                }
            }

            if (!bookingHistory.TryGetValue(nearestAvailableSlot, out ParkingSlotValue bookingValue))
            {
                bookingValue = new ParkingSlotValue(vehicle.VehicleType);
                bookingHistory.Add(nearestAvailableSlot, bookingValue);
            }
            bookingValue.AddVehicle(vehicle);

            vehicleParkingSlot.Add(vehicle.RegistrationNumber, nearestAvailableSlot);

            return nearestAvailableSlot;
        }

        public void UnparkVehicle(string vehicleRegNo)
        {
            if (!vehicleParkingSlot.TryGetValue(vehicleRegNo, out ParkingSlot parkingSlot))
            {
                throw new ArgumentException($"No details found for vehicle {vehicleRegNo}");
            }

            var bookingValue = bookingHistory[parkingSlot];
            bookingValue.RemoveVehicle(vehicleRegNo);
            vehicleParkingSlot.Remove(vehicleRegNo);
        }

        public IList<string> GetAllVehicles(string vehicleColour)
        {
            var query =
                bookingHistory.Values
                    .SelectMany(b => b.Vehicles)
                    .Where(v => v.VehicleColour.Equals(vehicleColour, StringComparison.OrdinalIgnoreCase))
                    .Select(v => v.RegistrationNumber);

            return query.ToList();
        }

        public IList<BookingSlotQueryModel> GetAllSlots(string vehicleColour)
        {
            var result = new List<BookingSlotQueryModel>();
            foreach (var kvp in bookingHistory)
            {
                var query = kvp.Value.Vehicles.Where(v => v.VehicleColour.Equals(vehicleColour, StringComparison.OrdinalIgnoreCase));
                foreach(var e in query)
                {
                    var model = new BookingSlotQueryModel()
                    {
                        X = kvp.Key.X,
                        Y = kvp.Key.Y,
                        Vehicle = e
                    };

                    result.Add(model);
                }
            }

            return result;
        }

        public ParkingSlot GetBookedSlot(string vehicleRegNo)
        {
            if (!vehicleParkingSlot.ContainsKey(vehicleRegNo))
            {
                throw new ArgumentException($"Vehicle {vehicleRegNo} is not found");
            }

            return vehicleParkingSlot[vehicleRegNo];
        }

        private ParkingSlot GetNearestAvailableSlot(VehicleCategory vehicleType, ParkingSlot parkingSlot = null)
        {
            int i = 0, currj = 0;
            if (parkingSlot != null)
            {
                i = parkingSlot.X;
                currj = parkingSlot.Y;
            }

            for (; i < totalStories; i++)
            {
                for (int j = currj; j < totalStories; j++)
                {
                    var slot = new ParkingSlot() { X = i, Y = j };
                    if (!bookingHistory.ContainsKey(slot))
                    {
                        return slot;
                    }

                    if (bookingHistory[slot].VehicleType.Equals(vehicleType) 
                        && bookingHistory[slot].IsSlotAvailable())
                    {
                        return slot;
                    }
                }

                currj = 0;
            }

            return null;
        }

        private bool CheckIfValidParkingSlot(ParkingSlot parkingSlot, VehicleCategory vehicleType)
        {
            if (parkingSlot.X < 0 || parkingSlot.X >= totalStories)
            {
                return false;
            }

            return true;
        }
    }
}
