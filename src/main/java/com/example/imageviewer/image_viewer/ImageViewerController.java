package com.example.imageviewer.image_viewer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ImageViewerController {
    @FXML
    private Button change_folder_bt, next_bt, first_bt, back_bt, last_bt;

    @FXML
    private CheckBox diapo_box, loop_box;

    @FXML
    private Slider time_bar;

    @FXML
    private ImageView image_place;

    @FXML
    private Label quantity_text;

    private List<File> imageFiles;
    private int currentIndex = 0;
    private File selectedDirectory;

    @FXML
    public void initialize() {
        System.out.println("Method initialize() was called!");

        time_bar.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("New time for diaporama: " + newValue.intValue() + " seconds.");
            if (diapo_box.isSelected()) {
            }
        });

        String imagePath = System.getProperty("user.dir") + "/src/main/resources/com/example/imageviewer/image_viewer/images";
        File defaultDirectory = new File(imagePath);
        if (defaultDirectory.exists() && defaultDirectory.isDirectory()) {
            selectedDirectory = defaultDirectory;
            loadImages();
        } else {
            System.out.println("Image Folder not found!");
        }
    }

    @FXML
    public void chooseDirectory() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choose Folder");
        dialog.setHeaderText("Type the path for Image Folder: ");
        dialog.setContentText("Path:");

        dialog.showAndWait().ifPresent(directoryPath -> {
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                selectedDirectory = directory;
                System.out.println("New directory selected: " + directoryPath);
                loadImages();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid directory");
                alert.setContentText("The path provided is not a valid directory.");
                alert.showAndWait();
            }
        });
    }

    private void loadImages() {
        if (selectedDirectory == null) {
            System.out.println("No folder selected!");
            return;
        }

        System.out.println("Loading images from: " + selectedDirectory.getAbsolutePath());

        File[] files = selectedDirectory.listFiles((dir, name) -> name.toLowerCase().matches(".*\\.(png|jpg|jpeg|gif|bmp)"));

        if (files != null) {
            System.out.println("Images found: ");
            for (File file : files) {
                System.out.println(file.getAbsolutePath());
            }
        }

        if (files != null && files.length > 0) {
            imageFiles = Arrays.asList(files);
            quantity_text.setText("Number of images:" + imageFiles.size());
            currentIndex = 0;
            displayImage();
        } else {
            System.out.println("No images found in directory!");
            quantity_text.setText("Number of images: 0");
            image_place.setImage(null);
            imageFiles = null;
        }
    }

    private void displayImage() {
        if (imageFiles != null && !imageFiles.isEmpty() && currentIndex >= 0 && currentIndex < imageFiles.size()) {
            File file = imageFiles.get(currentIndex);
            String imagePath = file.toURI().toString();
            System.out.println("Displaying image in index " + currentIndex + ": " + imagePath);

            Image image = new Image(imagePath, false);
            if (image.isError()) {
                System.out.println("Error loading image: " + imagePath);
            } else {
                image_place.setImage(image);
                image_place.setPreserveRatio(true);
                image_place.setSmooth(true);
                image_place.setFitWidth(566);
                image_place.setFitHeight(246);
                System.out.println("Image loaded successfully.");
            }
        } else {
            System.out.println("No images to display!");
        }
    }

    @FXML
    public void showFirstImage() {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            currentIndex = 0;
            displayImage();
        }
    }

    @FXML
    public void showLastImage() {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            currentIndex = imageFiles.size() - 1;
            displayImage();
        }
    }

    @FXML
    public void showNextImage() {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            if (currentIndex < imageFiles.size() - 1) {
                currentIndex++;
            } else if (loop_box.isSelected()) {
                currentIndex = 0;
            }
            displayImage();
        }
    }

    @FXML
    public void showPreviousImage() {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            if (currentIndex > 0) {
                currentIndex--;
            } else if (loop_box.isSelected()) {
                currentIndex = imageFiles.size() - 1;
            }
            displayImage();
        }
    }

}
