package inventory.controller;

import inventory.model.Part;
import inventory.service.InventoryService;
import inventory.validation.PartValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable, ControllerInterface {

    // Declare fields
    private Stage stage;
    private Parent scene;
    private boolean isOutsourced = true;
    private String errorMessage = new String();
    private int partId;

    private InventoryService service;
    
    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;
    
    @FXML
    private Label addPartDynamicLbl;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;
    
    @FXML
    private TextField addPartDynamicTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    public AddPartController(){
        // Empty constructor
    }

    @Override
    public void setService(InventoryService service){

        this.service=service;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        outsourcedRBtn.setSelected(true);
    }
    /**
     * Method to add to button handler to switch to scene passed as source
     * @param event
     * @param source
     * @throws IOException
     */
    @FXML
    private void displayScene(ActionEvent event, String source) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource(source));
        //scene = FXMLLoader.load(getClass().getResource(source));
        scene = loader.load();
        ControllerInterface ctrl=loader.getController();
        ctrl.setService(service);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Ask user for confirmation before canceling part addition
     * and switching scene to Main Screen
     * @param event
     * @throws IOException
     */
    @FXML
    void handleAddPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Cancelation");
        alert.setContentText("Are you sure you want to cancel adding part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.out.println("Ok selected. Part addition canceled.");
            displayScene(event, "/fxml/MainScreen.fxml");
        } else {
            System.out.println("Cancel clicked.");
        }
    }

    /**
     * If in-house radio button is selected set isOutsourced boolean
     * to false and modify dynamic label to Machine ID
     * @param event 
     */
    @FXML
    void handleInhouseRBtn(ActionEvent event) {
        isOutsourced = false;
        addPartDynamicLbl.setText("Machine ID");
    }

    /**
     * If outsourced radio button is selected set isOutsourced boolean
     * to true and modify dynamic label to Company Name
     * @param event 
     */
    @FXML
    void handleOutsourcedRBtn(ActionEvent event) {
        isOutsourced = true;
        addPartDynamicLbl.setText("Company Name");
    }

    /**
     * Validate given part parameters.  If valid, add part to inventory,
     * else give user an error message explaining why the part is invalid.
     * @param event
     * @throws IOException
     */
    @FXML
    void handleAddPartSave(ActionEvent event) throws Exception {
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        int stock = Integer.parseInt(inventoryTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        String partDynamicValue = addPartDynamicTxt.getText();

        errorMessage = PartValidator.validate(name, price, stock, min, max);

        if (!errorMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Eroare validare!");
            alert.setHeaderText("Eroare!");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return;
        }

        if (isOutsourced) {
            service.addOutsourcePart(name, price, stock, min, max, partDynamicValue);
        } else {
            service.addInhousePart(name, price, stock, min, max, Integer.parseInt(partDynamicValue));
        }

        displayScene(event, "/fxml/MainScreen.fxml");
    }


}
