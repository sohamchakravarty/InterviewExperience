using System.Collections.Generic;

namespace UdaanMachineCoding.Models
{
    public class VehicleParkingHistory
    {
        public Booking ActiveBooking { get; set; }

        public IList<Booking> BookingHistory { get; set; }
    }
}
