package com.example.imageviewer.image_viewer;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ImageViewerController implements PropertyChangeListener {
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

    private ImageViewerBean imageViewerBean;
    private List<File> imageFiles;
    private int currentIndex = 0;
    private File selectedDirectory;

    @FXML
    public void initialize() {
        imageViewerBean = new ImageViewerBean();
        imageViewerBean.addPropertyChangeListener(this);

        time_bar.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("New time for diaporama: " + newValue.intValue() + " seconds.");
        });

        String imagePath = System.getProperty("user.dir") + "/src/main/resources/com/example/imageviewer/image_viewer/images";
        File defaultDirectory = new File(imagePath);
        if (defaultDirectory.exists() && defaultDirectory.isDirectory()) {
            selectedDirectory = defaultDirectory;
            imageViewerBean.setDirectoryName(defaultDirectory.getAbsolutePath());
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
                imageViewerBean.setDirectoryName(directoryPath);
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

        File[] files = selectedDirectory.listFiles((dir, name) -> name.toLowerCase().matches(".*\\.(png|jpg|jpeg|gif|bmp)"));
        if (files != null && files.length > 0) {
            imageFiles = Arrays.asList(files);
            imageViewerBean.setImageCount(imageFiles.size());
            currentIndex = 0;
            displayImage();
        } else {
            imageViewerBean.setImageCount(0);
            image_place.setImage(null);
            imageFiles = null;
        }
    }

    private void displayImage() {
        if (imageFiles != null && !imageFiles.isEmpty() && currentIndex >= 0 && currentIndex < imageFiles.size()) {
            File file = imageFiles.get(currentIndex);
            String imagePath = file.toURI().toString();

            Image image = new Image(imagePath, false);
            if (!image.isError()) {
                image_place.setImage(image);
                image_place.setPreserveRatio(true);
                image_place.setSmooth(true);
                image_place.setFitWidth(566);
                image_place.setFitHeight(246);
            }
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
            } else if (imageViewerBean.isLoopMode()) {
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
            } else if (imageViewerBean.isLoopMode()) {
                currentIndex = imageFiles.size() - 1;
            }
            displayImage();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("imageCount".equals(evt.getPropertyName())) {
            quantity_text.setText("Number of images: " + evt.getNewValue());
        }
    }
}