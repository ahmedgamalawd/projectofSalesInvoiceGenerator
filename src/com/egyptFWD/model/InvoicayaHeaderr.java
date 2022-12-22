
package com.egyptFWD.model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoicayaHeaderr {
    private int invoicayaNumberr;
    private String theCustomerNamee;
    private Date invoicayaDatee;
    private ArrayList<InvoicayaLine> linez;  

    public InvoicayaHeaderr(int invoicayaNumberr, String customerName, Date invDate) {
        this.invoicayaNumberr = invoicayaNumberr;
        this.theCustomerNamee = customerName;
        this.invoicayaDatee = invDate;
    }

    
    
    
    public Date getInvoicayaDatee() {
        return invoicayaDatee;
    }

    
    
    
    
    
    public void setInvoicayaDatee(Date invoicayaDatee) {
        this.invoicayaDatee = invoicayaDatee;
    }

    
    
    
    public int getInvoicayaNumberr() {
        return invoicayaNumberr;
    }

    
    
    
    public void toSetInvoicayaNumberr(int invNumb) {
        this.invoicayaNumberr = invNumb;
    }

    
    
    
    public String toGetTheCustomerName() {
        return theCustomerNamee;
    }

    
    
    
    
    
    
    public void setTheCustomerNamee(String theCustomerNamee) {
        this.theCustomerNamee = theCustomerNamee;
    }

    
    
    
    
    
    
    @Override
    public String toString() {
        String str = "InvoiceHeader{" + "invNum=" + invoicayaNumberr + ", customerName=" + theCustomerNamee + ", invDate=" + invoicayaDatee + '}';
        for (InvoicayaLine line : getLinat()) {
            str += "\n\t" + line;
        }
        return str;
    }

    
    
    
    
    
    public ArrayList<InvoicayaLine> getLinat() {
        if (linez == null)
            linez = new ArrayList<>();  // lazy creation
        return linez;
    }

    public void setLinez(ArrayList<InvoicayaLine> linez) {
        this.linez = linez;
    }

    public double getInvTotal() {
        double total = 0.0;
        for (InvoicayaLine line : getLinat()) {
            total += line.getLineTotal();
        }
        return total;
    }
    
    public void addInvLine(InvoicayaLine line) {
        getLinat().add(line);
    }
    
    public String getDataAsCSV() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return "" + getInvoicayaNumberr() + "," + df.format(getInvoicayaDatee()) + "," + toGetTheCustomerName();
    }
    
}
