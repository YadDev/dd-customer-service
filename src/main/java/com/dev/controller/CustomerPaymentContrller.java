package com.dev.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dev.entities.Customer;
import com.dev.entities.CustomerPayment;
import com.dev.entities.PaymentUpdateRequest;
import com.dev.exception.DevanshException;
import com.dev.services.CustomerPaymentService;

@RestController
@RequestMapping("/api")
public class CustomerPaymentContrller {

	@Autowired 
	CustomerPaymentService custPaymentService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());
	
	@GetMapping("/payments")
	public ResponseEntity<Object> getCustomerPaymentStatus(){
		LOGGER.info("In Get Customer Payment Controller");
		return ResponseEntity.ok(custPaymentService.getAllCustomerPaymentDetail());
		
	}
	
	@GetMapping("/payments/{custNumber}")
	public ResponseEntity<Object> getCustomerPayDetail(@PathVariable String custNumber, @RequestParam Optional<String> where){
		LOGGER.info("In Get Customer Payment Controller");
		Collection<CustomerPayment> custPayament = null;
		if(where.isEmpty()) {
			custPayament = custPaymentService.getCustomerPaymentDetail(custNumber);
		}
		else {
			LOGGER.info("where query param is missing");
			String finalQuery = "where custNumber='"+ custNumber +"' and "+where.get();
			custPayament =  (Collection<CustomerPayment>) custPaymentService.getCustomerPaymentDetailWithQuery(finalQuery, null);
		}
		return ResponseEntity.ok(custPayament);
		
	}
	
	@PostMapping("/payment")
	public ResponseEntity<Object> createCustomerPayment(@RequestBody CustomerPayment customerPayment){
		LOGGER.info("In Create Customer Payment Controller");
		return ResponseEntity.ok(custPaymentService.createCustomerPayment(customerPayment));
		
	}
	@PutMapping("/payment")
	public ResponseEntity<Object> updateCustomerPaymentAgainstPaymentId(@RequestBody PaymentUpdateRequest payUpdateReq){
		LOGGER.info("In Update Customer Payment Controller");
		try {
			return ResponseEntity.ok(custPaymentService.updateCustomerPayment(payUpdateReq));
		} catch (DevanshException e) {
			return ResponseEntity.internalServerError().body(""+e.getMessage());
		}
		
	}
	
	@PostMapping("/uploadPayment")
	public ResponseEntity<Object> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
	    
	    List<CustomerPayment> listOfCustomer = null;
	   
	    try(Workbook workbook=  WorkbookFactory.create(reapExcelDataFile.getInputStream())){
	    	listOfCustomer = excelReader(workbook);
	    	return ResponseEntity.ok(custPaymentService.uploadCustomerPayment(listOfCustomer));
	    }
	    catch(IOException e) {
	    	return ResponseEntity.internalServerError().body("Error while updating Bulk Payment!");
	    }
	 
	   
	}
	
	List<CustomerPayment> excelReader(Workbook workbook){
		List<CustomerPayment> listOfCustomer = new ArrayList<>();
		 Sheet sheet = null;
			sheet = workbook.getSheet("Task Activity");
		    for(int i=1;i<sheet.getPhysicalNumberOfRows() ;i++) {
		    	CustomerPayment tempStudent = new CustomerPayment();
		            
		        Row row = sheet.getRow(i);
		        Customer cust = new Customer();
		        cust.setCustNumber(row.getCell(0).getStringCellValue());
		        tempStudent.setCustomer(cust);
		        tempStudent.setPaymentForMonth(row.getCell(1).getStringCellValue());
		        listOfCustomer.add(tempStudent);   
		    }
		    
		    System.out.println("Excel sheet read "+listOfCustomer);
		    return listOfCustomer;
	}
}
