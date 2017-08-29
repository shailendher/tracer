# About

The application reads the log files and converts them to JSON entries.

# How to run

1. mvn package
2. java -jar target\tracer-0.0.1-SNAPSHOT-jar-with-dependencies.jar -i data\small-log.txt -o data\small-output.txt

# Input parameters

Usage: <main class> [options]
  Options:
  * -i, --input
      Path to the log file.
  * -m, --mode
      conservate: single threaded, aggressive: multi threaded and faster by
      multiple of 2 or more
      Default: AGGRESSIVE
      Possible Values: [CONSERVATIVE, AGGRESSIVE]
  * -o, --output
      Path to the output file.

# Core concept

Input reader ->
* Single or batch mode depending on configuration
* In Batch mode, the stream is split into batches of 10 and fetched concurrently using x threads (x -> no. of cores -1).

Output writer ->
* Thread safe implementation invoked by multiple to write the json entries to a single output file

FifoBuffer ->
* Bounded FIFO based LinkedMap to temporarily group the log entries by their id and process it.
* A map returns better performance to group items compared to a BoundedLinkedQueue.
* Guava multimap is used purely for convience and can be replace with a custom Map<String, List<T>> implementation as well.
* When max. limit is reached, event listeners are triggered to remove and process the first entry. This is done in batches of 100 for performance.
* On exhaustion of input source, all entries in the map are flushed at once.

# TODO or Improvements

* Better validation of beans and input
* Proper datatypes for fields
* Current design is based on listener for the FifoBuffer. A Processor/Writer running on a separate thread and monitoring the queue would be better.
* Parallel stream currently uses Common ForkPool. A custom pool finetuned based on performance testing would yield better results.
* Async execution of listener events.





