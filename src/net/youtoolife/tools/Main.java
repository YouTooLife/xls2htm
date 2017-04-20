package net.youtoolife.tools;
	
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import net.youtoolife.tools.view.OverviewController;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	


public Stage primaryStage;
private BorderPane rootLayout;

public static String dir = System.getProperty("user.home")+"YouTooLife/darkmit/";

OverviewController control = null;


@Override
public void start(Stage primaryStage) {
	 this.primaryStage = primaryStage;
     this.primaryStage.setTitle("JXls2Html");

        initRootLayout();

        showPersonOverview();
}




public void initRootLayout() {
    try {
        // Загружаем корневой макет из fxml файла.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Отображаем сцену, содержащую корневой макет.
        Scene scene = new Scene(rootLayout);
        
        
        scene.setOnDragOver(new EventHandler<DragEvent>() {

        	@Override
            public void handle(DragEvent event) {
        		//DragEvent db = new D
        		Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
		});  
        
        // Dropping over surface
        scene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String filePath = null;
                    /*for (File file:db.getFiles()) {
                        filePath = file
                        System.out.println(filePath);
                        
                    }*/
                    filePath = db.getFiles().get(0).getAbsolutePath();
                    control.openFileDD(filePath);
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

/**
 * Показывает в корневом макете сведения об адресатах.
 */
public void showPersonOverview() {
    try {
        // Загружаем сведения об адресатах.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/MainForm.fxml"));
        AnchorPane overview = (AnchorPane) loader.load();

        rootLayout.setCenter(overview);
        
        OverviewController controller = loader.getController();
        control = controller;
        controller.setMainApp(this);
        
    } catch (IOException e) {
        e.printStackTrace();
    }
}

/**
 * Возвращает главную сцену.
 * @return
 */
public Stage getPrimaryStage() {
    return primaryStage;
}



public static void main(String[] args) {
	
	
	launch(args);
}

}
