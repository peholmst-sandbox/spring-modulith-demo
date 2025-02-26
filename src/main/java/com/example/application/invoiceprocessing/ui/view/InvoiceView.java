package com.example.application.invoiceprocessing.ui.view;

import com.example.application.invoiceprocessing.Invoice;
import com.example.application.invoiceprocessing.InvoiceProcessing;
import com.example.application.sharedkernel.ui.component.ViewToolbar;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route
@PageTitle("Invoice Processing")
@Menu(title = "Invoice Processing", order = 2, icon = "vaadin:invoice")
public class InvoiceView extends Main {


    public InvoiceView(InvoiceProcessing invoiceProcessing) {
        var invoiceGrid = new Grid<Invoice>();
        invoiceGrid.setItems(query -> invoiceProcessing.listInvoices(toSpringPageRequest(query)).stream());
        invoiceGrid.addColumn(Invoice::invoiceId).setHeader("Invoice ID");
        invoiceGrid.addColumn(Invoice::orderId).setHeader("Order ID");
        invoiceGrid.addColumn(Invoice::description).setHeader("Description");
        invoiceGrid.addColumn(Invoice::referenceNumber).setHeader("Ref No").setAutoWidth(true);
        invoiceGrid.addColumn(Invoice::amount).setHeader("Amount");
        invoiceGrid.addColumn(Invoice::invoiceDate).setHeader("Invoice Date");
        invoiceGrid.addColumn(Invoice::dueDate).setHeader("Due Date");
        invoiceGrid.addColumn(Invoice::paid).setHeader("Paid");
        invoiceGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Invoice Processing"));
        add(invoiceGrid);
    }
}
