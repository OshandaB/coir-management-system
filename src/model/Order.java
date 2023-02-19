package model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Order {

    private Button updateBtn;
    private Button deleteBtn;

    private  final StringProperty order_id=new SimpleStringProperty();
    private  final StringProperty buyer_id=new SimpleStringProperty();
    private  final StringProperty order_date=new SimpleStringProperty();
    private  final StringProperty order_time=new SimpleStringProperty();
    private  final StringProperty payment=new SimpleStringProperty();

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

    public String getOrder_id() {
        return order_id.get();
    }

    public StringProperty order_idProperty() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id.set(order_id);
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

    public String getOrder_date() {
        return order_date.get();
    }

    public StringProperty orderProperty() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date.set(order_date);
    }

    public String getOrder_time() {
        return order_time.get();
    }

    public StringProperty order_timeProperty() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time.set(order_time);
    }

    public String getPayment() {
        return payment.get();
    }

    public StringProperty  payment_numberProperty() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment.set(payment);
    }
}

