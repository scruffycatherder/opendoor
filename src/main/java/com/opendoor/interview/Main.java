package com.opendoor.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SpringBootApplication
public class Main {

  @RequestMapping(method=RequestMethod.GET, path="/")
  String index() {
    return "index";
  }

  
  public static void main(String[] args) throws Exception {
	    SpringApplication.run(Main.class, args);
	  }

}
