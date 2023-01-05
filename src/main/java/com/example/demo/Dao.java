package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class Dao {

	@Autowired
	JdbcTemplate jdbcTemplate;
	public UserBean ValidateLogin(String username, String password)
	{
		UserBean userBean=new UserBean();
		return jdbcTemplate.query("select * from Users where username='"+username+"' and password1='"+password+"' ;",new ResultSetExtractor<UserBean>(){  
			@Override  
			public UserBean extractData(ResultSet rs) throws SQLException,  
			DataAccessException {  

				//List<Employee> list=new ArrayList<Employee>();  
				while(rs.next()){  

					userBean.setUserid(rs.getInt(1));  
					userBean.setUsername(rs.getString(2)); 
					userBean.setFullName(rs.getString(4)+" "+rs.getString(5));

				}  
				return userBean;  
			}  
		});  


	}
	public List<UserDetails> getalluserexcept(int userid, String mailid)
	{
		List<UserDetails> alluser=new ArrayList<UserDetails>();

		return jdbcTemplate.query("select concat(Firstname,' ',Lastname),username,userid from Users where userid<>"+userid+" and username like '"+mailid+"%';",new ResultSetExtractor<List<UserDetails>>(){  
			@Override  
			public List<UserDetails> extractData(ResultSet rs) throws SQLException,  
			DataAccessException {  

				//List<Employee> list=new ArrayList<Employee>();  
				while(rs.next()){  


					UserDetails userDetails=new UserDetails();
					userDetails.setFullname(rs.getString(1));
					userDetails.setUsername(rs.getString(2));
					userDetails.setUserid(rs.getInt(3));
					alluser.add(userDetails);  

				}  
				return alluser;  
			}  
		}); 
	}

	@Transactional
	public List<String> fetchallmessage(int sender,int reciever)
	{


		List<Integer> allsender=jdbcTemplate.query("select messageid from allmessage where sender="+sender+" and reciever="+reciever+" ;",new ResultSetExtractor<List<Integer>>(){
			@Override  
			public List<Integer> extractData(ResultSet rs) throws SQLException,  
			DataAccessException { 

				List<Integer> senderlist=new ArrayList<Integer>();
				while(rs.next()){
					senderlist.add(rs.getInt(1));
				}
				for(int i=0;i<senderlist.size();i++)
					jdbcTemplate.update("update allmessage set sendershown='Y' where messageid="+senderlist.get(i)+";");

				return senderlist;
			}

		});
		for(int i=0;i<allsender.size();i++)
			System.out.println(allsender.get(i));

		List<Integer> allreciever=jdbcTemplate.query("select messageid from allmessage where sender="+reciever+" and reciever="+sender+" ;",new ResultSetExtractor<List<Integer>>(){
			@Override  
			public List<Integer> extractData(ResultSet rs) throws SQLException,  
			DataAccessException { 

				List<Integer> recieverlist=new ArrayList<Integer>();
				while(rs.next()){
					recieverlist.add(rs.getInt(1));
				}
				for(int i=0;i<recieverlist.size();i++)
					jdbcTemplate.update("update allmessage set recievershown='Y' where messageid="+recieverlist.get(i)+";");

				return recieverlist;
			}

		});
		for(int i=0;i<allreciever.size();i++)
			System.out.println(allreciever.get(i));


		allsender.addAll(allreciever);
		List<String> allmessage= new ArrayList<String>();
		String allmessageId=null;
		allmessage.add(Integer.toString(sender));
		int i=0;
		if(allsender.size()>0)
		{
			for(i=0;i<allsender.size()-1;i++)

			{
				if(allmessageId==null)
					allmessageId=allsender.get(i)+",";
				else
					allmessageId+=allsender.get(i)+",";
			}
			if(allmessageId==null)
				allmessageId=Integer.toString(allsender.get(i));
			else
				allmessageId+=Integer.toString(allsender.get(i));

			System.out.println(allmessageId);
			jdbcTemplate.query("select sender,message from allmessage where messageid in ("+allmessageId+");",new ResultSetExtractor<String>(){
				@Override  
				public String extractData(ResultSet rs) throws SQLException,  
				DataAccessException { 

					List<Integer> recieverlist=new ArrayList<Integer>();
					while(rs.next()){
						allmessage.add(Integer.toString(rs.getInt(1))+","+rs.getString(2));

					}
					return null;
				}

			});
			//allmessage.add(a);

		}

		return allmessage;
	}




	@Transactional
	public List<String> fetchallmessageIteration(int sender,int reciever)
	{

		List<Integer> allsender=jdbcTemplate.query("select messageid from allmessage where sender="+sender+" and reciever="+reciever+" and sendershown='N';",new ResultSetExtractor<List<Integer>>(){
			@Override  
			public List<Integer> extractData(ResultSet rs) throws SQLException,  
			DataAccessException { 

				List<Integer> senderlist=new ArrayList<Integer>();
				while(rs.next()){
					senderlist.add(rs.getInt(1));
				}
				for(int i=0;i<senderlist.size();i++)
					jdbcTemplate.update("update allmessage set sendershown='Y' where messageid="+senderlist.get(i)+";");

				return senderlist;
			}

		});
		for(int i=0;i<allsender.size();i++)
			System.out.println(allsender.get(i));

		List<Integer> allreciever=jdbcTemplate.query("select messageid from allmessage where sender="+reciever+" and reciever="+sender+" and recievershown='N';",new ResultSetExtractor<List<Integer>>(){
			@Override  
			public List<Integer> extractData(ResultSet rs) throws SQLException,  
			DataAccessException { 

				List<Integer> recieverlist=new ArrayList<Integer>();
				while(rs.next()){
					recieverlist.add(rs.getInt(1));
				}
				for(int i=0;i<recieverlist.size();i++)
					jdbcTemplate.update("update allmessage set recievershown='Y' where messageid="+recieverlist.get(i)+";");

				return recieverlist;
			}

		});
		for(int i=0;i<allreciever.size();i++)
			System.out.println(allreciever.get(i));


		allsender.addAll(allreciever);
		List<String> allmessage= new ArrayList<String>();
		String allmessageId=null;
		allmessage.add(Integer.toString(sender));
		int i=0;
		if(allsender.size()>0)
		{
			for(i=0;i<allsender.size()-1;i++)

			{
				if(allmessageId==null)
					allmessageId=allsender.get(i)+",";
				else
					allmessageId+=allsender.get(i)+",";
			}
			if(allmessageId==null)
				allmessageId=Integer.toString(allsender.get(i));
			else
				allmessageId+=Integer.toString(allsender.get(i));

			jdbcTemplate.query("select sender,message from allmessage where messageid in ("+allmessageId+");",new ResultSetExtractor<String>(){
				@Override  
				public String extractData(ResultSet rs) throws SQLException,  
				DataAccessException { 

					List<Integer> recieverlist=new ArrayList<Integer>();
					while(rs.next()){
						allmessage.add( Integer.toString(rs.getInt(1))+","+rs.getString(2));

					}
					return null;
				}

			});
		}




		return allmessage; 
	}

}
