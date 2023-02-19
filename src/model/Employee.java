package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class Employee {
    private Button updateBtn;
    private Button deleteBtn;

    private  final StringProperty employee_id=new SimpleStringProperty();
    private  final StringProperty employee_name=new SimpleStringProperty();
    private  final StringProperty salary=new SimpleStringProperty();
    private  final StringProperty address=new SimpleStringProperty();
    private  final StringProperty contact_number=new SimpleStringProperty();

    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public Button getUpdateBtn() {
        return updateBtn;
    }

    public void setUpdateBtn(Button updateBtn) {
        this.updateBtn = updateBtn;
    }


    public String getEmployee_id() {
        return employee_id.get();
    }

    public StringProperty employee_idProperty() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id.set(employee_id);
    }

    public String getEmployee_name() {
        return employee_name.get();
    }

    public StringProperty employee_nameProperty() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name.set(employee_name);
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

    public String getSalary() {
        return salary.get();
    }

    public StringProperty salaryProperty() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary.set(salary);
    }
}
