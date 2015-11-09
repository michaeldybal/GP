package com.gp.dao;
import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
 /**
  * Class, which establishes a connection to the database
  *
  */
public class DerbyDBManager {
   private static Connection con = null ;    
   private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
   private static final String url = "jdbc:derby:" ;
   private static String dbName = null;
   /**
    * logger object
    */
   private static final Logger log = LogManager.getLogger();
    
   /**
    * Constructor
    * @param myDBName - database name
    */
   public DerbyDBManager(String myDBName) {
       dbName=myDBName;
       if(!dbExists()) {
           try {
               Class.forName(driver) ;
               con = DriverManager.getConnection(url + dbName + ";create=true");
           } catch (ClassNotFoundException e) {
        	   log.error("ClassNotFoundException DerbyDBManager");
           } catch (SQLException e) {
        	   log.error("SQLException DerbyDBManager");
           }
       }
   }
    
   /**
    * Verifies the existence of the database
    * @return bool result
    */
   private Boolean dbExists()
   {
       Boolean exists = false ;
       try {
           Class.forName(driver) ;
           con = DriverManager.getConnection(url + dbName);
           exists = true ;
       } catch(Exception e) {
       }
       return(exists) ;
   }

   /**
    * Performs Insert, Update, Delete queries
    * @param sql - query
    * @throws SQLException
    */
   public void executeUpdate(String sql) throws SQLException {
       Statement stmt = con.createStatement() ;
       stmt.close() ;
   }  

   /**
    * Performs Select query
    * @param sql - query
    * @return - ResultSet
    * @throws SQLException
    */
   public ResultSet executeQuery(String sql) throws SQLException {
       Statement stmt = con.createStatement() ;
       ResultSet result = stmt.executeQuery(sql) ;
       return result;
   }
}