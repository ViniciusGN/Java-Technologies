package com.example.imageviewer.image_viewer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.List;

public class ImageViewerBean {
    private String directoryName;
    private int imageCount;
    private boolean loopMode;
    private boolean slideshowMode;

    private PropertyChangeSupport support;

    public ImageViewerBean() {
        support = new PropertyChangeSupport(this);
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        String oldName = this.directoryName;
        this.directoryName = directoryName;
        support.firePropertyChange("directoryName", oldName, directoryName);
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        int oldCount = this.imageCount;
        this.imageCount = imageCount;
        support.firePropertyChange("imageCount", oldCount, imageCount);
    }

    public boolean isLoopMode() {
        return loopMode;
    }

    public void setLoopMode(boolean loopMode) {
        boolean oldLoop = this.loopMode;
        this.loopMode = loopMode;
        support.firePropertyChange("loopMode", oldLoop, loopMode);
        System.out.println("Loop Mode is now: " + this.loopMode);
    }

    public boolean isSlideshowMode() {
        return slideshowMode;
    }

    public void setSlideshowMode(boolean slideshowMode) {
        boolean oldSlideshow = this.slideshowMode;
        this.slideshowMode = slideshowMode;
        support.firePropertyChange("slideshowMode", oldSlideshow, slideshowMode);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}