using System;
using System.Collections.Generic;
using System.Text;

namespace SwiggyInterview.Models
{
    public class Vehicle
    {
        public Vehicle(string regNo, string vehicleType, string vehicleColour)
        {
            RegistrationNumber = regNo;
            
            Enum.TryParse(vehicleType, true, out VehicleCategory type);
            VehicleType = type;

            VehicleColour = vehicleColour;
        }

        public string RegistrationNumber { get; set; }

        public VehicleCategory VehicleType { get; set; }

        public string VehicleColour { get; set; }
    }
}
