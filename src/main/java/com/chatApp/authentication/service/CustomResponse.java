package com.chatApp.authentication.service;

import com.chatApp.authentication.mapper.MetaResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponse {

    public static <T> ResponseEntity<Map> ok(T data){
        //Prepare meta info
        MetaResponse metaInfo = getMeta("SUCCESS", 200, false);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        map.put("data", data);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static <T>ResponseEntity<Map> ok(){
        MetaResponse metaInfo = getMeta("SUCCESS", 200, false);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static <T>ResponseEntity<Map> ok(T data, HttpHeaders httpHeaders){
        //Prepare meta info
        MetaResponse metaInfo = getMeta("SUCCESS", 200, false);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        map.put("data", data);
        return ResponseEntity.ok().headers(httpHeaders).body(map);
    }

    public static ResponseEntity<Map> forbidden() {
        return forbidden("You don't have access to perform this action");
    }

    public static ResponseEntity<Map> forbidden(String errorMessage){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(errorMessage, 403, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<Map> internalServerError(){
        //Prepare meta info
        MetaResponse metaInfo = getMeta("ERROR", 500, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Map> internalServerError(String msg){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(msg, 500, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<Map> badRequest(){
        //Prepare meta info
        MetaResponse metaInfo = getMeta("ERROR", 400, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Map> badRequest(String msg){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(msg, 400, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Map> badRequest(Object data, String msg){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(msg, 400, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        map.put("data", data);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Map> unAuthorised(String msg){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(msg, 401, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    /*
     * Use this response to return list data
     */
    public static <T> ResponseEntity<Map> paginatedResponse(T data, long count, boolean showFeedbackPopup) {
        MetaResponse metaInfo = getMeta("SUCCESS", 200, false);
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("count", count);
        map.put("showFeedbackPopup", showFeedbackPopup);
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public static <T>ResponseEntity<Map> created(){
        MetaResponse metaInfo = getMeta("SUCCESS", 201, false);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    public static ResponseEntity<Map> notfound(String msg) {
        //Prepare meta info
        MetaResponse metaInfo = getMeta(msg, 404, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    public static MetaResponse getMeta(String msg, int statusCode, boolean isError) {
        MetaResponse metaInfo = new MetaResponse();
        metaInfo.setMsg(msg);
        metaInfo.setStatusCode(statusCode);
        metaInfo.setError(isError);
        return metaInfo;
    }

    public static ResponseEntity<Map> notFound(String errorMessage){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(errorMessage, 404, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<Map> conflict(String errorMessage){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(errorMessage, 409, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }
   /* public static ResponseEntity<Map> conflict(List<Employee> employeeList,String errorMessage){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(errorMessage, 409, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        map.put("data", employeeList);
        return new ResponseEntity<>(map, HttpStatus.CONFLICT);
    }*/
    public static ResponseEntity redirect(String url) {
        return new ResponseEntity<>(url, HttpStatus.SEE_OTHER);
    }
    
    public static ResponseEntity<Map> tooManyRequest(String errorMessage){
        //Prepare meta info
        MetaResponse metaInfo = getMeta(errorMessage, 429, true);
        //Prepare response
        Map<String, Object> map = new HashMap<>();
        map.put("meta", metaInfo);
        return new ResponseEntity<>(map, HttpStatus.TOO_MANY_REQUESTS);
    }

}


