<%@ page language="java" %>
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
  color: black;
  border: solid;
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

/* Column container */
.row {  
  display: -ms-flexbox; /* IE10 */
  display: flex;
  -ms-flex-wrap: wrap; /* IE10 */
  flex-wrap: wrap;
}

/* Create two unequal columns that sits next to each other */
/* Sidebar/left column */
.side {
  -ms-flex: 25%; /* IE10 */
  flex: 25%;
  background-color: white;
  padding: 20px;
}

/* Main column */
.main {   
  -ms-flex: 75%; /* IE10 */
  flex: 75%;
  background-color: white;
  background-image: url("images/background.png");
  padding: 20px;
}

/* login panel */
.login {
  font-size: 18px;
  color: white;
  background-color: #d8251b;
  width: 100%;
  padding: 20px;
}

/* Full-width input fields */
.login input[type=text], input[type=password] {
  font-size: 18px;
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  color: black;
  display: inline-block;
  border: 1px solid white;
  box-sizing: border-box;
}

.login input[type=checkbox] {
  font-size: 18px;
  width: 30px;
  height: 30px;
  padding: 12px 20px;
  margin: 8px 0;
  text-align: center;
  border-style: none;
  border-color: white;
}


/* Set a style for all buttons */
.login button {
  font-size: 18px;
  background-color: #d8251b;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: solid;
  border-width: thin;
  boder-color: white;
  cursor: pointer;
  width: 100%;
}

.login button:hover {
  background-color: #2770b8;
}

/* find a branch */
.find-branch {
  font-size: 18px;
  color: white;
  background-color: #0073cf;
  width: 100%;
  padding: 20px;
  cursor: pointer;
}

/* schedule appointment */
.sched-appt {
  font-size: 18px;
  color: white;
  background-color: #0052c2;
  width: 100%;
  padding: 20px;
  cursor: pointer;
}


/* card and iphone */
.top-main {
  width: 100%;
  padding: 20px;
}

/* Set a style for all buttons */
.top-main button {
  font-size: 18px;
  background-color: #204162;
  color: white;
  padding: 14px 14px;
  margin: 4px 0;
  border: solid;
  float: left;
  border-width: thin;
  boder-color: white;
  cursor: pointer;
  width: 250px;
}

.top-main button:hover {
  background-color: #2770b8;
}

/* Fake image, just for this example */
.fakeimg {
  background-color: #aaa;
  width: 100%;
  padding: 20px;
}



/* Responsive layout - when the screen is less than 700px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 700px) {
  .row {   
    flex-direction: column;
  }
}

/* Responsive layout - when the screen is less than 400px wide, make the navigation links stack on top of each other instead of next to each other */
@media screen and (max-width: 400px) {
  .navbar a {
    float: none;
    width: 100%;
  }
}


.checking-blurb-1 {
  font-size: 40px;
  color: #d8251b;
  width: 100%;
  padding: 10px;
}

.grid-container-blurb {
  display: grid;
  grid-template-columns: auto auto;
  padding: 20px;
}

.grid-item-blurb {
  border-radius: 5px;
  border: 10px;
  padding: 10px;
  font-size: 18px;
  text-align: left;
  color: #4d4d4d;
  height: 280px;
}

.side-blurb-1 {
  font-size: 22px;
  color: #d8251b;
  padding: 10px;
}

.side-blurb-1 .body-title {
  font-size: 16px;
  color: #4d4d4d;
  padding: 10px;
}

.side-blurb-1 .body-text {
  font-size: 16px;
  color: #4d4d4d;
  padding: 10px;
}

.side-blurb-1 a {
  font-size: 16px;
  color: #4d4d4d;
  text-decoration: none;
}

