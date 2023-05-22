package Model;

import java.io.Serializable;

public class ListOrder implements Serializable {
    private String Name;
    private String ID;
    private String Size;
    private int Quantity;

    public ListOrder(String Name, String ID, String Size, int Quantity){
        this.Name = Name;
        this.ID = ID;
        this.Size = Size;
        this.Quantity = Quantity;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
