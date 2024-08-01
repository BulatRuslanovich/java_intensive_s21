# BMP Image Printer

This project allows you to convert a black and white BMP image into a text representation using specified characters for white and black pixels.

## Compilation

To compile the project, run the following commands:

```sh
# Create target & lib directory for compilation
mkdir -p target lib

# Download libraries
curl -s https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar 


# Extract files and replace to directory 'target'
cd target
jar xf ../lib/jcommander-1.72.jar
jar xf ../lib/JCDP-4.0.0.jar
cd ..

# Compile
javac -cp lib/JCDP-4.0.0.jar:lib/jcommander-1.72.jar: src/java/edu/school21/printer/*/*.java -d ./target

# Copy resources to the target directory
cp -R src/resources target/.

# Create the JAR file
jar cmf src/manifest.MF ./target/images-to-chars-printer.jar -C ./target .

# Change privileges to make the JAR file executable
chmod u+x ./target/images-to-chars-printer.jar
```

# Launch app

```sh
# Run the JAR file with command line arguments
java -jar ./target/images-to-chars-printer.jar --white=<you_color> --black=<you_color>
```

# Example Usage
```sh
java -jar ./target/images-to-chars-printer.jar --white=RED --black=GREEN
```
