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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

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

  @RequestMapping("/")
  String index() {
    return "index";
  }

  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {

      Statement stmt = connection.createStatement();
      // select * from listings l where ST_DWithin(l.geoloc, 'POINT(33.5763 -111.9275)'::geography, 50);
      ResultSet results = stmt.executeQuery("select * from listings l where ST_DWithin(l.geoloc, 'POINT(33.5763 -111.9275)'::geography, 50);");
      //stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      //stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      //ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
      
      StringBuffer buf = new StringBuffer();
      while (results.next()) {
    	  buf.append("apn: " + results.getString(1) + System.lineSeparator());
    	  buf.append("ListingID: " + results.getInt(2) + System.lineSeparator());
    	  buf.append("DwellingType: " + results.getString(4) + System.lineSeparator());
    	  buf.append("ListPrice: " + results.getFloat(11) + System.lineSeparator());
    	  buf.append("Lat: " + results.getFloat(14) + System.lineSeparator());
    	  buf.append("Lon: " + results.getFloat(15) + System.lineSeparator());
          buf.append("--------------------------");
      }
/*
      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }
*/
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
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }

}