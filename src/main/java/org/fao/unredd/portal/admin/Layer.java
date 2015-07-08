package org.fao.unredd.portal.admin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sgiaccio on 11/02/15.
 *
 * Represents a Layre portal definition
 */
public class Layer {
    public String id;
    public String label;
    public String baseUrl;
    public String wmsName;
    public String wmsTime;
    public String imageFormat;
    public Boolean visible;
    public Boolean queryable;
    public String queryDialogType;
    public String legend;
    public String sourceLink;
    public String sourceLabel;
    public Map<String, String> wmsParameters;
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
