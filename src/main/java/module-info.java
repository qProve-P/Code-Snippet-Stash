module code.snippet.stash {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome;
    requires java.desktop;

    opens io.github.qprove_p.codesnippetstash.gui to javafx.fxml;
    opens io.github.qprove_p.codesnippetstash.data to jakarta.persistence, org.hibernate.orm.core;

    exports io.github.qprove_p.codesnippetstash.gui to javafx.fxml, javafx.graphics;
    exports io.github.qprove_p.codesnippetstash.storage to javafx.fxml, javafx.graphics;
    opens io.github.qprove_p.codesnippetstash.storage to javafx.fxml;
}