package view;

import controller.ItemController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Item;

public class EditItemPage {
	private BorderPane root;
	private GridPane grid;
	public Scene scene;
	private Label nameLabel, catLabel, sizeLabel, priceLabel, title;
	private Button updateBtn, backBtn;
	private ItemController ic;
	private TextField nameField, catField, sizeField, priceField;
	private Item item;

	public void init() {
		title = new Label("Update Page");
		root = new BorderPane();
		scene = new Scene(root, 1200, 600);
		grid = new GridPane();
		nameLabel = new Label();
		nameField = new TextField();
		nameField.setText(item.getItemName());

		catLabel = new Label();
		catField = new TextField();
		catField.setText(item.getItemCategory());

		sizeLabel = new Label();
		sizeField = new TextField();
		sizeField.setText(item.getItemSize());

		priceLabel = new Label();
		priceField = new TextField();
		priceField.setText(item.getItemPrice());

		updateBtn = new Button("Submit");
		backBtn = new Button("Back");
		ic = new ItemController();
	}

	public void setAlignment() {
		root.setLeft(grid);
		grid.setPadding(new Insets(20));
		grid.setVgap(10);
		grid.setHgap(10);

		grid.add(title, 0, 0);
		grid.add(nameLabel, 0, 1);
		grid.add(nameField, 1, 1);

		grid.add(catLabel, 0, 2);
		grid.add(catField, 1, 2);

		grid.add(sizeLabel, 0, 3);
		grid.add(sizeField, 1, 3);

		grid.add(priceLabel, 0, 4);
		grid.add(priceField, 1, 4);

		grid.add(updateBtn, 1, 5);
		grid.add(backBtn, 2, 5);

	}

	public void event() {
		updateBtn.setOnAction(e -> handle(e));
		backBtn.setOnAction(e -> handle(e));
	}

	public void handle(ActionEvent event) {
		if (event.getSource() == updateBtn) {
			double res;
			res = ic.checkItemValidation(nameField.getText(), catField.getText(), sizeField.getText(),
					priceField.getText());
			// Item Name validation
			if (res <= -1 && res > -2) {
				if (res == -1.1) {
					showAlert("Invalid Item Name", "Item Name can't be empty.");
					return;
				} else if (res == -1.2) {
					showAlert("Invalid Item Name", "Item Name must be at least 3 character long.");
					return;
				}

			}
			// Item Category validation
			else if (res <= -2 && res > -3) {
				if (res == -2.1) {
					showAlert("Invalid Category", "Category can't be empty.");
					return;
				} else if (res == -2.2) {
					showAlert("Invalid Category", "Category must be at least 8 character long.");
					return;
				}
			}
			// Item Size validation
			else if (res <= -3 && res > -4) {
				if (res == -3.1) {
					showAlert("Invalid Item Size", "Item Size can't be empty.");
					return;
				}
			}
			// Item Price validation
			else if (res <= -4) {
				if (res == -4.1) {
					showAlert("Invalid Price", "Price can't be empty.");
					return;
				} else if (res == -4.2) {
					showAlert("Invalid Price", "Price can't be 0.");
					return;
				} else if (res == -4.3) {
					showAlert("Invalid Price", "Price must be a number");
					return;
				}
			}
			// Update Success
			else {

				showSuccess("Success", "Your Item has been Updated");
				ic.editItem(item.getItemId(), nameField.getText(), catField.getText(), sizeField.getText(),
						priceField.getText());
				System.out.println("Berhasil Upload");
				Main.redirect(new SellerItemsPage().scene);
			}

		} else if (event.getSource() == backBtn) {
			Main.redirect(new SellerItemsPage().scene);
		}
	}

	public void setStyle() {
		title.setStyle("-fx-font-size: 36px;");
	}

	public void showAlert(String title, String errorMessage) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}

	public void showSuccess(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public EditItemPage(Item i) {
		this.item = i;
		init();
		setAlignment();
		setStyle();
		event();
		Main.redirect(scene);

	}

}