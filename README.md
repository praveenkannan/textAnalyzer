Text Analyzer
=============

TextAnalyzer.java is a simple java class that that processes text and provides information about its word contents. It has the ability to create a report that shows a count of how many times each word occurs in the text. The report would be sorted, with a primary sort of word length, and a secondary ASCII sort. The code has been written with the intent to be part of a utility 

The input to this program is proided from a file. We have two sample files(a small and a big file) to try out


How to compile & Run
====================
To Compile from Source
----------------------
javac -d . TextAnalyzer.java
java -classpath . com.praveen.TextAnalyzer <Input_File_Name>

To run the Jar file
-------------------
java -jar TextAnalyzer.jar <Input_File_Name>

Example
=======
java -classpath . com.praveen.TextAnalyzer SmallFile.txt 

1 The
1 fox
1 the
1 back
1 lazy
1 over
2 brown
1 dog’s
1 quick
1 jumped

Code complexity
===============
We are given a contraint to not use Java collections. Keeping that in mind we come up with the following decisions.
# Adopt an effecient solution that doesnt compramise on Storage and computation. 

Design
======
> We read from the file once and place all the contents as a string array: O(n) I/O and storage
> A sort method with overridden comparison constraint: O(n log n)
> We do a one pass on sorted array and compute the word count and print them on the fly. O(n)
