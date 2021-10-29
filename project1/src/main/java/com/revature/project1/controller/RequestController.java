package com.revature.project1.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.revature.project1.model.Request;
import com.revature.project1.service.RequestService;
import com.revature.project1.service.RequestServiceImplementation;

import io.javalin.http.Handler;

public class RequestController {

	private static RequestService requestService = RequestServiceImplementation.getRequestService();
	private static Gson gson = new Gson();
	
	public static Handler add = (ctx) -> {
		int userId = Integer.parseInt(ctx.formParam("userId"));
    	double amount = Double.parseDouble(ctx.formParam("amount"));
    	String reason = ctx.formParam("reason");
    	Date requestDate = new Date(System.currentTimeMillis());
    	String status = "pending";
    	Request request = new Request();
    	request.setRequester(userId);
    	request.setAmount((float) amount);
    	request.setReason(reason);
    	request.setRequestDate(requestDate);
    	request.setStatus(status);
    	requestService.add(request);
	};
	
	public static Handler findById = (ctx) -> {
		String requestId = ctx.pathParam("requestId");
		try {
			Request request = requestService.findById(Integer.parseInt(requestId));
			String json = gson.toJson(request);
			ctx.result(json);
			ctx.status(200);
		} catch (Exception ex) {
			ctx.status(404);
			System.out.println(ex.getMessage());
			// error handling
		}
	};
	
	public static Handler findMine = (ctx) -> {
		String status = ctx.queryParam("status");
		//String sortRequestDate = ctx.queryParam("sort_request_date");
		int user = UserController.getUserId();
		List<Request> userRequests = requestService.findByRequester(user);
		List<Request> requests = new ArrayList<Request>();
		if (status != null) {
			if (status.compareToIgnoreCase("pending") == 0) {
				List<Request> pendingRequests = requestService.findPending();
				for(Request r: userRequests) {
					if (pendingRequests.contains(r)) {
						requests.add(r);
					}
				}
			} else if (status.compareToIgnoreCase("approved") == 0) {
				List<Request> approvedRequests = requestService.findApproved();
				for (Request r: userRequests) {
					if (approvedRequests.contains(r)) {
						requests.add(r);
					}
				}
			} else if (status.compareToIgnoreCase("denied") == 0) {
				List<Request> deniedRequests = requestService.findDenied();
				for (Request r: userRequests) {
					if (deniedRequests.contains(r)) {
						requests.add(r);
					}
				}
			}
		} else {
			List<Request> sorted = requestService.sortDecisionDateAscending();
			for (Request r: sorted) {
				if (userRequests.contains(r)) {
					requests.add(r);
				}
			}
		}
		String json = gson.toJson(requests);
		ctx.result(json);	
	};
	
	public static Handler findAll = (ctx) -> {
		String requester = ctx.queryParam("requester");
		String decidingManager = ctx.queryParam("deciding_manager");;
		String status = ctx.queryParam("status");
		String sortAmount = ctx.queryParam("sort_amount");
		String sortRequestDate = ctx.queryParam("sort_request_date");
		String sortDecisionDate = ctx.queryParam("sort_decision_date");
		List<Request> requests = new ArrayList<Request>();
		if (requester != null) {
			requests = requestService.findByRequester(Integer.parseInt(requester));
		} else if (decidingManager != null) {
			requests = requestService.findByDecidingManager(Integer.parseInt(decidingManager));
		} else if (status != null) {
			if (status.compareToIgnoreCase("approved") == 0) {
			    requests = requestService.findApproved();
			} else if (status.compareToIgnoreCase("denied") == 0) {
				requests = requestService.findDenied();
			} else {
				requests = requestService.findPending();
			}
		} else if (sortAmount != null) {
			if (sortAmount.compareToIgnoreCase("asc") == 0) {
				requests = requestService.sortAmountAscending();
			} else {
				requests = requestService.sortAmoundDescending();
			}
		} else if (sortRequestDate != null) {
			if (sortRequestDate.compareToIgnoreCase("asc") == 0) {
				requests = requestService.sortRequestDateAscending();
			} else {
				requests = requestService.sortRequestDateDescending();
			}
		} else { 
			if (sortDecisionDate.compareToIgnoreCase("asc") == 0) {
				requests = requestService.sortDecisionDateAscending();
			} else {
				requests = requestService.sortDecisionDateDescending();
			}
		}
		String json = gson.toJson(requests);
		ctx.result(json);
		ctx.status(200);
    };
    
    public static Handler update = (ctx) -> {
    	String body = ctx.body();
    	Request request = gson.fromJson(body, Request.class);
    	Request returnedRequest = requestService.update(request);
    	ctx.result(gson.toJson(returnedRequest));
    	ctx.status(202);
    };
}