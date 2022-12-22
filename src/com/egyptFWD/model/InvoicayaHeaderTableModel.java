package com.egyptFWD.model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
public class InvoicayaHeaderTableModel extends AbstractTableModel {

    private List<InvoicayaHeaderr> invoicatLists;
    private DateFormat dateeformat = new SimpleDateFormat("dd-MM-yyyy");

    public InvoicayaHeaderTableModel(List<InvoicayaHeaderr> invoicatList) {
        this.invoicatLists = invoicatList;
    }

    public List<InvoicayaHeaderr> getInvoicatLists() {
        return invoicatLists;
    }

    
    
    
    
    
    
    
    @Override
    public int getRowCount() {
        return invoicatLists.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "the Invoice Number";
            case 1:
                return "the Customer Name";
            case 2:
                return "the Invoice Date";
            case 3:
                return "the Invoice Total";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnsIndex) {
        switch (columnsIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Double.class;
            default:
                return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowsIndex, int columnsIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowsIndex, int columnsIndex) {
        InvoicayaHeaderr rows = invoicatLists.get(rowsIndex);

        switch (columnsIndex) {
            case 0:
                return rows.getInvoicayaNumberr();
            case 1:
                return rows.toGetTheCustomerName();
            case 2:
                return dateeformat.format(rows.getInvoicayaDatee());
            case 3:
                return rows.getInvTotal();
            default:
                return "";
        }

    }

}
