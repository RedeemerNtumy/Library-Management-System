module com.projects.lms.finallms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.projects.lms.finallms to javafx.fxml;
    exports com.projects.lms.finallms;
    exports com.projects.lms.finallms.dao;
    opens com.projects.lms.finallms.dao to javafx.fxml;
    exports com.projects.lms.finallms.models;
    opens com.projects.lms.finallms.models to javafx.fxml;
    exports com.projects.lms.finallms.listmanagers;
    opens com.projects.lms.finallms.listmanagers to javafx.fxml;
}