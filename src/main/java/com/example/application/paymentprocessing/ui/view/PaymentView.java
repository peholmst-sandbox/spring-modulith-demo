package com.example.application.paymentprocessing.ui.view;

import com.example.application.paymentprocessing.Payment;
import com.example.application.paymentprocessing.PaymentProcessing;
import com.example.application.sharedkernel.domain.Money;
import com.example.application.sharedkernel.ui.component.ViewToolbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route
@PageTitle("Payment Processing")
@Menu(title = "Payment Processing", order = 3, icon = "vaadin:cash")
public class PaymentView extends Main {

    private final PaymentProcessing paymentProcessing;

    private final TextField referenceNumber;
    private final BigDecimalField amount;
    private final Grid<Payment> paymentGrid;

    public PaymentView(PaymentProcessing paymentProcessing) {
        this.paymentProcessing = paymentProcessing;

        referenceNumber = new TextField();
        referenceNumber.setPlaceholder("Ref No");

        amount = new BigDecimalField();
        amount.setPlaceholder("Amount");

        var payBtn = new Button("Pay", event -> pay());
        payBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        paymentGrid = new Grid<>();
        paymentGrid.setItems(query -> paymentProcessing.listPayments(toSpringPageRequest(query)).stream());
        paymentGrid.addColumn(Payment::paymentId).setHeader("Payment ID");
        paymentGrid.addColumn(Payment::amount).setHeader("Amount");
        paymentGrid.addColumn(Payment::referenceNumber).setHeader("Ref No");
        paymentGrid.addColumn(Payment::paymentDate).setHeader("Payment Date");
        paymentGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Payment Processing", ViewToolbar.group(amount, referenceNumber, payBtn)));
        add(paymentGrid);
    }

    private void pay() {
        paymentProcessing.registerPayment(new Money(amount.getValue()), referenceNumber.getValue());
        amount.clear();
        referenceNumber.clear();
        paymentGrid.getDataProvider().refreshAll();
    }
}
