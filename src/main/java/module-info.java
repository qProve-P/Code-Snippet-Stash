module code.snippet.stash {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires org.apache.logging.log4j;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens io.github.qprove_p.codesnippetstash.gui to javafx.fxml;

    exports io.github.qprove_p.codesnippetstash.gui to javafx.fxml, javafx.graphics;
}