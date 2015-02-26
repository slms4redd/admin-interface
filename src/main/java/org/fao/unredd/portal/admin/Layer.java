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
    String id;
    String label;
    String baseUrl;
    String wmsName;
    String wmsTime;
    String imageFormat;
    Boolean visible;
    Boolean queryable;
    String queryDialogType;
    String legend;
    String sourceLink;
    String sourceLabel;
    Map<String, String> wmsParameters;
//  int rank; // Don't know yet if this is needed

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
                '}';
    }
}
