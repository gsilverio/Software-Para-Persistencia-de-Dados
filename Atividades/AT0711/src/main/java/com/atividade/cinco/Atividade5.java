package com.atividade.cinco;

import com.atividade.cinco.view.AppView;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Atividade5 {

	public static void main(String[] args) {
		Application.launch(AppView.class, args);
	}

}
