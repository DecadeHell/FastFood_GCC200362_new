package Fast_Food;

import Lib.XFile;
import Model.ListOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainDisplay extends JFrame{
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel mainPanel;
    private JTable tbfastfoob;
    private JTextField ID;
    private JTextField Name;
    private JButton sortButton;
    private JComboBox cbsize;
    private JTextField txtquantity;
    JFrame frontScreen;
    DefaultTableModel tbModel;
    DefaultComboBoxModel cbModel = new DefaultComboBoxModel();

    ArrayList<ListOrder> canList;
    String filePath = "manage.dat";
    int currentRow = -1;

public MainDisplay(String title, Login aThis){
    super(title);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(mainPanel);
    this.pack();
    frontScreen = aThis;
    initTable();
    loadCb();
    tbfastfoob.setDefaultEditor(Object.class, null);

    canList = new ArrayList<>();
    boolean file = loadFile();
    if (file){
        fillToTable();
    }else {
        showMess("Nothing to show");
    }
    sortButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sort();
            fillToTable();
        }
    });
    addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            addBt();
        }
    });
    updateButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateBt();
        }
    });
    deleteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            deleteBt();
        }
    });
    tbfastfoob.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            currentRow = tbfastfoob.getSelectedRow();
            showDetail(currentRow);
        }
    });
}

    private void sort() {
        Collections.sort(canList, new Comparator<ListOrder>() {
            @Override
            public int compare(ListOrder o1, ListOrder o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
    private void deleteBt() {
        deleteOrder();
        fillToTable();
        saveFile();
    }

    private void deleteOrder() {
        int re =JOptionPane.showConfirmDialog(this, " +" +
                "Do you want to delete this one?", "Delete Warning",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(re == JOptionPane.YES_OPTION){
            canList.remove(currentRow);}
        resetForm();
    }


    private void resetForm(){
        ID.setText("");
        Name.setText("");
        cbsize.setSelectedIndex(0);
        txtquantity.setText("");
    }

    private void updateBt() {
        updateOrder();
        fillToTable();
        saveFile();
    }

    private void updateOrder() {
        ListOrder c = canList.get(currentRow);
        String id = ID.getText();
        c.setID(id);
        String name = Name.getText();
        c.setName(name);
        String size = cbsize.getSelectedItem().toString();
        c.setSize(size);
        int quantity = Integer.parseInt(txtquantity.getText());
        c.setQuantity(quantity);
    }




    private void addBt() {
        addOrder();
        fillToTable();
        saveFile();
    }

    private void saveFile() {
        XFile.writeObject(filePath, canList);
    }

    private void fillToTable() {
        tbModel.setRowCount(0);
        for(ListOrder c : canList){
            Object[] row = new Object[]{
                    c.getName(), c.getID(), c.getSize(), c.getQuantity()
            };
            tbModel.addRow(row);
        }
    }

    private void addOrder() {
        String name = Name.getText();
        String id = ID.getText();
        String size = cbsize.getSelectedItem().toString();
        int quantity = Integer.parseInt(txtquantity.getText().toString());
        ListOrder c = new ListOrder(name, id, size, quantity);
        canList.add(c);
    }
    private void initTable() {
        String[] columnNames ={"Name","ID","Size","Quantity"};
        tbModel = new DefaultTableModel(columnNames,0);
        tbfastfoob.setModel(tbModel);
    }
    private void loadCb() {
        String[] typeLst = {"Choose your size","small","medium","large"};
        for(String s:typeLst){
            cbModel.addElement(s);
        }
        cbsize.setModel(cbModel);
    }

    private boolean loadFile() {
        if(XFile.readObject(filePath)==null){
            return false;
        }
        canList = (ArrayList<ListOrder>) XFile.readObject(filePath);
        return true;
    }
    private void showDetail(int currentRow){
        ListOrder c = canList.get(currentRow);
        String name = c.getName();
        Name.setText(name);
        String id = c.getID();
        ID.setText(id);
        String size = c.getSize();
        cbsize.setSelectedItem(size);
        int quantity = c.getQuantity();
        txtquantity.setText(String.valueOf(quantity));
    }


    private void showMess(String mess) {
        JOptionPane.showMessageDialog(MainDisplay.this,mess);
    }
//    public static void main(String[] args) {
//        Manager c = new Manager("Fast Food");
//        c.setVisible(true);
//        c.setLocationRelativeTo(null);
//    }
}

