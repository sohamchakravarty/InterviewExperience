using System;
using System.Collections.Generic;
using System.Text;

namespace SwiggyInterview.Models
{
    public class BookingSlotQueryModel : ParkingSlot
    {
        public Vehicle Vehicle { get; set; }
    }
}
