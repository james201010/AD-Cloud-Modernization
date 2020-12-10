<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="com.appdynamics.cloud.modern.web.utils.ConfigHelper" %>

<html lang="en">
<head>
<title>AD Financial</title>
<link rel="icon" href="images/favicon.png" type = "image/x-icon">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">



<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
* {
  box-sizing: border-box;
}

.content {
  max-width: 1440px;
  margin: auto;
}

/* Style the body */
body {
  font-family: Arial, Helvetica, sans-serif;
  color: black;
  margin: 0;
}

/* Sticky DIV - toggles between relative and fixed, depending on the scroll position. It is positioned relative until a given offset position is met in the viewport - then it "sticks" in place (like position:fixed). The sticky value is not supported in IE or Edge 15 and earlier versions. However, for these versions the navbar will inherit default position  */
.stickydiv {
  overflow: hidden;
  position: sticky;
  position: -webkit-sticky;
  top: 0;
}

/* Header/logo Title */
.header {
  padding: 10px;
  text-align: left;
  background: #ffffff;
  color: #d8251b;
}

.header .search-container {
  float: right;
}

.header input[type=text] {
  padding: 6px;
  margin-top: 16px;
  font-size: 14px;
  border: solid;
  color: black;
  border-width: thin;
  border-color: #5a5a5a;
}

.header .search-container button {
  float: right;
  padding: 6px;
  margin-top: 16px;
  margin-right: 0px;
  height: 30px;
  color: white;
  background: #5a5a5a;
  font-size: 16px;
  border: solid;
  border-width: thin;
  border-left-color: none;
  border-color: #5a5a5a;
  cursor: pointer;
}

.header .search-container button:hover {
  background: #ccc;
}

/* Righ hand of Header */
.header a.right {
  font-size: 18px;
  text-align: center;
  display: block;
  padding: 24px 2px;
  color: black;
  float: right;
  border: solid;
  border-width: thin;
  text-decoration: none;
}

/* Navbar */
.navbar {
  font-size: 18px;
  background-color: #204162;
  overflow: hidden;
  position: sticky;
  position: -webkit-sticky;
  top: 0;
}

/* Style the navigation bar links */
.navbar a {
  float: left;
  display: block;
  color: white;
  text-align: center;
  padding: 22px 36px;
  text-decoration: none;
}


/* Right-aligned link */
.navbar a.right {
  float: right;
}

/* Change color on hover */
.navbar a:hover {
  background-color: cbcbcb;
  color: black;
}

/* Active/current link */
.navbar a.active {
  background-color: #d8251b;
  color: white;
}


.login-welcome {
  background-color: white;
  color: #757575;
  font-size: 16px;
  padding: 10px;
  margin-left: 20px;
}

.accounts-title {
  background-color: white;
  color: #2770b8;
  font-size: 30px;
  padding: 10px;
  margin-left: 20px;
  margin-top: 4px;
}

.accounts-title .right-stuff {
  background-color: white;
  color: #2770b8;
  font-size: 16px;
  padding: 10px;
  margin-right: 10px;
  float: right;
  cursor: pointer;
}

.grid-container {
  display: grid;
  grid-template-columns: auto auto auto;
  background-color: white;
  padding: 20px;
}

.grid-item {
  border-radius: 15px;
  background-color: #204162;
  border: 10px solid rgba(255, 255, 255, 255);
  padding: 20px;
  font-size: 12px;
  text-align: left;
  color: white;
  height: 250px;
}

.grid-item a {
  color: white;
  text-decoration: none;
}

.grid-item:hover {
  background-color: #cbcbcb;
  color: black;
}
.grid-item a:hover {
  color: black;
}
.grid-item .acct-name {
  font-size: 22px;
}

.grid-item .acct-name .dots {
  float: right;
}
.grid-item .acct-number {
  font-size: 18px;
}
.grid-item .acct-avalbal {
  font-size: 30px;
}


.grid-container-2 {
  display: grid;
  grid-template-columns: auto auto;
  background-color: white;
}

.grid-item-2 {
  border: 10px solid rgba(255, 255, 255, 255);
  padding: 10px;
  font-size: 12px;
  text-align: left;
  color: black;
}

.grid-item-2 .title-text {
  background-color: white;
  color: #2770b8;
  font-size: 30px;
  padding: 10px;
  margin-left: 0px;
  margin-top: 0px;
}

/* Footer */
.footer {
  padding: 20px;
  text-align: center;
}


.grid-container-footer {
  display: grid;
  grid-template-columns: auto auto auto auto;
  padding: 20px;
}

.grid-item-footer {
  border-radius: 5px;
  border: 10px;
  padding: 10px;
  font-size: 18px;
  text-align: left;
  color: #4d4d4d;
  height: 280px;
}

.grid-item-footer .title-text {
  font-size: 18px;
  color: #204162;
  padding: 10px;
}

.grid-item-footer .body-text {
  font-size: 12px;
  color: #4d4d4d;
  padding: 10px;
  cursor: pointer;
}

</style>
    
    
</head>


