package com.egyptFWD.controller;
 //here we can set the controller

import com.egyptFWD.model.InvoicayaHeaderr;

import com.egyptFWD.model.InvoicayaHeaderTableModel;

import com.egyptFWD.model.InvoicayaLine;

import com.egyptFWD.model.InvoicayaLinesTableModel;

import com.egyptFWD.view.InvoicayaFrame;

import com.egyptFWD.view.InvoicayaHeaderDialog;

import com.egyptFWD.view.InvoicayaLineDialog;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.FileWriter;

import java.io.IOException;

import java.text.DateFormat;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;

import javax.swing.JFileChooser;

import javax.swing.JOptionPane;

import javax.swing.event.ListSelectionEvent;

import javax.swing.event.ListSelectionListener;


public class SalesInvListenner implements ActionListener, ListSelectionListener {

    private InvoicayaFrame theFrame;
    private DateFormat thedateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public SalesInvListenner(InvoicayaFrame framee) {
        this.theFrame = framee;
    }

    @Override
    public void actionPerformed(ActionEvent c) {

        switch (c.getActionCommand()) {
            case "CreateNewInvoice":
                displayNewInvoiceDialog();
                break;
            case "DeleteInvoice":
                toDeleteInvoicaya();
                break;
            case "CreateNewLine":
                displayNewLineDialog();
                break;
            case "DeleteLine":
                toDeleteLine();
                break;
            case "LoadFile":
                loadFilee();
                break;
            case "SaveFile":
                saveTheData();
                break;
            case "createInvCancel":
                createInvCancel();
                break;
            case "createInvOK":
                createInvOK();
                break;
            case "createLineCancel":
                toCreateLineCancel();
                break;
            case "createLineOK":
                toCreateLineOK();
                break;
        }
    }

