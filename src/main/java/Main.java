import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));

        Parent load = fxmlLoader.load();

        Scene scene = new Scene(load, 602, 550);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
