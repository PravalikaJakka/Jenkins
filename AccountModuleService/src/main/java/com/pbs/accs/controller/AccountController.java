package com.pbs.accs.controller;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pbs.accs.dto.Account;
import com.pbs.accs.dto.Address;
import com.pbs.accs.dto.Customer;
import com.pbs.accs.service.AccountService;
import com.pbs.accs.service.AddressService;
import com.pbs.accs.service.CustomerService;
@CrossOrigin("http://localhost:4200")
@RestController
public class AccountController 
{
	@Autowired
	AccountService accountService;
	public void setAccountService(AccountService accountService)
	{
		this.accountService=accountService;
	}
	
	@Autowired 
	CustomerService customerService;
	public void setCustomerService(CustomerService customerService)
	{
		this.customerService=customerService;
	}
	
	@Autowired
	AddressService addressService;
	public void setAddressService(AddressService addressService)
	{
		this.addressService=addressService;
	}
	
	 /*@Autowired 
	 RestTemplate restTemplate;
     public void setRestTemplate(RestTemplate restTemplate) 
     { 
    	 this.restTemplate=restTemplate; 
     }*/
     
    @GetMapping("/getAccount/{accountNo}")
 	public ResponseEntity<Optional<Account>> getAccount(@PathVariable Long accountNo)
 	{
 		Optional<Account> account =  accountService.getAccount(accountNo);
   	  if(account.isPresent())
   		  return new ResponseEntity<Optional<Account>>(account,HttpStatus.OK);
   	  return new ResponseEntity<Optional<Account>>(account,HttpStatus.NOT_FOUND);
  	}
     
     @PostMapping("/addAccount")
     public ResponseEntity<String> addAccount(@RequestBody() Account account)
     {
    	 try
     	  {
   			account.setStatus("Active");
   			account.setOpenDate(LocalDate.now());
   			accountService.addAccount(account);
     	      return new ResponseEntity<String>("Account created with account number "+account.getAccountNo(),HttpStatus.OK);
     	  }
     	  catch(Exception ex)
     	  {
     	    	return new ResponseEntity<String>(ex.getMessage()+" Account Creation Failed",HttpStatus.BAD_REQUEST);
     	  } 
     }
     
     @PutMapping(value="/updateAccount",consumes="application/json")
 	public ResponseEntity<String> updateAccount(@RequestBody()Account account)
 	{
 		try
 		{
 			accountService.updateAccount(account);
 			return new ResponseEntity<String>("Account Updated",HttpStatus.OK);
 		}
 		catch(Exception ex)
 		{
 			return new ResponseEntity<String>(ex.getMessage()+" Account Update Failed",HttpStatus.BAD_REQUEST);
 		}
 		
 	}
 	
 	@DeleteMapping("/deleteAccount/{accountNo}")
 	public ResponseEntity<String> deleteAccount(@PathVariable Long accountNo)
 	{
 		try
   	  {
 			accountService.deleteAccount(accountNo);
   	    return new ResponseEntity<String>("Deleted Successfully",HttpStatus.OK);
   	  }
   	  catch(Exception ex)
   	  {
   		  return new ResponseEntity<String>("Deletion Failed",HttpStatus.BAD_REQUEST);
   	  }			
 	}
 	
 	@GetMapping("/getCustomer/{customerId}")
 	public ResponseEntity<Optional<Customer>> getCustomer(@PathVariable int customerId)
 	{
 		Optional<Customer> customer =  customerService.getCustomer(customerId);
   	  if(customer.isPresent())
   		  return new ResponseEntity<Optional<Customer>>(customer,HttpStatus.OK);
   	  return new ResponseEntity<Optional<Customer>>(customer,HttpStatus.NOT_FOUND);
 	}
     
     @PostMapping("/addCustomer")
     public ResponseEntity<String> addCustomer(@RequestBody() Customer customer)
     {
    	 try
    	 {
    		 //addressService.addAddress(customer.getAddress());
   			customerService.addCustomer(customer);
     	      return new ResponseEntity<String>("Customer Inserted",HttpStatus.OK);
     	  }
     	  catch(Exception ex)
     	  {
     	    	return new ResponseEntity<String>(ex.getMessage()+" Customer Insertion Failed",HttpStatus.BAD_REQUEST);
     	  } 
     }
     
     @PutMapping(value="/updateCustomer",consumes="application/json")
 	public ResponseEntity<String> updateCustomer(@RequestBody()Customer customer)
 	{
 		try
 		{
 			addressService.updateAddress(customer.getAddress());
 			customerService.updateCustomer(customer);
 			return new ResponseEntity<String>("Customer Updated",HttpStatus.OK);
 		}
 		catch(Exception ex)
 		{
 			return new ResponseEntity<String>(ex.getMessage()+" Customer Update Failed",HttpStatus.BAD_REQUEST);
 		}
 		
 	}
 	
 	@DeleteMapping("/deleteCustomer/{customerId}")
 	public ResponseEntity<String> deleteCustomer(@PathVariable int customerId)
 	{
 		try
   	  {
 			Customer customer=customerService.getCustomer(customerId).get();
 			addressService.deleteAddress(customer.getAddress().getAddressId());
 			customerService.deleteCustomer(customerId);
   	    return new ResponseEntity<String>("Deleted Successfully",HttpStatus.OK);
   	  }
   	  catch(Exception ex)
   	  {
   		  return new ResponseEntity<String>("Deletion Failed",HttpStatus.BAD_REQUEST);
   	  }			
 	} 	
}
