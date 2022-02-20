/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//start showing from the fifth review since the first four is already showing
var count = 3;

function hideAllReviews(){
    var reviews = document.getElementsByClassName("reviews");
    for(var i =0; i < reviews.length; i++){
        reviews[i].style.display = "none";
    }
    
}

document.getElementById("prev").onclick = function(){
    //show the prev four
    hideAllReviews();
    var total = document.getElementsByClassName("reviews");
    for(var i = 1; i <= 4; i++){
        if(count <= 0){
            count = total.length-1;
        }else{
            count--;
        }
        total[count].style.display = "block";
    }
};

document.getElementById("next").onclick = function(){
    //show the next four
    hideAllReviews();
    var total = document.getElementsByClassName("reviews");
    for(var i = 1; i <= 4; i++){
        if(count >= total.length-1){
            count = 0;
        }else{
            count++;
        }
        total[count].style.display = "block";
    }
};

