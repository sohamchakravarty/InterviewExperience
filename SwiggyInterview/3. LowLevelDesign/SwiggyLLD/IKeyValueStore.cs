using SwiggyLLD.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace SwiggyLLD
{
    public interface IKeyValueStore
    {
        void PutKey(string key, string value, DateTime? timeToLive = null);

        DictValue GetKey(string key);
    }

    interface IEvict
    {
        void EvictKeys();
    }
}
