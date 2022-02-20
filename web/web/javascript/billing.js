
function shipping(){
    var result = document.getElementById("result");
    var firstname = document.getElementById("firstname").value;
    var lastname = document.getElementById("lastname").value;
    var country = document.getElementById("country").value;
    var state = document.getElementById("states").value;
    var city = document.getElementById("city").value;
    var zip = document.getElementById("zip").value;
    var street = document.getElementById("street").value;
    var landmark = document.getElementById("landmark").value;
    var query = "firstname="+firstname+"&country="+country+"&lastname="+lastname+
            "&state="+state+"&city="+city+"&zip="+zip+"&street="+street+
            "&landmark="+landmark;
    document.getElementById("state").innerHTML = "please wait your request is processing...";
    document.getElementById("loader").style.display="block";
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open('POST',"../jsp/billing.jsp", true);
    xmlHttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlHttp.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            if(this.responseText.trim() === "proceed"){
                document.getElementById("loader").style.display="none";
                 document.getElementById("message").innerHTML = "hit the subscribe button below to complete"+
                         " your subscription";
                 document.getElementById("alert").style.display = "block";
                action();
                window.location.href ="#paySection";
            }else{
                 document.getElementById("loader").style.display="none";
                 document.getElementById("message").innerHTML = this.responseText;
                 document.getElementById("alert").style.display = "block";
            }
            
        }
    };
    xmlHttp.send(query);
    return false;
}
function showBottom(){
    var paypal = document.getElementById("paypal");
    document.getElementById("lastTable").style.display = "block";
    paypal.style.backgroundColor = "#fff";
    paypal.style.borderLeft = "5px solid #233D4D";
}

var topPos = 0;
function action(){
    var topTable = document.getElementById("topfirstTable");
    document.getElementById("shippingBtn").style.display = "none";
    document.getElementsByClassName("drop-down-icon")[0].setAttribute("src","../assets/up.png");
    
    topTable.style.display = "none";
    var paypal = document.getElementById("paypal");
    paypal.style.display = "block";
   
    document.getElementsByClassName("drop-down-icon")[1].setAttribute("src","../assets/down.png");
    showBottom();
    document.getElementsByClassName("drop-down-icon")[1].onclick = function(){
       if(topPos === 0){
           document.getElementsByClassName("drop-down-icon")[1].setAttribute("src","../assets/up.png");
           topPos++;
           document.getElementById("lastTable").style.display = "none";
           document.getElementById("paypal").style.display = "none";
       }else if(topPos === 1){
           document.getElementById("paypal").style.display = "block";
           document.getElementById("lastTable").style.display = "block";
           document.getElementsByClassName("drop-down-icon")[1].setAttribute("src","../assets/down.png");
           topPos--;
       }
   };
}

var position = 0;
document.getElementsByClassName("drop-down-icon")[0].onclick = function(){
   if(position === 0){
       var top = document.getElementById("topfirstTable");
       top.style.display = "none";
       document.getElementsByClassName("drop-down-icon")[0].setAttribute("src","../assets/up.png");
       position++;
       document.getElementById("shippingBtn").style.display = "none";
   }else if(position === 1){
       var top = document.getElementById("topfirstTable");
       top.style.display = "block";
       document.getElementsByClassName("drop-down-icon")[0].setAttribute("src","../assets/down.png");
       position--;
       document.getElementById("shippingBtn").style.display = "block";
   }
};