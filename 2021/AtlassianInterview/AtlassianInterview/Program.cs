using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Linq;

/*
file1.txt(size: 100)
file2.txt(size: 200) in collection "collection1"
file3.txt(size: 200) in collection "collection1"
file4.txt(size: 300) in collection "collection2"
file5.txt(size: 10)
*/

namespace AtlassianInterview.Round2
{
    public class File
    {
        public File(string name, int size)
        {
            Id = Guid.NewGuid();
            Name = name;
            Size = size;
        }

        public Guid Id { get; }

        public string Name { get; }

        public int Size { get; }
    }

    public class Collection
    {
        public Collection(string name)
        {
            Name = name;
            this.Files = new List<File>();
        }

        public string Name { get; }

        public IList<File> Files { get; }

        public void AddFile(File file)
        {
            Files.Add(file);
        }
    }

    public class CollectionComparer : IComparer<Collection>
    {
        public int Compare([AllowNull] Collection x, [AllowNull] Collection y)
        {
            var xSize = x.Files.Sum(f => f.Size);
            var ySize = y.Files.Sum(f => f.Size);

            int result = xSize.CompareTo(ySize);
            return result;
        }
    }

    class Program
    {
        class Result
        {
            public int TotalFilesProcessed { get; set; }

            public IList<Collection> TopCollections { get; set; }
        }

        class CollectionInput
        {
            public CollectionInput(string fn, int fs, string cn = "")
            {
                FileName = fn;
                FileSize = fs;
                CollectionName = cn;
            }

            public string FileName { get; set; }

            public int FileSize { get; set; }

            public string CollectionName { get; set; }
        }

        static IList<File> Files;
        static IList<Collection> Collections;

        static Program()
        {
            Files = new List<File>();
            Collections = new List<Collection>();
        }

        static Result GetResult(IList<CollectionInput> inputs, int n)
        {
            var result = new Result();

            // input population M
            foreach (var input in inputs)
            {
                var file = new File(input.FileName, input.FileSize);
                
                Files.Add(file);

                if (!string.IsNullOrWhiteSpace(input.CollectionName))
                {
                    var collection = Collections.SingleOrDefault(c => c.Name.Equals(input.CollectionName, StringComparison.OrdinalIgnoreCase));
                    if (collection == null)
                    {
                        collection = new Collection(input.CollectionName);
                        Collections.Add(collection);
                    }

                    collection.AddFile(file);
                }
            }

            //
            result.TotalFilesProcessed = Files.Sum(f => f.Size);

            // computing Top N
            var heap = new SortedSet<Collection>(new CollectionComparer());
            foreach (var collection in Collections)
            {
                if (heap.Count() >= n)
                {
                    var currCollectionSize = collection.Files.Sum(f => f.Size);
                    var minCollectionSize = heap.Min.Files.Sum(f => f.Size);
                    if (currCollectionSize > minCollectionSize)
                    {
                        heap.Remove(heap.Min);
                    }
                    else
                    {
                        continue;
                    }
                }

                heap.Add(collection);
            }

            result.TopCollections = heap.ToList();
            return result;
        }

        static void Main(string[] args)
        {

            var inputs = new List<CollectionInput>()
            {
                new CollectionInput("file1.txt", 100),
                new CollectionInput("file2.txt", 200),
                new CollectionInput("file3.txt", 300),
                new CollectionInput("file4.txt", 400),
                new CollectionInput("file5.txt", 500),
                new CollectionInput("file6.txt", 600),
                new CollectionInput("file7.txt", 700),
                new CollectionInput("file8.txt", 800),
                new CollectionInput("file9.txt", 900),
                //new CollectionInput("file2.txt", 200, "collection1"),
                //new CollectionInput("file3.txt", 300, "collection1"),
                //new CollectionInput("file4.txt", 400, "collection2"),
                //new CollectionInput("file5.txt", 500, "collection2"),
                //new CollectionInput("file6.txt", 600, "collection2"),
                //new CollectionInput("file7.txt", 700, "collection3"),
                //new CollectionInput("file8.txt", 800, "collection4"),
                //new CollectionInput("file9.txt", 900, "collection1"),
                new CollectionInput("file10.txt", 500)
            };

            Console.ReadLine();
        }
    }
}