.side-blurb-1 a:hover {
  color: #d8251b;
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
      <input type="text" placeholder="Search.." name="search" style="width:200px;">
      <button type="submit"><i class="fa fa-search"></i></button>
    </form>
  </div>
</div>

<div class="navbar">
  <a href="#" class="active">Home</a>
  <a href="#">Checking</a>
  <a href="#">Savings</a>
  <a href="#">Credit Cards</a>
  <a href="#">Personal Lending</a>
  <a href="#">Business Lending</a>
  <a href="#">Investing</a>
  <a href="#" class="right">About Us</a>
</div>

</div>

<br>
<div class="row">
  <div class="side">
    
    <div class="login" style="height:364px;">
      <form action="/login" method="POST">
        <input type="text" placeholder="Online ID" name="username" required>
        <input type="password" placeholder="Passcode" name="password" required>
        <input type="checkbox" name="remember">&nbsp;&nbsp;Remember me<br><br>
        <button type="submit">Sign In</button>
        <br><br>Forgot ID/Passcode?
      </form>  
      <br>

    </div>
    <div>&nbsp;</div>
    <div class="find-branch" style="height:70px;">
      <i class="fa fa-map-marker"></i>&nbsp;&nbsp;Find your closest branch or ATM
    </div><div class="sched-appt" style="height:60px;">
      <i class="fa fa-calendar"></i>&nbsp;&nbsp;Schedule and Appointment
    </div>
    <br><br>
    <div class="side-blurb-1" style="height:200px;"><i class="fa fa-mobile fa-3x" aria-hidden="true"></i>&nbsp;&nbsp;Online & Mobile Banking
       <div class="body-title" >
         With online and mobile banking, you can easily:
       </div>
       <div class="body-text" >
         <a href="#"><i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Pay your bills</a><br>
         <a href="#"><i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Transfer your funds</a><br>
         <a href="#"><i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Manage alerts and more</a><br>
       </div>
       
    </div><br><br>

    <div class="side-blurb-1" style="height:300px;"><i class="fa fa-shield fa-2x" aria-hidden="true"></i>&nbsp;&nbsp;Secure Account Alerts
       <div class="body-title" >
         Receive real-time, custom alerts sent straight to your phone to help stop fraud in its tracks.
       </div>
       <div class="body-text" >
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Declined transactions<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Online purchases<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Transactions outside the US<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Pay at the pump transactions<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Purchases over a specific amount<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;ATM withdrawals<br>
       </div>
       
    </div><br>
    
    <div class="side-blurb-1" style="height:300px;"><i class="fa fa-university fa-2x" aria-hidden="true"></i>&nbsp;&nbsp;Banking Services
       <div class="body-title" >
         Whatever your banking needs are, either for personal or business, we've got you covered.
       </div>
       <div class="body-text" >
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Checking<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Savings<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Money Market<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Personal Loans<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Business Loans<br>
         <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;Private Banking<br>
       </div>
       
    </div><br>

  </div>
  
  
  <div class="main">
    <h1>Open a checking account</h1>
    <h3>Consider the benefits of opening an AD Financial banking account</h3>
    <div class="top-main">
      <button type="submit">Open account</button>  
    </div>
    <div class="top-main" style="height:200px;"><img src="images/card-and-iphone.png"></div>
    <br><br><br><br><br><br><br><br><br><br>
    
    <div class="checking-blurb-1"><i class="fa fa-usd fa-2x" aria-hidden="true"></i>&nbsp;&nbsp;Who doesn't like free?</div>
    <div class="grid-container-blurb">
       <div class="grid-item-blurb"><img src="images/couple-selfie.png"></div>
       <div class="grid-item-blurb"><br>Have a lot of spare time? Probably not. Have a lot of money to waste? Definitely not. 
       That's why AD Financial checking accounts embrace the concept of 'free.' <br><br>
       We offer electronic banking tools and 24/7 access to your money, 
       freeing up your time for work and family and everything else. <br><br>
       And we hold the line on fees and surcharges, which frees up your money 
       for the better things in life. With our Free Checking, you have the 
       opportunity to earn interest ... and it's so simple to do.</div>
    
    </div><br><br>
    
    <div class="checking-blurb-1" style="text-align: right;"><i class="fa fa-home fa-2x" aria-hidden="true"></i>&nbsp;&nbsp;Home sweet home&nbsp;&nbsp;&nbsp;</div>
    <div class="grid-container-blurb">
       <div class="grid-item-blurb"><img src="images/home-lending.png"></div>
       <div class="grid-item-blurb">Whether you're looking to buy your dream home, take out some equity in your existing home to remodel, 
       put in that new pool, or just want to save money by taking advantage of lower interest rates, AD Financial has a broad range of mortgage 
       financing options to fit your needs with the lowest interest rates available. <br><br>
       
       <i class="fa fa-trophy fa-1x" aria-hidden="true"></i>&nbsp;&nbsp;
       Earlier this year AD Financial was rated #1 in customer satisfaction for mortgage financing services!
       
       </div>
    
    </div>
    
    <br>

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
