using System;
using System.Collections.Generic;
using UdaanMachineCoding.Models;
using System.Linq;

namespace UdaanMachineCoding
{
    public class ParkingLotService : IParkingLotService
    {
        private IList<ParkingLot> parkingLots;
        private Dictionary<string, Booking> activeBookings;
        private Dictionary<string, IList<Booking>> vehicleBookingHistory;

        public ParkingLotService()
        {
            parkingLots = new List<ParkingLot>();
            activeBookings = new Dictionary<string, Booking>();
            vehicleBookingHistory = new Dictionary<string, IList<Booking>>();
        }

        public void RegisterParkingLot(ParkingLot parkingLot)
        {
            parkingLots.Add(parkingLot);
        }

        public VehicleParkingHistory GetParkingHistory(string vehicleRegNo)
        {
            var result = new VehicleParkingHistory();

            if (activeBookings.ContainsKey(vehicleRegNo))
            {
                result.ActiveBooking = activeBookings[vehicleRegNo];
            }

            if (vehicleBookingHistory.ContainsKey(vehicleRegNo))
            {
                result.BookingHistory = vehicleBookingHistory[vehicleRegNo];
            }

            return result;
        }

        public Booking ParkVehicle(string parkingLotLocation, string vehicleRegNo, string category, DateTime startTime)
        {
            var parkingLot = parkingLots.SingleOrDefault(p => p.Location.Equals(parkingLotLocation));
            var vehicleCategory = Enum.Parse<VehicleCategory>(category, true);

            parkingLot.ReserveSpot(vehicleCategory);

            var booking = new Booking(vehicleRegNo, vehicleCategory, parkingLotLocation, startTime);
            activeBookings.Add(vehicleRegNo, booking);

            return booking;
        }

        public void UnParkVehicle(string vehicleRegNo, DateTime endTime)
        {
            var booking = activeBookings[vehicleRegNo];

            var parkingLot = parkingLots.SingleOrDefault(p => p.Location.Equals(booking.ParkingLotLocation));

            var bookingDuration = (endTime - booking.StartTime).TotalHours;
            var rateOfBooking = parkingLot.UnreserveSpotAndGetRate(booking.VehicleCategory, bookingDuration);

            activeBookings.Remove(vehicleRegNo);
            booking.CloseBooking(endTime, rateOfBooking);

            if (!vehicleBookingHistory.TryGetValue(vehicleRegNo, out IList<Booking> bookingHistory))
            {
                bookingHistory = new List<Booking>();
                vehicleBookingHistory.Add(vehicleRegNo, bookingHistory);
            }

            bookingHistory.Add(booking);
        }
    }
}
