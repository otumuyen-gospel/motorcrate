/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function sendMail(){
    var result = document.getElementById("result");
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var description = document.getElementById("description").value;
    var subject = document.getElementById("subject").value;
    var query = "name="+name+"&email="+email+"&description="+description+
            "&subject="+subject;
    document.getElementById("state").innerHTML = "sending message to recipient...";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/contact.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            document.getElementById("loader").style.display="none";
            document.getElementById("message").innerHTML = this.responseText;
            document.getElementById("alert").style.display = "block";
        }
    };
    xmlHttp.send(query);
}

function signUp(){
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirm = document.getElementById("confirm").value;
    var referral_id = document.getElementById("referral_id").value;
    var query = "firstname="+firstname+"&email="+email+"&lastname="+lastname+
            "&password="+password+"&confirm="+confirm+"&referral_id="+referral_id;
    document.getElementById("state").innerHTML = "creating user account...";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/signup.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            if(this.responseText.trim() === "created"){
               document.getElementById("state").innerHTML = "signing "+email+" in...";
               window.location.href = "../account/";
            }else{
                document.getElementById("loader").style.display="none";
                document.getElementById("message").innerHTML = this.responseText;
                document.getElementById("alert").style.display = "block";
            }
        }
    };
    xmlHttp.send(query);
    
}
function signOut(){
    
    var query = "req=signout";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/signout.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    document.getElementById("state").innerHTML = "signing out...";
    document.getElementById("loader").style.display="block";
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            if(this.responseText.trim() === "out"){
               window.location.href = "../sign in/";
            }else{
                document.getElementById("loader").style.display="none";
                document.getElementById("message").innerHTML = this.responseText;
                document.getElementById("alert").style.display = "block";
            }
        }
    };
    xmlHttp.send(query);
    
}
function signIn(){
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var query = "email="+email+"&password="+password;
    document.getElementById("state").innerHTML = "authenticating "+email+"...";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/signin.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            if(this.responseText.trim() === "authenticated"){
               document.getElementById("state").innerHTML = "signing "+email+" in...";
               window.location.href = "../account/";
            }else if(this.responseText.trim() === "coupon"){
               document.getElementById("state").innerHTML = "redirecting back to coupon page...";
               window.location.href = "../redeem a motor crate/";
            }else if(this.responseText.trim() === "billing"){
               document.getElementById("state").innerHTML = "redirecting to billing page...";
               window.location.href = "../billing/";
            }else{
                document.getElementById("loader").style.display="none";
                document.getElementById("message").innerHTML = this.responseText;
                document.getElementById("alert").style.display = "block";
            }
            
        }
    };
    xmlHttp.send(query);
}

function UpdateProfile(){
    var result = document.getElementById("result");
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var confirm = document.getElementById("confirm").value;
    var query = "firstname="+firstname+"&email="+email+"&lastname="+lastname+
            "&password="+password+"&confirm="+confirm;
    
    document.getElementById("state").innerHTML = "Updating Account...";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/profile.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
             document.getElementById("loader").style.display="none";
             document.getElementById("message").innerHTML = this.responseText.trim();
             document.getElementById("alert").style.display = "block";
            if(this.responseText.trim() === "updated"){
                 document.getElementById("profile").style.display = "none";
                 document.getElementById("message").innerHTML = "Account Updated";
                 document.getElementById("alert").style.display = "block";
            }
        }
    };
    xmlHttp.send(query);
}


function forgot(){
    var result = document.getElementById("result");
    var email = document.getElementById("email").value;
    var query = "email="+email;
    document.getElementById("state").innerHTML = "sending reset link to "+email+"...";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/forgot.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            if(this.responseText.trim() === "authenticated"){
               document.getElementById("state").innerHTML = "redirecting user to confirm page..."; 
               window.location.href = "../forgot password confirm/";
            }else{
                 document.getElementById("loader").style.display = "none";
                 document.getElementById("message").innerHTML = this.responseText;
                 document.getElementById("alert").style.display = "block";
            }
            
        }
    };
    xmlHttp.send(query);
}

