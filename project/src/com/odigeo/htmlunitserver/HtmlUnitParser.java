package com.odigeo.htmlunitserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


/**
 * Returns the parsed HTTP for a provided URL
 */
@WebServlet(description = "Returns the parsed HTTP for a provided URL", urlPatterns = { "/HtmlUnitParser" })
public class HtmlUnitParser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HtmlUnitParser() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * The requst will include an url with an urlencoded URL that we should parse with htmlunit and return.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 String url = request.getParameter("url");
		 
		 if (url == null) {
			 response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		 } else {
			 PrintWriter out = response.getWriter();
			 String decodedUrl = URLDecoder.decode(url, "UTF-8");
			 /*
			  *  The link may contain an escaped fragment, which we shall remove for example.
			  *  www.example.com/ajax.html?_escaped_fragment_=key=value becomes
			  *  www.example.com/ajax.html#!key=value
			  */
			 decodedUrl = decodedUrl.replace("?_escaped_fragment_=", "#!");

			 final WebClient webClient = new WebClient();
			 final HtmlPage page = webClient.getPage(decodedUrl);
		     out.println(page.asXml());
		 }
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
