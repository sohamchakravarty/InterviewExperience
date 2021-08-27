using SwiggyLLD.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace SwiggyLLD
{
    public class KeyValueStore : IKeyValueStore
    {
        Dictionary<string, DictValue> store;
        SortedSet<HeapNode> lruKeys;
        int capacity;

        public KeyValueStore(int capacity)
        {
            this.capacity = capacity;
            store = new Dictionary<string, DictValue>();
            lruKeys = new SortedSet<HeapNode>(new HeapNodeComparer());
        }

        public DictValue GetKey(string key)
        {
            return store[key];
        }

        public void PutKey(string key, string value, DateTime? timeToLive = null)
        {
            if (store.Count >= capacity)
            {
                EvictKeys();
            }

            var dictValue = new DictValue(value, timeToLive);
            store.Add(key, dictValue);

            if (timeToLive != null)
            {
                var heapNode = new HeapNode() { DictKey = key, DictValue = dictValue };
                lruKeys.Add(heapNode);
            }
        }

        private void DeleteKey(string key)
        {
            var dictValue = store[key];

            if (dictValue.TimeToLive != null)
            {
                var heapNode = new HeapNode() { DictKey = key, DictValue = dictValue };
                lruKeys.Remove(heapNode);
            }

            store.Remove(key);
        }

        private void EvictKeys()
        {
            RemoveAllExpiredKeys();
            RemoveLRUKey();
        }

        private void RemoveLRUKey()
        {
            var lruKey = lruKeys.Min;

            DeleteKey(lruKey.DictKey);
        }

        private void RemoveAllExpiredKeys()
        {
            var expiredKeys = store.Where(v => v.Value.TimeToLive != null && v.Value.TimeToLive < DateTime.Now);

            foreach (var kvp in expiredKeys)
            {
                DeleteKey(kvp.Key);
            }
        }
    }
}
