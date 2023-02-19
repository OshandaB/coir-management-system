package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Buyer {

    private Button updateBtn;
    private Button deleteBtn;

    private  final StringProperty buyer_id=new SimpleStringProperty();
    private  final StringProperty buyer_name=new SimpleStringProperty();
    private  final StringProperty address=new SimpleStringProperty();
    private  final StringProperty email=new SimpleStringProperty();
    private  final StringProperty contact_number=new SimpleStringProperty();

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

    public String getBuyer_id() {
        return buyer_id.get();
    }

    public StringProperty buyer_idProperty() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id.set(buyer_id);
    }

    public String getBuyer_name() {
        return buyer_name.get();
    }

    public StringProperty buyer_nameProperty() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name.set(buyer_name);
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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
}
