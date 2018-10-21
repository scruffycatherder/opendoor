/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.opendoor.interview;

import org.springframework.core.SpringVersion;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
//@Repository

@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping(method=RequestMethod.GET, path="/")
  String index() {
    return "index";
  }

  @RequestMapping(method=RequestMethod.GET, path="/db")
  String db(Map<String, Object> model) {

  	try {
		Class.forName("org.postgresql.Driver");
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    try (Connection connection = dataSource.getConnection()) {


      Statement stmt = connection.createStatement();
      ResultSet results = stmt.executeQuery("select * from listings l where ST_DWithin(l.geoloc, 'POINT(33.5763 -111.9275)'::geography, 50);");

      
      StringBuffer buf = new StringBuffer();
      while (results.next()) {
    	  buf.append("apn: " + results.getString(1) + System.lineSeparator());
    	  buf.append("<br>");
    	  buf.append("ListingID: " + results.getInt(2) + System.lineSeparator());
    	  buf.append("<br>");
    	  buf.append("DwellingType: " + results.getString(4) + System.lineSeparator());
    	  buf.append("<br>");
    	  buf.append("ListPrice: " + results.getFloat(11) + System.lineSeparator());
    	  buf.append("<br>");
    	  buf.append("Lat: " + results.getFloat(14) + System.lineSeparator());
    	  buf.append("<br>");
    	  buf.append("Lon: " + results.getFloat(15) + System.lineSeparator());
    	  buf.append("<br>");
          buf.append("--------------------------");
    	  buf.append("<br>");
      }
      
      buf.append("<br>Spring version: " + SpringVersion.getVersion());
	  buf.append("<br>");


      model.put("records", buf.toString());
      return "db";
     
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      System.err.println("************** DBURL is NULL OR EMPTY");
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      System.err.println("**************  DBURL is : " + dbUrl);
      dbUrl = dbUrl.replace("postgres", "jdbc:postgresql");
      config.setJdbcUrl(dbUrl);

      return new HikariDataSource(config);
    }
  }

}
