package cs4111.dao;


import cs4111.form.characterForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;

public class characterDao {

    public List queryCharacters(characterForm form, int page, int pageSize) throws Exception {
        List<characterForm> list = new ArrayList();
        int num1 = pageSize * (page - 1) + 1;
        int num2 = num1 + (pageSize - 1);

        try {
            //connnect
    		Connection conn = null; 
    		ResultSet rset = null; 
    		String error_msg = ""; 
    		try {
    		OracleDataSource ods = new OracleDataSource();
    		ods.setURL("jdbc:oracle:thin:qj2131/YddgaHVC@//w4111f.cs.columbia.edu:1521/ADB"); 
    		conn = ods.getConnection();
    		Statement stmt = conn.createStatement();
            String sql = "select * " +
                    "from character ";
            if(form.getCharacter_name()!=null&&!form.getCharacter_name().equals("")){
                sql+="where name like '%"+form.getCharacter_name()+"%'";
            }
            System.out.println(sql);
    		rset = stmt.executeQuery(sql);
    		} catch (SQLException e) 
    		{ 
    		error_msg = e.getMessage(); if( conn != null ) {
    		conn.close();
    		}
    		}
    		
            while (rset.next()) {
            	characterForm tempForm = new characterForm();
                tempForm.setCharacter_name(judgeNull(rset.getString(1)));
                tempForm.setBirthday(judgeNull(rset.getString(2)));
                tempForm.setImage(judgeNull(rset.getString(3)));
                tempForm.setCast_name(judgeNull(rset.getString(4)));
                tempForm.setNicknames(judgeNull(rset.getString(5)));
                tempForm.setFunniest_quotes(judgeNull(rset.getString(6)));
                list.add(tempForm);
            }
            
    		if( conn != null ) {
    			conn.close();
    		}
            return list;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        
        return list;
    	
    }
    
    public String doDelete(characterForm form)throws Exception{
        Connection conn=null;
        String sql="update character set birthday = '123' where name = '"+form.getCharacter_name()+"'";
        System.out.println(sql);
        try{
        	OracleDataSource ods = new OracleDataSource();
    		ods.setURL("jdbc:oracle:thin:qj2131/YddgaHVC@//w4111f.cs.columbia.edu:1521/ADB"); 
    		conn = ods.getConnection();
    		Statement stmt = conn.createStatement();
    		ResultSet rset = null; 
    		rset = stmt.executeQuery(sql);
    		return "success";
        }catch (Exception ex){
            if(conn!=null){
                conn.rollback();
            }
            ex.printStackTrace();
            return "fail";
        }finally {
    		if( conn != null ) {
    			conn.close();
    		}
        }

    }
    
    public String judgeNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

 }

