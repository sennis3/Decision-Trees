Sean Ennis
sennis3, 653900061
CS 411
Assignment 12 - Decision Trees
May 5, 2020


File Input Format:

The input training data must be given as a CSV file. Additionally, the first line of the CSV file must be the attribute headers. For example, the CSV file should look something like this:

Alt, Bar, Fri, Hun, Pat, Price,Rain, Res, Type,    Est,    Goal
Yes, No,  No,  Yes, Some, $$$, No,   Yes, French,  0-10,   Yes
Yes, No,  No,  Yes, Full, $,   No,   No,  Thai,    30-60,  No
No,  Yes, No,  No,  Some, $,   No,   No,  Burger,  0-10,   Yes
Yes, No,  Yes, Yes, Full, $,   No,   No,  Thai,    10-30,  Yes
Yes, No,  Yes, No,  Full, $$$, No,   Yes, French,  >60,    No 
No,  Yes, No,  Yes, Some, $$,  Yes,  Yes, Italian, 0-10,   Yes
No,  Yes, No,  No,  None, $,   Yes,  No,  Burger,  0-10,   No
No,  No,  No,  Yes, Some, $$,  Yes,  Yes, Thai,    0-10,   Yes
No,  Yes, Yes, No,  Full, $,   Yes,  No,  Burger,  >60,    No 
Yes, Yes, Yes, Yes, Full, $$$, No,   Yes, Italian, 10-30,  No 
No,  No,  No,  No,  None, $,   No,   No,  Thai,    0-10,   No 
Yes, Yes, Yes, Yes, Full, $,   No,   No,  Burger,  30-60,  Yes

Each example must be on its own line following the attribute headers line. There should be commas between the values on each line. Spaces are ok.

The CSV file above is also included in the program directories to run with it.


Running the program with the file:

The file must a text file and it needs to be read in from the command line. The program expects a file as a parameter to the function.
When providing the file as a parameter when running the function, the absolute file path must be used. However, this isn't necessary if the file is located within the current working directory.

To run the program, navigate to the bin directory in sennis3_DecisionTrees and run it with "java DecisionTree[file]"

Example: "java DecisionTree C:\Users\sennis3\Desktop\aima_data.txt"

If the input files are located within the bin directory, it can also be run with the example below.

Example: "java DecisionTree aima_data.txt"


Output:

The program prints the training data table before each split and shows which examples are left. It also displays the information gain calculated for each attribute, and it says which one was chosen for being the highest. The resulting decision tree is printed at the bottom.


Bugs:

Unfortunately, there is a bug in my code where it does not create branches for attribute values that don't exist among the current examples. This was due to the way I implemented my attribute class. I was not able to fix this in time. Besides this, the program should create the decision tree correctly.