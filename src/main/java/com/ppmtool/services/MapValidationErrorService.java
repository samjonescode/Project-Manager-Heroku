package com.ppmtool.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

//adding to branch Sam
@Service
public class MapValidationErrorService {

	public static ResponseEntity<?> MapValidationService(BindingResult result) {
		if (result.hasErrors()) {

			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
		}
		Map<String, String> successMap = new HashMap<>();
		return new ResponseEntity<Map<String, String>>(successMap,HttpStatus.CREATED); //fix this lol
	}

	public MapValidationErrorService() {
	}


}
