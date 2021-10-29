package com.revature.project1.main;

import io.javalin.Javalin;

import com.revature.project1.controller.RequestController;
import com.revature.project1.controller.UserController;

public class App {
		
    public static void main( String[] args ) {
		Javalin app = Javalin.create(config -> {
			config.enableCorsForAllOrigins();
			config.addStaticFiles("/public");
		}).start(7000);
		
        app.get("/", ctx -> ctx.redirect("/signin.html"));
        app.post("/signin.html", UserController.signIn);
        
        app.post("/employee.html", ctx -> {
        	String selection = ctx.formParam("selection");
        	if (selection.equals("Submit a Request")) {
        		ctx.redirect("request_form.html");
        	} else if (selection.equals("View My Requests")) {
        		ctx.redirect("my_requests.html");
        	} else {
        		ctx.redirect("signin.html");
        	}
        });
        app.post("/request_form.html", RequestController.add);
        app.post("/my_requests", RequestController.findMine);
        
        app.post("/update_form.html", RequestController.update);
    }
}
    
