package com.atividade.cinco.events;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class TurmasChangedEvent extends ApplicationEvent {

    public TurmasChangedEvent(Object source) {
        super(source);
    }
}
