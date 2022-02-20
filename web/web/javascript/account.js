/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function openForm(){
    document.getElementById("profile").style.display="block";
}
function closeForm(){
    document.getElementById("profile").style.display="none";
}

document.getElementById("closer").onclick= function(){
    closeForm();
};
function cancelSubscription(){
    document.getElementById("type").value = "cancel";
    document.getElementById("issue").innerHTML = "You Are About To Cancel Your Subscription";
    document.getElementById("dialog").style.display="block";
}

function myFunction() {
  var copyText = document.getElementById("myInput");
  copyText.select();
  copyText.setSelectionRange(0, 99999);
  document.execCommand("copy");
  
  var tooltip = document.getElementById("myTooltip");
  tooltip.innerHTML = "Copied";
}

function outFunc() {
  var tooltip = document.getElementById("myTooltip");
  tooltip.innerHTML = "Copy link";
}
