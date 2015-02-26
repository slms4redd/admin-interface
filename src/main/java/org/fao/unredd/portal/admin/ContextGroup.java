package org.fao.unredd.portal.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sgiaccio on 11/02/15.
 *
 * Represents the portal context group recursive definition
 */

public class ContextGroup implements Recursive {
    String label;
    String infoFile;
    List<Recursive> items = new ArrayList<>(); // can be a ContextGroup or a Context

    @Override
    public String toString() {
        return "ContextGroup{" +
                "label='" + label + '\'' +
                ", infoFile='" + infoFile + '\'' +
                ", items=" + items +
                '}';
    }
}
