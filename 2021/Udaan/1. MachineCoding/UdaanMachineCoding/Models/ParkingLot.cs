using System;
using System.Collections.Generic;
using System.Linq;

namespace UdaanMachineCoding.Models
{
    public class ParkingLot
    {
        public ParkingLot(string location, IList<VehicleType> vehicleTypes)
        {
            this.Location = location;
            this.VehicleTypes = vehicleTypes;
        }

        public string Location { get; }

        public IList<VehicleType> VehicleTypes { get; private set; }

        public void ReserveSpot(VehicleCategory vehicleCategory)
        {
            var vehicletype = VehicleTypes.SingleOrDefault(vt => vt.Category.Equals(vehicleCategory));

            if (vehicletype.AvailableSpots == 0)
            {
                throw new ArgumentException($"No parking available for category: {vehicleCategory}");
            }

            vehicletype.AvailableSpots--;
        }

        public int UnreserveSpotAndGetRate(VehicleCategory vehicleCategory, double durationInHours)
        {
            var vehicletype = VehicleTypes.SingleOrDefault(vt => vt.Category.Equals(vehicleCategory));

            if (vehicletype.AvailableSpots == vehicletype.MaxSpots)
            {
                throw new ArgumentException($"All parkings are empty for category: {vehicleCategory}");
            }

            vehicletype.AvailableSpots++;

            //1,10 2,20 6,50 24,70 
            var vehicleRate = vehicletype.Rates.FirstOrDefault(r => durationInHours <= r.DurationInHours);

            int rate = 0;
            if (vehicleRate == null)
            {
                var lastRate = vehicletype.Rates.LastOrDefault();
                var totalDays = (int) Math.Ceiling(durationInHours / (double) lastRate.DurationInHours);
                rate = lastRate.Rate * totalDays;
            }
            else
            {
                rate = vehicleRate.Rate;
            }

            return rate;
        }
    }
}
