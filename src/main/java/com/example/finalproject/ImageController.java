package com.example.finalproject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;

public class ImageController {

    @FXML private ImageView imageView;
    @FXML private Label infoTextLabel;

    @FXML private ComboBox<String> formatComboBox;

    private File selectedImageFile;
    private File convertedImageFile;

    @FXML
    // The formatCombobox was populated with three String values by using the initialize method.
    public void initialize() {
        // Add three different String values to the formatComBox
        formatComboBox.getItems().addAll("PNG", "JPEG", "GIF");
        // Set the default selection of the ComboBox to the first item in the list.
        formatComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void selectImage() {
        // Display a file selection dialog which allows user to choose an image file from their computer.
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        // Specify the file types that the user can select.
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        // File object representing the selected file is obtained after the user upload a file.
        File selectedFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());
        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            // An Image object is created from the selected file using the Image constructor.
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            // Some text is displayed in a Label object which shows the width and height of the image.
            infoTextLabel.setText(String.format("Width: %.0f, Height: %.0f", image.getWidth(), image.getHeight()));
        }
    }

    @FXML
    public void convertImage() {
         /*
         Check if an image has been selected in the application's image view component.
        If there is no image selected, the method will set a text message to inform the user
        to select an image first and then return without performing the image conversion.
        */
        if (imageView.getImage() == null) {
            infoTextLabel.setText("Please select an image first.");
            return;
        }
        /*
            This section of code tries to convert the image.
            It creates a new File object for the input file that was previously selected.
            It then gets the output format from a ComboBox component and converts it to lowercase.
            Next, it creates a new File object for the converted image file with a new file name and the selected output format.
            Finally, it calls the convert method, passing in the input, output files, and output format.
         */
        try {
            File inputFile = selectedImageFile;
            String outputFileExtension = formatComboBox.getValue().toLowerCase();
            convertedImageFile = new File(inputFile.getParent(), "new_" + inputFile.getName().split("\\.")[0] + "." + outputFileExtension);
            convert(inputFile, convertedImageFile, outputFileExtension);
            System.out.println("Image converted successfully.");
        } catch (IOException e) {
            // If an IOException occurs during the conversion process, an error message will be displayed.
            System.out.println("Image conversion failed.");
            e.printStackTrace();
        }
    }

    @FXML
    public void saveImage() {
        /*
        checks if the converted image file exists.
        If the converted image file does not exist, the method sets a text message to inform the user to perform the image conversion first.
         */
        if (convertedImageFile == null || !convertedImageFile.exists()) {
            infoTextLabel.setText("Please do image conversion first.");
            return;
        }
        /*
        Set the extension filter to only show the image files of the type selected in the formatComboBox.
        It also sets the initial file name to be the name of the converted image file.
         */
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*." + formatComboBox.getValue().toLowerCase())
        );
        fileChooser.setInitialFileName(convertedImageFile.getName());
        File saveFile = fileChooser.showSaveDialog(imageView.getScene().getWindow());
        /*
        Rename the converted image file to the selected file.
        Set a message to inform the user that the image was saved successfully.
         */
        if (saveFile != null) {
            convertedImageFile.renameTo(saveFile);
            infoTextLabel.setText("Image saved successfully.");
        }
    }

    /*
    Takes in an input file, an output file, and an output format as parameters,
    and converts the image file to the selected format.
     */
    private static void convert(File inputFile, File outputFile, String outputFormat) throws IOException {
        // Read in the input file to manipulate the image data.
        BufferedImage image = ImageIO.read(inputFile);
        // Handle the conversion of the image data to the selected output format.
        ImageIO.write(image, outputFormat, outputFile);
    }
}