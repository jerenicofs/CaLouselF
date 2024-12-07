package view;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Item;

public class EditItemPage{
	private BorderPane root;
    private GridPane grid;
    public Scene scene;
    private Label title, text;
    private Button uploadBtn;
    private TableView<Item> table;
    private ItemController ic;

    public EditItemPage() {
        ic = new ItemController();
        init();
        setPosition();
        setStyle();
        Main.redirect(scene);
    }

    public void init() {
        root = new BorderPane();
        grid = new GridPane();
        scene = new Scene(root, 1200, 600);
        title = new Label("Shop Editor");
        text = new Label("Edit Your Items here");
        uploadBtn = new Button("Upload New Item");
        table = new TableView<>();

        setupTable();
        loadData();
    }
	
    private void setupTable() {

        TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameCol.setMinWidth(150);

        TableColumn<Item, String> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        catCol.setMinWidth(150);

        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        sizeCol.setMinWidth(100);

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        priceCol.setMinWidth(100);
        
        //Update Btn
        TableColumn<Item, Void> updateCol = new TableColumn<>("Update");
        updateCol.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Updating item: " + item.getItemName());
                    //Bawa ke Update Page
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        //Delete Btn
        TableColumn<Item, Void> deleteCol = new TableColumn<>("Delete");
        deleteCol.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    getTableView().getItems().remove(item);
                    System.out.println("Deleted item: " + item.getItemName());
                    ic.deleteItem(item.getItemId());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        table.getColumns().addAll(nameCol, catCol, sizeCol, priceCol, updateCol, deleteCol);
    }
    
    
    private void loadData() {
        table.getItems().setAll(ic.getAllItems());
    }
	
    
    public void setPosition() {
        root.setTop(title);
        root.setLeft(grid);

        grid.setAlignment(Pos.TOP_LEFT);
        grid.setPrefHeight(350);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        title.setAlignment(Pos.TOP_CENTER);
        grid.add(text, 0, 0);
        grid.add(uploadBtn, 0, 1);
        grid.add(table, 0, 2);
    }
    
    
    public void setStyle() {
        title.setStyle("-fx-font-size: 36px;");
    }
	

}
