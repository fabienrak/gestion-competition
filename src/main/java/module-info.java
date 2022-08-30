module org.app.attila {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.sql.rowset;
    requires org.apache.poi.ooxml.schemas;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires org.xerial.sqlitejdbc;
    requires com.jfoenix;

    opens fxml to javafx.fxml;
    opens org.app.attila to javafx.fxml;
    opens org.app.attila.controller to javafx.fxml;
    opens org.app.attila.model to javafx.fxml, javafx.base;
    exports org.app.attila to javafx.graphics, java.sql.rowset;

}