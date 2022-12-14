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
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.dev.exception.DevanshException;
import com.dev.model.PaymentUpdateRequest;
import com.dev.services.CustomerPaymentService;
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	
	@SuppressWarnings("unchecked")
	@GetMapping("/payments/{custNumber}")
	public ResponseEntity<Object> getCustomerPayDetail(@PathVariable String custNumber, @RequestParam Optional<String> where){
		LOGGER.info("In Get Customer Payment Controller");
		Collection<CustomerPayment> custPayament = new ArrayList<>();
		CustomerPayment custPay = null;
		if(where.isEmpty()) {
			custPayament = custPaymentService.getCustomerPaymentDetail(custNumber);
		}
		else {
			LOGGER.info("where query param is missing");
			custPay =  custPaymentService.getCustomerPaymentDetailWithQuery(custNumber, where.get());
			custPayament.add(custPay);
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
			sheet = workbook.getSheet("Sheet1");
		    for(int i=0;i<sheet.getPhysicalNumberOfRows() ;i++) {
		    	CustomerPayment custPayment = new CustomerPayment();
		            
		        Row row = sheet.getRow(i);
		        Customer cust = new Customer();
		        cust.setCustNumber(""+row.getCell(0).getStringCellValue());
		        custPayment.setCustomer(cust);
		        custPayment.setPreviousBalance(Double.valueOf(row.getCell(1).getNumericCellValue()));
		        custPayment.setCurrentMonthBalance(Double.valueOf(row.getCell(2).getNumericCellValue()));
		        custPayment.setPaymentForMonth(""+row.getCell(3).getStringCellValue());
		        custPayment.setNetTotal(custPayment.getCurrentMonthBalance()+custPayment.getPreviousBalance());
		        custPayment.setAmountPaid(Double.valueOf(row.getCell(4).getNumericCellValue()));
		        custPayment.setGrandTotal(custPayment.getNetTotal()-custPayment.getAmountPaid());
		        listOfCustomer.add(custPayment);   
		    }
		    
		    System.out.println("Excel sheet read Successful "+listOfCustomer);
		    return listOfCustomer;
	}
}
