using System;

namespace UdaanMachineCoding.Models
{
    public class Booking
    {
        public Booking(string vehicleNo, VehicleCategory category, string location, DateTime startTime)
        {
            this.BookingId = Guid.NewGuid();
            this.VehicleRegNo = vehicleNo;
            this.VehicleCategory = category;
            this.ParkingLotLocation = location;
            this.StartTime = startTime;
            this.Status = BookingStatus.Active;
        }

        public Guid BookingId { get; set; }

        public string VehicleRegNo { get; }

        public VehicleCategory VehicleCategory { get; }

        public string ParkingLotLocation { get; }

        public DateTime StartTime { get; }

        public DateTime? EndTime { get; private set; }

        public int TotalAmountPaid { get; private set; }

        public BookingStatus Status { get; private set; }

        public void CloseBooking(DateTime endTime, int totalAmount)
        {
            this.EndTime = endTime;
            this.TotalAmountPaid = totalAmount;
            this.Status = BookingStatus.Completed;
        }
    }
}
