package net.youtoolife.tools.view;


import java.io.File;

import com.sun.javafx.geom.Area;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import net.youtoolife.tools.Main;
import net.youtoolife.tools.XlsParcer;

public class OverviewController {
	
	


	
	@FXML
	private HTMLEditor text;
	
	@FXML
	private TextArea area;
	
	@FXML
	private ComboBox<String> comboBox;
	
	private XlsParcer parcer;
	private String fileName;

	
	private Main main;

   
    public OverviewController() {
    	
    }
    
    
    public void setMainApp(Main main) {
        this.main = main;

      
    }
	
    
	@FXML
    private void initialize() {
		System.out.println("Init");
		parcer = new XlsParcer();
	}
	
	
	public void openFileDD(String fileFullName) {
		System.out.println("Open");
		
		fileName = fileFullName;
		
		ObservableList<String> list = FXCollections.observableArrayList();
		list.addAll(parcer.openFile(fileName));
		
		
		comboBox.setItems(list);
		comboBox.setId(list.get(0));
		area.setText(parcer.parse(fileName, list.get(0)));
		text.setHtmlText(area.getText());
		
		/*Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("File '"+fileName +"' was successfully opened!");
		alert.showAndWait();*/
		main.primaryStage.setTitle("JXls2Html: "+fileName);
	}
	
	@FXML
    private void openFileBtn() {
		System.out.println("Open");
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open document...");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.dir"))
        );                 
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("XLSX", "*.xlsx"),
            new FileChooser.ExtensionFilter("Old format xls", "*.xls"),
            new FileChooser.ExtensionFilter("All documents", "*.*")
        );
		
		
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile == null) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("File selection cancelled.");
			alert.showAndWait();
			return;
		}
		
		
		fileName = selectedFile.getAbsolutePath();
		
		ObservableList<String> list = FXCollections.observableArrayList();
		list.addAll(parcer.openFile(fileName));
		
		
		comboBox.setItems(list);
		comboBox.setId(list.get(0));
		area.setText(parcer.parse(fileName, list.get(0)));
		text.setHtmlText(area.getText());
		
		/*Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("File '"+selectedFile.getName() +"' was successfully opened!");
		alert.showAndWait();*/
		main.primaryStage.setTitle("JXls2Html: "+selectedFile.getAbsolutePath());
	}
	
	@FXML
    private void uploadBtn() {
		System.out.println("Upload");
	}
	
	@FXML
    private void comboBoxChange() {
		//area.setText(text.getHtmlText());
		//text.setHtmlText(parcer.parse(fileName, comboBox.getValue()));
		area.setText(parcer.parse(fileName, comboBox.getValue()));
		text.setHtmlText(area.getText());
		
	}
	

	
	
	
	

}
