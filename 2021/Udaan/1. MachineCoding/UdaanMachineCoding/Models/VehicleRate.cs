namespace UdaanMachineCoding.Models
{
    public class VehicleRate
    {
        public VehicleRate(int duration, int rate)
        {
            this.DurationInHours = duration;
            this.Rate = rate;
        }

        public int DurationInHours { get; set; }

        public int Rate { get; set; }
    }
}
