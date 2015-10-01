package org.fao.unredd.portal.admin;

import com.google.gson.annotations.Expose;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sgiaccio on 11/02/15.
 *
 * Represents a Layre portal definition
 */
public class Layer {
    @Expose public String id;
    @Expose public String label;
    @Expose public String baseUrl;
    @Expose public String wmsTime;
    @Expose public String wmsName;
    @Expose public String imageFormat;
    @Expose public Boolean visible;
    @Expose public Boolean queryable;
    @Expose public String queryDialogType;
    @Expose public String legend;
    @Expose public String sourceLink;
    @Expose public String sourceLabel;
    @Expose public Map<String, String> wmsParameters;
    public int rank; // Don't know yet if this is needed

    @Override
    public String toString() {
        return "Layer{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", wmsName='" + wmsName + '\'' +
                ", wmsTime='" + wmsTime + '\'' +
                ", imageFormat='" + imageFormat + '\'' +
                ", visible=" + visible +
                ", queryable=" + queryable +
                ", queryDialogType='" + queryDialogType + '\'' +
                ", legend='" + legend + '\'' +
                ", sourceLink='" + sourceLink + '\'' +
                ", sourceLabel='" + sourceLabel + '\'' +
                ", wmsParameters=" + wmsParameters +
                ", rank=" + rank +
                '}';
    }
}
