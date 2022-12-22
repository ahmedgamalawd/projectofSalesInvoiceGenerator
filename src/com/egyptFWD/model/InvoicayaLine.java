
package com.egyptFWD.model;
public class InvoicayaLine {
    private String itmNamee;
    private double itmPriceee;
    private int itmCountee;
    private InvoicayaHeaderr Headersss;

    public InvoicayaLine(String itemName, double itemPrices, int itemCounts, InvoicayaHeaderr header) {
        this.itmNamee = itemName;
        this.itmPriceee = itemPrices;
        this.itmCountee = itemCounts;
        this.Headersss = header;
    }
    
    
    
    public String getItmNamee() {
        return itmNamee;
    }

    
    public void setItmNamee(String itmNamee) {
        this.itmNamee = itmNamee;
    }

    
    
    public double getItmPriceee() {
        return itmPriceee;
    }

    
    public void setItmPriceee(double itmPriceee) {
        this.itmPriceee = itmPriceee;
    }

    
    public int getItmCountee() {
        return itmCountee;
    }

    
    public void setItmCountee(int itmCountee) {
        this.itmCountee = itmCountee;
    }

    
    public InvoicayaHeaderr getHeadersss() {
        return Headersss;
    }

    
    public void setHeadersss(InvoicayaHeaderr Headersss) {
        this.Headersss = Headersss;
    }

    
    @Override
    public String toString() {
        return "InvoiceLine{" + "itemName=" + itmNamee + ", itemprice=" + itmPriceee + ", itemCount=" + itmCountee + '}';
    }
    
    
    public double getLineTotal() {
        return itmCountee * itmPriceee;
    }
    
    
    public String getDataAsCSV() {
        return "" + getHeadersss().getInvoicayaNumberr() + "," + getItmNamee() + "," + getItmPriceee() + "," + getItmCountee();
    }
}
