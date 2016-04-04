# SimpleTools
A library of simple tools written in Java by me, which aim at providing a common base for projects in the future.

## How to Use this library
There are two ways of using this library :

1. **Using the uncompressed `.class` files :** simply copy the contents of `bin/` into the your project directory, and
import the required classes. For example, to import all of the classes in the `com/satvik/args` folder, use `import com.satvik.args.*;`. 
Compile as normal.

2. **Using the `SimpleTools.jar` file :** download SimpleTools.jar and copy it to a location within your project, and import the 
required classes as mentioned earlier. While compiling, remember to add `SimpleTools.jar` to your classpath, as follows :

On UNIX based systems : `javac -cp '.:path/to/jar/SimpleTools.jar' sourcefile.java`
On Windows systems : `javac -cp .;path/to/jar/SimpleTools.jar' sourcefile.java`

While executing your program, remember to add the jar to the classpath again, as follows :

On UNIX based systems : `java -cp '.:path/to/jar/SimpleTools.jar sourcefile'`
On Windows systems : `java -cp '.;path/to/jar/SimpleTools.jar sourcefile'` 