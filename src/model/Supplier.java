package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Supplier {
    private Button updateBtn;
    private Button deleteBtn;

    private  final StringProperty supplier_id=new SimpleStringProperty();
    private  final StringProperty supplier_name=new SimpleStringProperty();
    private  final StringProperty address=new SimpleStringProperty();
    private  final StringProperty contact_number=new SimpleStringProperty();
    private  final StringProperty email=new SimpleStringProperty();

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

    public String getSupplier_id() {
        return supplier_id.get();
    }

    public StringProperty supplier_idProperty() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id.set(supplier_id);
    }

    public String getSupplier_name() {
        return supplier_name.get();
    }

    public StringProperty supplier_nameProperty() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name.set(supplier_name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getContact_number() {
        return contact_number.get();
    }

    public StringProperty contact_numberProperty() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number.set(contact_number);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
