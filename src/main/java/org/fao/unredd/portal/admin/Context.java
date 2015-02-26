package org.fao.unredd.portal.admin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgiaccio on 11/02/15.
 *
 * Represents a portal Context definition
 */
public class Context implements Recursive {
    String id;
    String label;
    Boolean active;
    List<Layer> layers;
    String infoFile;
    String inlineLegendUrl;

    @Override
    public String toString() {
        return "Context{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", active=" + active +
                ", layers=" + layers +
                ", infoFile='" + infoFile + '\'' +
                ", inlineLegendURL='" + inlineLegendUrl + '\'' +
                '}';
    }
}
