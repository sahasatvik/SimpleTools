# SimpleTools
A library of simple tools written in Java by me, which aim at providing a common base for projects in the future.

## Features
The `docs/` folder in this repository, as well as in `SimpleTools.jar` contains an extensive documentation of all of the 
features of every class in the library. You can start at `docs/index.html`.

## How to Use this library in your Project
There are two ways of using this library :

1. **Using the uncompressed `.class` files :** simply copy the contents of `bin/` from the repository or `SimpleTools.jar`
into the your project directory, and import the required classes (eg. to import all of the classes in the `com/satvik/args`
folder, use `import com.satvik.args.*;`. Compile as normal.

2. **Using the `SimpleTools.jar` file :** download SimpleTools.jar and copy it to a location within your project, and import
the required classes as mentioned earlier. While compiling, remember to add `SimpleTools.jar` to your classpath, as follows : <br>
  &nbsp;&nbsp;&nbsp;&nbsp;On UNIX based systems : `javac -cp '.:path/to/jar/SimpleTools.jar' sourcefile.java`<br>
  &nbsp;&nbsp;&nbsp;&nbsp;On Windows systems : `javac -cp .;path/to/jar/SimpleTools.jar' sourcefile.java`<br>
  While executing your program, remember to add `SimpleTools.jar` to the classpath again, as follows :<br>
  &nbsp;&nbsp;&nbsp;&nbsp;On UNIX based systems : `java -cp '.:path/to/jar/SimpleTools.jar sourcefile'`<br>
  &nbsp;&nbsp;&nbsp;&nbsp;On Windows systems : `java -cp '.;path/to/jar/SimpleTools.jar sourcefile'`