    private void loadFilee() {
        JOptionPane.showMessageDialog(theFrame, "Please, select header file!", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser openTheFile = new JFileChooser();
        int elResults = openTheFile.showOpenDialog(theFrame);
        if (elResults == JFileChooser.APPROVE_OPTION) {
            File headerFile = openTheFile.getSelectedFile();
            try {
                FileReader headerFrame = new FileReader(headerFile);
                BufferedReader headerBrr = new BufferedReader(headerFrame);
                String headerLinee = null;

                while ((headerLinee = headerBrr.readLine()) != null) {
                    String[] headerrParts = headerLinee.split(",");
                    String invNumString = headerrParts[0];
                    String invDateString = headerrParts[1];
                    String customerrName = headerrParts[2];

                    int invoiceeNumberr = Integer.parseInt(invNumString);
                    Date invoiceeDate = thedateFormat.parse(invDateString);

                    InvoicayaHeaderr invoicat = new InvoicayaHeaderr(invoiceeNumberr, customerrName, invoiceeDate);
                    theFrame.getInvoicatList().add(invoicat);
                }

                JOptionPane.showMessageDialog(theFrame, "Please, select lines file!", "Attension", JOptionPane.WARNING_MESSAGE);
                elResults = openTheFile.showOpenDialog(theFrame);
                if (elResults == JFileChooser.APPROVE_OPTION) {
                    File linesFiles = openTheFile.getSelectedFile();
                    BufferedReader TheLineBr = new BufferedReader(new FileReader(linesFiles));
                    String linesLine = null;
                    while ((linesLine = TheLineBr.readLine()) != null) {
                        String[] linePartss = linesLine.split(",");
                        String invoiceNumberString = linePartss[0];
                        String itemNameString = linePartss[1];
                        String itemPriceString = linePartss[2];
                        String itemCountString = linePartss[3];

                        int invoicatNumbers = Integer.parseInt(invoiceNumberString);
                        double itmePrices = Double.parseDouble(itemPriceString);
                        int itmeCounts = Integer.parseInt(itemCountString);
                        InvoicayaHeaderr header = findInvoicayaByNumber(invoicatNumbers);
                        InvoicayaLine invoiceLinewahed = new InvoicayaLine(itemNameString, itmePrices, itmeCounts, header);
                        header.getLinat().add(invoiceLinewahed);
                    }
                    theFrame.setInvoiceHeaderTableModel(new InvoicayaHeaderTableModel(theFrame.getInvoicatList()));
                    theFrame.getInvoicesTable().setModel(theFrame.getInvoiceHeaderTableModel());
                    theFrame.getInvoicesTable().validate();
                }
                System.out.println("Checkk");
            } catch (ParseException x) {
                x.printStackTrace();
                JOptionPane.showMessageDialog(theFrame, "Date Format Error\n" + x.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException x) {
                x.printStackTrace();
                JOptionPane.showMessageDialog(theFrame, "Number Format Error\n" + x.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FileNotFoundException x) {
                x.printStackTrace();
                JOptionPane.showMessageDialog(theFrame, "File Error\n" + x.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException x) {
                x.printStackTrace();
                JOptionPane.showMessageDialog(theFrame, "Read Error\n" + x.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        displayInvoicat();
    }

    private void saveTheData() {
        String headersString = "";
        String linat = "";
        for (InvoicayaHeaderr header : theFrame.getInvoicatList()) {
            headersString += header.getDataAsCSV();
            headersString += " \n";
            for (InvoicayaLine line : header.getLinat()) {
                linat += line.getDataAsCSV();
                linat += " \n";
            }
        }
        JOptionPane.showMessageDialog(theFrame, "Please, select file to save header data!", "Attension", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int TheResult = fileChooser.showSaveDialog(theFrame);
        if (TheResult == JFileChooser.APPROVE_OPTION) {
            File headerFile = fileChooser.getSelectedFile();
            try {
                FileWriter nGsW = new FileWriter(headerFile);
                nGsW.write(headersString);
                nGsW.flush();
                nGsW.close();

                JOptionPane.showMessageDialog(theFrame, "Please, select file to save lines data!", "Attension", JOptionPane.WARNING_MESSAGE);
                TheResult = fileChooser.showSaveDialog(theFrame);
                if (TheResult == JFileChooser.APPROVE_OPTION) {
                    File linesFile = fileChooser.getSelectedFile();
                    FileWriter IGsW = new FileWriter(linesFile);
                    IGsW.write(linat);
                    IGsW.flush();
                    IGsW.close();
                }
            } catch (Exception x) {
                JOptionPane.showMessageDialog(theFrame, "Error: " + x.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(theFrame, "Data saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

    }

    
    
    
    private InvoicayaHeaderr findInvoicayaByNumber(int invoicayaNumber) {
        InvoicayaHeaderr theHeader = null;
        for (InvoicayaHeaderr invoicaya : theFrame.getInvoicatList()) {
            if (invoicayaNumber == invoicaya.getInvoicayaNumberr()) {
                theHeader = invoicaya;
                break;
            }
        }
        return theHeader;
    }

    
    
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Invoice Selected!");
        invoicesTableRowSelected();
    }

    
    
    
    
    private void invoicesTableRowSelected() {
        int selectedRowIndex = theFrame.getInvoicesTable().getSelectedRow();
        if (selectedRowIndex >= 0) {
            InvoicayaHeaderr row = theFrame.getInvoiceHeaderTableModel().getInvoicatLists().get(selectedRowIndex);
            theFrame.getCustNameTF().setText(row.toGetTheCustomerName());
            theFrame.getInvDateTF().setText(thedateFormat.format(row.getInvoicayaDatee()));
            theFrame.getInvNumLbl().setText("" + row.getInvoicayaNumberr());
            theFrame.getInvTotalLbl().setText("" + row.getInvTotal());
            ArrayList<InvoicayaLine> lines = row.getLinat();
            theFrame.setInvoiceLinesTableModel(new InvoicayaLinesTableModel(lines));
            theFrame.getInvLinesTable().setModel(theFrame.getInvoiceLinesTableModel());
            theFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        }
    }

    private void displayNewInvoiceDialog() {
        theFrame.setHeaderDialog(new InvoicayaHeaderDialog(theFrame));
        theFrame.getHeaderDialog().setVisible(true);
    }

    private void displayNewLineDialog() {
        theFrame.setLineDialog(new InvoicayaLineDialog(theFrame));
        theFrame.getLineDialog().setVisible(true);
    }

    private void createInvCancel() {
        theFrame.getHeaderDialog().setVisible(false);
        theFrame.getHeaderDialog().dispose();
        theFrame.setHeaderDialog(null);
    }

    private void createInvOK() {
        String custName = theFrame.getHeaderDialog().getCustomerNameField().getText();
        String invDateStr = theFrame.getHeaderDialog().getInvDateeField().getText();
        theFrame.getHeaderDialog().setVisible(false);
        theFrame.getHeaderDialog().dispose();
        theFrame.setHeaderDialog(null);
        try {
            Date invDate = thedateFormat.parse(invDateStr);
            int invNum = toGetNextInvoiceNum();
            InvoicayaHeaderr invoiceHeader = new InvoicayaHeaderr(invNum, custName, invDate);
            theFrame.getInvoicatList().add(invoiceHeader);
            theFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(theFrame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        displayInvoicat();
    }

    private int toGetNextInvoiceNum() {
        int max = 0;
        for (InvoicayaHeaderr header : theFrame.getInvoicatList()) {
            if (header.getInvoicayaNumberr() > max) {
                max = header.getInvoicayaNumberr();
            }
        }
        return max + 1;
    }

    private void toCreateLineCancel() {
        theFrame.getLineDialog().setVisible(false);
        theFrame.getLineDialog().dispose();
        theFrame.setLineDialog(null);
    }

    private void toCreateLineOK() {
        String itemName = theFrame.getLineDialog().getItemNameField().getText();
        String itemCountStr = theFrame.getLineDialog().getItmCountField().getText();
        String itemPriceStr = theFrame.getLineDialog().getItemPriceField().getText();
        theFrame.getLineDialog().setVisible(false);
        theFrame.getLineDialog().dispose();
        theFrame.setLineDialog(null);
        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);
        int headerIndex = theFrame.getInvoicesTable().getSelectedRow();
        InvoicayaHeaderr invoice = theFrame.getInvoiceHeaderTableModel().getInvoicatLists().get(headerIndex);

        InvoicayaLine invoiceLine = new InvoicayaLine(itemName, itemPrice, itemCount, invoice);
        invoice.addInvLine(invoiceLine);
        theFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        theFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        theFrame.getInvTotalLbl().setText("" + invoice.getInvTotal());
        displayInvoicat();
    }

    private void toDeleteInvoicaya() {
        int invIndex = theFrame.getInvoicesTable().getSelectedRow();
        InvoicayaHeaderr Headers = theFrame.getInvoiceHeaderTableModel().getInvoicatLists().get(invIndex);
        theFrame.getInvoiceHeaderTableModel().getInvoicatLists().remove(invIndex);
        theFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        theFrame.setInvoiceLinesTableModel(new InvoicayaLinesTableModel(new ArrayList<InvoicayaLine>()));
        theFrame.getInvLinesTable().setModel(theFrame.getInvoiceLinesTableModel());
        theFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        theFrame.getCustNameTF().setText(" ");
        theFrame.getInvDateTF().setText(" ");
        theFrame.getInvNumLbl().setText(" ");
        theFrame.getInvTotalLbl().setText(" ");
        displayInvoicat();
    }

    private void toDeleteLine() {
        int lineIndexes = theFrame.getInvLinesTable().getSelectedRow();
        InvoicayaLine line = theFrame.getInvoiceLinesTableModel().getInvoiceLinez().get(lineIndexes);
        theFrame.getInvoiceLinesTableModel().getInvoiceLinez().remove(lineIndexes);
        theFrame.getInvoiceLinesTableModel().fireTableDataChanged();
        theFrame.getInvoiceHeaderTableModel().fireTableDataChanged();
        theFrame.getInvTotalLbl().setText("" + line.getHeadersss().getInvTotal());
        displayInvoicat();
    }

    private void displayInvoicat() {
        System.out.println("**************");
        for (InvoicayaHeaderr TheHeader : theFrame.getInvoicatList()) {
            System.out.println(TheHeader);
        }
        System.out.println("***************");
    }

}
