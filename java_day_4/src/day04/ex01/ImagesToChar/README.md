# BMP Image Printer

This project allows you to convert a black and white BMP image into a text representation using specified characters for white and black pixels.

## Compilation

To compile the project, run the following commands:

```sh
# Create target directory for compilation
mkdir -p target

# Compile Java source files to the target directory
javac -d ./target/ src/java/edu/school21/printer/*/*.java

# Copy resources to the target directory
cp -R src/resources target/.

# Create the JAR file with the specified manifest.MF and add all compiled classes and resources
jar cmf src/manifest.MF ./target/images-to-chars-printer.jar -C ./target .

# Change privileges to make the JAR file executable
chmod u+x ./target/images-to-chars-printer.jar
```

# Launch app

Usage: app (char for white pixels) (char for black pixels)
```sh
# Run the JAR file with command line arguments
java -jar ./target/images-to-chars-printer.jar <char for white pixels> <char for black pixels>
```

# Example Usage
```sh
java -jar ./target/images-to-chars-printer.jar . 0
```
