using System;

namespace SwiggyLLD
{
    class Program
    {
        static void Main(string[] args)
        {
            var store = new KeyValueStore(4);
            store.PutKey("1", "01010", DateTime.Now.AddDays(2));
            store.PutKey("2", "01110", DateTime.Now.AddDays(3));
            store.PutKey("3", "01310", DateTime.Now.AddDays(1));
            store.PutKey("4", "01510");
            store.PutKey("5", "01510");

            Console.WriteLine("Hello World!");
            Console.ReadLine();
        }
    }
}


/*
Key-value (size of keys) => 10K
1. put() -> TTL for a key
2. get

once reached size it should evict a key

External
get, put

Internal
delete
evict [ all expired keys, LRUKey ]

----------------------------

Dictionary: <Key, Value>
Value: Value, TTL


Heap => (Key, Value[Val, TTL])

---------------------------

 */

