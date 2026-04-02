using System;
using System.Collections.Generic;
using System.Text;

namespace SwiggyLLD.Models
{
    public class DictValue
    {
        public DictValue(string value, DateTime? timeToLive = null)
        {
            this.Value = value;
            TimeToLive = timeToLive;
        }

        public string Value { get; set; }

        public DateTime? TimeToLive { get; set; }
    }
}
