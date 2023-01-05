package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainC {

	@Autowired
	JdbcTemplate jdbctemplate;
	@Autowired
	Dao dao;

	@Autowired
	ServletContext context;
	public static String uploadDirectory="C:\\Users\\DELL\\Desktop\\b\\ChatServer\\src\\main\\webapp\\Media\\";
	@RequestMapping("/")
	public ModelAndView getMap(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("HI");
		ModelAndView mv= new ModelAndView();
		mv.setViewName("index.jsp");
		return mv;

	}
	@ResponseBody
	@RequestMapping("/pos")
	public List<String> getMap1(HttpServletRequest request, HttpServletResponse response)
	{
		System.out.println("Hi");
		List<String> j=new ArrayList<String>();
		j.add("A");
		j.add("B");
		j.add("C");
		return j;
		//ModelAndView mv= new ModelAndView();
		//mv.setViewName("A.jsp");
		//return mv;


	}

	@RequestMapping("/LogIn")
	public ModelAndView LogIn(HttpServletRequest request, HttpServletResponse response)
	{

		//System.out.println("Hi");
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		UserBean userBean=dao.ValidateLogin(username,password);
		ModelAndView mv=new ModelAndView();
		if(userBean.getUserid()==0)
		{

			mv.setViewName("index.jsp");
			return mv;
		}
		else
		{

			//List<UserDetails> users=dao.getalluserexcept(userBean.getUserid());
			HttpSession session=request.getSession();
			session.setAttribute("userid", userBean.getUserid());
			session.setAttribute("username", userBean.getUsername());
			session.setAttribute("fullName", userBean.getFullName());
			//mv.addObject("users",users);
			mv.setViewName("chat.jsp");
			return mv;


		}


	}


	@ResponseBody
	@RequestMapping("/sendMessage")
	public int insertdate(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		//java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		//System.out.println(date);
		return jdbctemplate.update("insert into allmessage(sender,reciever,message,actulatime,sendershown,recievershown) values('"+(int)session.getAttribute("userid")+"','"+Integer.parseInt(request.getParameter("recievername"))+"','"+request.getParameter("messageareaname").replaceAll("'", "''")+"','"+new java.sql.Timestamp(new java.util.Date().getTime())+"','"+"N','N');");
	}

	@ResponseBody
	@RequestMapping("/fetchallmessage")
	public List<String> fetchallmessage(HttpServletRequest request, HttpServletResponse response)
	{

		System.out.println("palash");
		HttpSession session=request.getSession();
		//List<String> allmessage=dao.fetchallmessage((int)session.getAttribute("userid"),Integer.parseInt(request.getParameter("selectreceivername")),session);
		List<String> allmessage=dao.fetchallmessage((int)session.getAttribute("userid"),Integer.parseInt(request.getParameter("selectreceivername")));
		return allmessage;
	}

	@ResponseBody
	@RequestMapping("/fetchallmessageIteration")
	public List<String> fetchallmessageIteration(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		List<String> allmessage=dao.fetchallmessageIteration((int)session.getAttribute("userid"),Integer.parseInt(request.getParameter("selectreceivername")));
		return allmessage;
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		session.invalidate();
		ModelAndView mv= new ModelAndView();
		mv.setViewName("index.jsp");
		return mv;

	}
	@RequestMapping("/searchPerson")
	@ResponseBody
	public List<String> searchPerson(HttpServletRequest request, HttpServletResponse response)
	{

		HttpSession session=request.getSession();
		String mailid=request.getParameter("personmail");
		List<UserDetails> users=dao.getalluserexcept((int)session.getAttribute("userid"),mailid);
		List<String> allusers=new ArrayList<String>();
		//System.out.println(users.size());
		for(int i=0;i<users.size();i++)
		{
			allusers.add(users.get(i).getUserid()+","+users.get(i).getUsername()+","+users.get(i).getFullname());
		}
		return allusers;
	}



	@ResponseBody
	@RequestMapping("/fileupload")
	public String uploadFile(@RequestParam(required=true, value="files") MultipartFile[] files, @RequestParam(required=true, value="jsondata")String jsondata,HttpServletRequest request) throws IOException  {


		String message=null;
		HttpSession session=request.getSession();
		int first=jsondata.indexOf(':')+2;
		int sec=jsondata.substring(first).indexOf('"');
		String reciever=jsondata.substring(first, first+sec);
		int count=0;
		for (MultipartFile file : files) {
			String originalname=file.getOriginalFilename();
			String originalextension=originalname.substring(originalname.lastIndexOf('.')+1);
			java.sql.Timestamp timestamp=new java.sql.Timestamp(new java.util.Date().getTime());
			Path fileNameAndPath = Paths.get(uploadDirectory,Integer.toString((int)session.getAttribute("userid"))+timestamp.toString().split(" ")[0]+timestamp.toString().substring(timestamp.toString().lastIndexOf('.')+1)+Integer.toString(count)+"."+originalextension);

			try {
				Files.write(fileNameAndPath, file.getBytes());
				String mimetype= new MimetypesFileTypeMap().getContentType(new File(fileNameAndPath.toString()));
				if(mimetype.split("/")[0].equals("image"))
					message="<img src=''Media/"+Integer.toString((int)session.getAttribute("userid"))+timestamp.toString().split(" ")[0]+timestamp.toString().substring(timestamp.toString().lastIndexOf('.')+1)+Integer.toString(count)+"."+originalextension+"'' width=''290px'' > <br>"+originalname+"</img>";
				else
					message="<video controls=''controls'' src=''Media/"+Integer.toString((int)session.getAttribute("userid"))+timestamp.toString().split(" ")[0]+timestamp.toString().substring(timestamp.toString().lastIndexOf('.')+1)+Integer.toString(count)+"."+originalextension+"'' width=''290px'' > <br>"+originalname+"</video>";
				jdbctemplate.update("insert into allmessage(sender,reciever,message,actulatime,sendershown,recievershown) values('"+(int)session.getAttribute("userid")+"','"+Integer.parseInt(reciever)+"','"+message+"','"+new java.sql.Timestamp(new java.util.Date().getTime())+"','"+"N','N');");
				count++;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return "File is uploaded successfully";

	}
}
