package com.example.application.timemachine.ui.view;

import com.example.application.sharedkernel.ui.component.ViewToolbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.modulith.moments.support.TimeMachine;

import java.time.Duration;

@Route
@PageTitle("Time Machine")
@Menu(title = "Time Machine", order = 20, icon = "vaadin:time-forward")
public class TimeMachineView extends Main {

    public TimeMachineView(TimeMachine timeMachine) {
        var today = new DatePicker("Today");
        today.setValue(timeMachine.today());
        today.setReadOnly(true);

        var jumpBtn = new Button("Jump forward 1 week", event -> {
            timeMachine.shiftBy(Duration.ofDays(7));
            today.setValue(timeMachine.today());
        });
        jumpBtn.addThemeVariants(ButtonVariant.LUMO_WARNING);

        setSizeFull();
        addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.MEDIUM, LumoUtility.Gap.SMALL);

        add(new ViewToolbar("Time Machine"), today, jumpBtn);
    }
}
