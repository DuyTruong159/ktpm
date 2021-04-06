module com.team.btlon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.team.btlon to javafx.fxml;
    exports com.team.btlon;
    exports com.team.pojo;
}
