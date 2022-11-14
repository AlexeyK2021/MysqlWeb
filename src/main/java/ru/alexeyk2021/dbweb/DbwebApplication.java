package ru.alexeyk2021.dbweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.alexeyk2021.dbweb.managers.DbManager;
import ru.alexeyk2021.dbweb.managers.LoginManager;

@SpringBootApplication
public class DbwebApplication {
	/*public static LoginManager loginManager;
	public static DbManager dbManager;*/

	public static void main(String[] args) {
		/*loginManager = LoginManager.getInstance();
		dbManager = DbManager.getInstance();*/
		SpringApplication.run(DbwebApplication.class, args);
	}

}
