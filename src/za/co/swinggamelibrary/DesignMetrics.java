/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.co.swinggamelibrary;

import java.awt.Dimension;
import java.util.HashSet;

/**
 *
 * @author dkrou
 */
public class DesignMetrics {

    private static DesignMetrics single_instance = null;
    private Dimension designResolutionDimensions;
    private Dimension currentResolutionDimensions;
    private Dimension sdResolutionDimensions;
    private Dimension hdResolutionDimensions;
    private Dimension hdrResolutionDimensions;
    private final HashSet<String> searchPaths;

    private DesignMetrics() {
        searchPaths = new HashSet<>();
    }

    public static DesignMetrics initialise(Dimension designResolution, Dimension currentResolution) {
        if (single_instance == null) {
            single_instance = new DesignMetrics();
            single_instance.designResolutionDimensions = designResolution;
            single_instance.currentResolutionDimensions = currentResolution;
            ImageScaler.initialise(designResolution, currentResolution);
        }

        return single_instance;
    }

    public static DesignMetrics getInstance() {
        if (single_instance == null) {
            // TODO throw exception that initialise must be called first
            return null;
        }

        return single_instance;
    }

    public Dimension getDesignResolution() {
        return designResolutionDimensions;
    }

    public Dimension getCurrentResolutionDimensions() {
        return currentResolutionDimensions;
    }

    public void setSdResolution(Dimension dimensions) {
        sdResolutionDimensions = dimensions;
    }

    public void setHdResolution(Dimension dimensions) {
        hdResolutionDimensions = dimensions;
    }

    public void setHdrResolution(Dimension dimensions) {
        hdrResolutionDimensions = dimensions;
    }

    public void setSearchPath(String path) {
        searchPaths.add(path);
    }
}
