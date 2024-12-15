package view;

import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import model.Item;
import model.Offer;

public class SellerItemsPage implements EventHandler<ActionEvent>{
	private BorderPane root;
    private GridPane grid;
    public Scene scene;
    private Label title, text, tablePlaceholder;
    private Button backBtn;
    private TableView<Item> tableItem;
    private ItemController ic;

    public SellerItemsPage() {
		ic = new ItemController();
        init();
        event();
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
        backBtn = new Button("Back");
        tableItem = new TableView<>();
        tablePlaceholder = new Label("No items available. Upload a new item to get started.");
        tableItem.setPlaceholder(tablePlaceholder);
        
        setupTable();
        loadData();
    }
	
    @SuppressWarnings("unchecked")
	private void setupTable() {
    	//Setup Table Item milik Seller
        TableColumn<Item, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        nameCol.setMinWidth(150);


        TableColumn<Item, String> sizeCol = new TableColumn<>("Size");
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        sizeCol.setMinWidth(100);

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        priceCol.setMinWidth(100);
        
        TableColumn<Item, String> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        catCol.setMinWidth(150);
        
        //Update Btn
        TableColumn<Item, Void> updateCol = new TableColumn<>("Update");
        updateCol.setCellFactory(param -> new TableCell<>() {
            Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Item item = getTableView().getItems().get(getIndex());
                    System.out.println("Updating item: " + item.getItemName());
                    //Bawa ke Update Page
                    Main.redirect(new EditItemPage(item).scene);
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
            Button deleteButton = new Button("Delete");

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

        tableItem.getColumns().addAll(nameCol, catCol, sizeCol, priceCol, updateCol, deleteCol);
        
        
    }
    
    
    private void loadData() {
        tableItem.getItems().setAll(ic.getAllItemsbyUser());
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
        grid.add(backBtn, 1, 1);
        grid.add(tableItem, 0, 2);
    }
    
    public void event() {
    	backBtn.setOnAction(e -> handle(e));
    }
    
    @Override
    public void handle(ActionEvent event) {
  
    	if (event.getSource() == backBtn) {
			Main.redirect(new HomePage().scene);
		}
    }
    
    
    public void setStyle() {
        title.setStyle("-fx-font-size: 36px;");
    }
	

}
