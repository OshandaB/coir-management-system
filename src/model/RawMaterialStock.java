package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class RawMaterialStock {
    private Button updateBtn;
    private Button deleteBtn;

    private  final StringProperty product_id=new SimpleStringProperty();
    private  final StringProperty product_name=new SimpleStringProperty();
    private  final StringProperty quantity=new SimpleStringProperty();

    public Button getUpdateBtn() {
        return updateBtn;
    }

    public void setUpdateBtn(Button updateBtn) {
        this.updateBtn = updateBtn;
    }

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public String getProduct_id() {
        return product_id.get();
    }

    public StringProperty product_idProperty() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id.set(product_id);
    }

    public String getProduct_name() {
        return product_name.get();
    }

    public StringProperty product_nameProperty() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name.set(product_name);
    }

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }
}