<body>

<div class="content">

	<div class="stickydiv">
	
		<div class="header">
		  <img src="images/logo.png">
		  <div class="search-container">
		    <form action="/action_page.php">
		      <input type="text" placeholder="Search.." name="search">
		      <button type="submit"><i class="fa fa-search"></i></button>
		    </form>
		  </div>
		</div>
		
		<div class="navbar">
		  <a href="/">Home</a>
		  <a href="#" class="active">Accounts</a>
		  <a href="#">Transfers</a>
		  <a href="#">Wires</a>
		  <a href="#">Pay Bills</a>
		  <a href="#">Money Management</a>
		  <a href="#" class="right">About Us</a>
		</div>
	
	</div>
	
	<br>
    <div class="login-welcome">
	   Welcome, ${firstName} ${lastName}.&nbsp;&nbsp; Your last login was ${lastLogin}  
	</div>
    <div class="accounts-title">
	    Accounts
	    <div class="right-stuff"><i class="fa fa-plus-square" aria-hidden="true"></i> New Account&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-th" aria-hidden="true"></i>&nbsp;&nbsp;<i class="fa fa-list-ul" aria-hidden="true"></i></div>
	</div>	
	      
	<div class="grid-container">
	
	    <c:forEach var="acct" items="${cashAccounts}">	
	      
		  <div class="grid-item">
		  <a href="cashacctdetails/?accountNumber=<c:out value="${acct.accountNumber}"/>">
			<div class="acct-name">${acct.accountName}<div class="dots"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></div></div>			  
			<div class="acct-number">${acct.accountNumber}</div>
			<br><br>
			<div class="acct-avalbal"><fmt:formatNumber type="currency" value="${acct.avalBalance}" /></div>
			<div class="acct-number">Available Balance</div>	
			<br>
			<div class="acct-number">Current Balance</div>
			<div class="acct-number"><fmt:formatNumber type="currency" value="${acct.currentBalance}" /></div>
		  </a>
		  </div>
		  		
		</c:forEach>
		
	    <c:forEach var="acct" items="${loanAccounts}">	
		  <div class="grid-item">
		  <a href="loanacctdetails/?accountNumber=<c:out value="${acct.accountNumber}"/>">
			<div class="acct-name">${acct.accountName}<div class="dots"><i class="fa fa-ellipsis-v" aria-hidden="true"></i></div></div>			  
			<div class="acct-number">${acct.accountNumber}</div>
			<br><br>
			<div class="acct-avalbal"><fmt:formatNumber type="currency" value="${acct.currentBalance}" /></div>
			<div class="acct-number">Current Balance</div>	
			<br>
			<div class="acct-number">Payment <fmt:formatNumber type="currency" value="${acct.paymentDueAmt}" /></div>
			<div class="acct-number">Due ${acct.paymentDueDate}</div>
	      </a>
		  </div>		
		</c:forEach>		      
	
	</div>

	<div class="grid-container-2">
	

	  <div class="grid-item-2">
	    <div class="title-text">
		   Upcoming Bills
		   <br><br>
		   <img src="images/7-days-dropdown.png">
		   <br>
		   <img src="images/billpay-header.png">
		   <br>
		   &nbsp;<img src="images/billpay-text.png">

		</div><br>	
		<div class="title-text">
		   My Credit Score
		   <br><br>
		   <img src="images/credit-score.png">
		</div>
	  </div>
	  		
	  <div class="grid-item-2">
	    <div class="title-text">
		   Services<br><br>
		   <img src="images/acct-summary-services.png">
		</div>

	  </div>
	
	</div>


<br><br>

<div class="footer">
  
      <div class="grid-container-footer">
      
       <div class="grid-item-footer">
          <div class="title-text">Helpful Information</div>
	       <div class="body-text" >
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Help & Support<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Contact Us<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Locations<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Fraud Prevention<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Forms<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Accessibility<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Sitemap<br>
	       </div>
                 
       </div>
       <div class="grid-item-footer">
          <div class="title-text">Company Information</div>
	       <div class="body-text" >
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;About Us<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Leadership<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;News<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Investor Relations<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;SEC Filings<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Investor News<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Careers<br>
	       </div>          
       </div>
       <div class="grid-item-footer">
          <div class="title-text">Products and Services</div>
	       <div class="body-text" >
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Personal Banking<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Personal Investments<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Personal Insurance<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Business Banking<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Business Investments<br>
	         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Business Insurance<br>
	       </div>                    
       </div>
       <div class="grid-item-footer">
          <div class="title-text">Connect with Us</div>
	       <div class="body-text" >
	         <i class="fa fa-facebook-square fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Facebook<br>
	         <i class="fa fa-youtube-play fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;YouTube<br>
	         <i class="fa fa-twitter-square fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Twitter<br>
	         <i class="fa fa-instagram fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Instagram<br>
	         <i class="fa fa-linkedin-square fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;LinkedIn<br>
	         Social Media Terms of Use<br>
	       </div>          
          
       </div>

    </div>
    
    
</div>


</div>
</body>
</html>