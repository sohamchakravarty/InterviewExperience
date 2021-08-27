using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Text;

namespace SwiggyLLD.Models
{
    public class HeapNodeComparer : IComparer<HeapNode>
    {
        public int Compare([AllowNull] HeapNode x, [AllowNull] HeapNode y)
        {
            int result = 0;

            if (x.DictValue.TimeToLive != null && y.DictValue.TimeToLive != null)
            {
                result = x.DictValue.TimeToLive.Value.CompareTo(y.DictValue.TimeToLive.Value);
            }

            else if (result == 0)
            {
                result = x.DictKey.CompareTo(y.DictKey);
            }

            return result;
        }
    }

    public class HeapNode
    {
        public string DictKey { get; set; }

        public DictValue DictValue { get; set; }
    }
}
