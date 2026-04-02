using System;
using System.Collections.Generic;

namespace UdaanMachineCoding.Models
{
    public class VehicleType
    {
        public VehicleType(string category, IList<VehicleRate> rates, int totalSpots)
        {
            this.Category = Enum.Parse<VehicleCategory>(category, true);
            this.Rates = rates;
            this.AvailableSpots = totalSpots;
            this.MaxSpots = totalSpots;
        }

        public VehicleCategory Category { get; }

        public int AvailableSpots { get; set; }

        public int MaxSpots { get; }

        public IList<VehicleRate> Rates { get; set; }
    }
}
