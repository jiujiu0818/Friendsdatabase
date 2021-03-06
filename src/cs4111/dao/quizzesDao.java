package cs4111.dao;


import cs4111.form.quizzesForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;

public class quizzesDao {

    public List queryQuizzes(quizzesForm form, int page, int pageSize) throws Exception {
        List<quizzesForm> list = new ArrayList();
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
                    "from quizzes ";
            if(form.getQuiz_no()!=null&&!form.getQuiz_no().equals("")){
                sql+="where quiz_no ='"+form.getQuiz_no()+"'";
            }
            if(form.getCorrect_answer()!=null&&!form.getCorrect_answer().equals("")){
                sql+="and correct_answer= '"+form.getCorrect_answer()+"'";
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
            	quizzesForm tempForm = new quizzesForm();
                tempForm.setQuiz_no(judgeNull(rset.getString(1)));
                tempForm.setTopic(judgeNull(rset.getString(2)));
                tempForm.setQuestion(judgeNull(rset.getString(3)));
                tempForm.setCorrect_answer(judgeNull(rset.getString(4)));
                tempForm.setAnswera(judgeNull(rset.getString(5)));
                tempForm.setAnswerb(judgeNull(rset.getString(6)));
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
    
    public String judgeNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

 }

