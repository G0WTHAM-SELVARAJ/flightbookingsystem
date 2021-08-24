package controllers;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;

public class CORSFilter implements Filter {

	
    public Result filter(FilterChain filterChain, Context context) {
    	Result result;
        if( context.getMethod().equalsIgnoreCase("OPTIONS") ) {
            result = Results.json();
        }
        result = filterChain.next(context);
        result.addHeader("Access-Control-Allow-Origin", "*");
        result.addHeader("Access-Control-Max-Age", "60000");
	result.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
	result.addHeader("Access-Control-Allow-Headers", "Content-type, application/json");
        return result;
    }
}
