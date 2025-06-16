package io.github.qprove_p.codesnippetstash.gui;

import javafx.scene.Node;
import javafx.scene.Parent;

public class DetailPageWrapper {
    Parent parent;
    DetailPage controller;

    DetailPageWrapper(Parent parent, DetailPage controller) {
        this.parent = parent;
        this.controller = controller;
    }
}
