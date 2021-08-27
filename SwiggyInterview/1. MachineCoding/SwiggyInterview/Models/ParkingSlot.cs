using System;
using System.Collections.Generic;
using System.Linq;

namespace SwiggyInterview.Models
{
    public class ParkingSlot
    {
        public int X { get; set; }

        public int Y { get; set; }

        public override int GetHashCode()
        {
            return X.GetHashCode();
        }

        public override bool Equals(object obj)
        {
            var other = obj as ParkingSlot;
            return other != null && other.X == this.X && other.Y == this.Y;
        }
    }

    public class ParkingSlotValue
    {
        public ParkingSlotValue(VehicleCategory vehicleType)
        {
            this.TotalValue = 0;
            this.VehicleType = vehicleType;
            this.Vehicles = new List<Vehicle>();
        }

        public VehicleCategory VehicleType { get; set; }

        public int TotalValue { get; private set; }

        public IList<Vehicle> Vehicles { get; }

        public void AddVehicle(Vehicle vehicle)
        {
            if (!vehicle.VehicleType.Equals(VehicleType))
            {
                throw new ArgumentException();
            }

            this.Vehicles.Add(vehicle);
            this.TotalValue += GetVehicleValue();
        }

        public void RemoveVehicle(string vehicleRegNo)
        {
            var vehicle = Vehicles.SingleOrDefault(v => v.RegistrationNumber.Equals(vehicleRegNo));

            this.Vehicles.Remove(vehicle);
            this.TotalValue -= GetVehicleValue();
        }

        public bool IsSlotAvailable()
        {
            var slotValueRequired = GetVehicleValue();

            return (TotalValue + slotValueRequired) <= 4;
        }

        private int GetVehicleValue()
        {
            switch (VehicleType)
            {
                case VehicleCategory.Bike:
                    return 1;
                case VehicleCategory.Car:
                    return 2;
                default:
                    return 4;
            }
        }
    }
}