function resend(email){
    var query = "email="+email;
    document.getElementById("state").innerHTML = "resending reset link to "+email+"...";
    document.getElementById("loader").style.display="block";
    
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/forgot.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            if(this.responseText.trim() === "authenticated"){
               
               document.getElementById("loader").style.display = "none";
               document.getElementById("message").innerHTML = "message sent successfully";
               document.getElementById("alert").style.display = "block";
            }else{
                 document.getElementById("loader").style.display = "none";
                 document.getElementById("message").innerHTML = this.responseText;
                 document.getElementById("alert").style.display = "block";
            }
            
        }
    };
    xmlHttp.send(query);
}


function reset(){
    var result = document.getElementById("result");
    var password = document.getElementById("password").value;
    var confirm = document.getElementById("confirm").value;
    var query = "password="+password+"&confirm="+confirm;
    document.getElementById("state").innerHTML = "resetting password...";
    document.getElementById("loader").style.display="block";
    
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/reset.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            document.getElementById("loader").style.display = "none";
            document.getElementById("message").innerHTML = this.responseText;
            document.getElementById("alert").style.display = "block";
        }
    };
    xmlHttp.send(query);
}

function redeemer(){
    code = document.getElementById("code").value;
    var query = "code="+code;
    document.getElementById("state").innerHTML = "Processing request...";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/redeem.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
             document.getElementById("loader").style.display="none";
             document.getElementById("message").innerHTML= this.responseText.trim();
             document.getElementById("alert").style.display="block";
        }
    };
    xmlHttp.send(query);
}
function showOptions(index){
    document.getElementsByClassName('options')[index].style.display='block';
}
function hideOptions(){
    document.getElementsByClassName('options')[0].style.display='none';
    document.getElementsByClassName('options')[1].style.display='none';
}
function setOptions(index, duration){
    document.getElementsByClassName('duration')[index].innerHTML=duration;
    hideOptions(index);
}
document.addEventListener("click", function(evt) {
    var select = document.getElementsByClassName("select");
        targetElement = evt.target;  // clicked element

    do {
        if (targetElement === select[0]) {
            // show options 0 and return
            hideOptions();
            showOptions(0);
            return;
        }else if(targetElement === select[1]){
            // show options 1 and return
            hideOptions();
            showOptions(1);
            return;
        }
        // Go up the DOM
        targetElement = targetElement.parentNode;
    } while (targetElement);

    // This is a click outside.
    hideOptions();
});

function plan(type){
    var duration = document.getElementsByClassName("duration")[type].innerHTML;
    var result = document.getElementsByClassName("result")[type];
    var category = document.getElementsByClassName("category")[type].innerHTML;
    var query = "duration="+duration+"&category="+category;
    
    document.getElementById("state").innerHTML = "Processing request...";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/plan.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            if(this.responseText.trim() === "login"){
                window.location.href = "../sign in/";
            }else if(this.responseText.trim() === "authenticated"){
                window.location.href = "../billing/";
            }else{
                document.getElementById("loader").style.display="none";
                document.getElementById("message").innerHTML= this.responseText.trim();
                document.getElementById("alert").style.display="block";
            }
        }
    };
    xmlHttp.send(query);
}

function customerOperation(){
    var type = document.getElementById("type").value;
    var why = document.getElementById("why").value;
    var query = "type="+type+"&why="+why;
    if(type === "cancel"){
         document.getElementById("state").innerHTML = "Canceling Subscription...";
    }
    document.getElementById("dialog").style.display="none";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/operation.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            document.getElementById("loader").style.display="none";
            document.getElementById("message").innerHTML= this.responseText.trim();
            document.getElementById("alert").style.display="block";
            if(this.responseText.trim() === "closed"){
                 window.location.href = "../sign in/";
                 window.history.forward(); 
            }
        }
    };
    xmlHttp.send(query);
}


