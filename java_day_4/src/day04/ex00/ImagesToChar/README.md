# BMP Image Printer

This project allows you to convert a black and white BMP image into a text representation using specified characters for white and black pixels.

## Compilation

To compile the project, run the following commands:

```sh
mkdir target
javac -d ./target/ src/java/edu/school21/printer/*/*.java
```

# Launch app

Usage: app (char for white pixels) (char for black pixels) (full path to black & white bmp image)
```sh
java -cp ./target edu.school21.printer.app.Main
```

# Example Usage
```sh
java -cp ./target edu.school21.printer.app.Main . 0 /home/getname/Downloads/it.bmp
```
