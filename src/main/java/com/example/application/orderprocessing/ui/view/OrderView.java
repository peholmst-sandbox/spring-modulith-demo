package com.example.application.orderprocessing.ui.view;

import com.example.application.orderprocessing.service.Order;
import com.example.application.orderprocessing.service.OrderDetails;
import com.example.application.orderprocessing.service.OrderService;
import com.example.application.sharedkernel.domain.Money;
import com.example.application.sharedkernel.ui.component.ViewToolbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.Period;

import static com.vaadin.flow.spring.data.VaadinSpringDataHelpers.toSpringPageRequest;

@Route
@PageTitle("Order Processing")
@Menu(title = "Order Processing", order = 1, icon = "vaadin:cart")
public class OrderView extends Main {

    private final OrderService orderService;

    private final TextField description;
    private final BigDecimalField amount;
    private final IntegerField paymentTime;
    private final Grid<Order> orderGrid;

    public OrderView(OrderService orderService) {
        this.orderService = orderService;

        description = new TextField();
        description.setPlaceholder("Order description");

        amount = new BigDecimalField();
        amount.setPlaceholder("Amount");

        paymentTime = new IntegerField();
        paymentTime.setPlaceholder("Payment time (days)");

        var createBtn = new Button("Create", event -> createOrder());
        createBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        orderGrid = new Grid<>();
        orderGrid.setItems(query -> orderService.list(toSpringPageRequest(query)).stream());
        orderGrid.addColumn(Order::id).setHeader("Order ID");
        orderGrid.addColumn(order -> order.details().description()).setHeader("Description");
        orderGrid.addColumn(order -> order.details().amount()).setHeader("Amount");
        orderGrid.addColumn(order -> order.details().paymentTime()).setHeader("Payment Time");
        orderGrid.addColumn(Order::orderDate).setHeader("Order Date");
        orderGrid.setSizeFull();

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Order Processing", ViewToolbar.group(description, amount, paymentTime, createBtn)));
        add(orderGrid);
    }

    private void createOrder() {
        var orderDetails = new OrderDetails(description.getValue(), new Money(amount.getValue()), Period.ofDays(paymentTime.getValue()));
        orderService.createOrder(orderDetails);
        description.clear();
        amount.clear();
        paymentTime.clear();
        orderGrid.getDataProvider().refreshAll();
    }
}
