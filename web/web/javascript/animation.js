

var isScrolling = false;
 
    window.addEventListener("scroll", throttleScroll, false);
 
    function throttleScroll(e) {
      if (isScrolling == false) {
        window.requestAnimationFrame(function() {
          scrolling(e);
          isScrolling = false;
        });
      }
      isScrolling = true;
    }
 
    document.addEventListener("DOMContentLoaded", scrolling, false);
 
    var tooling = document.getElementsByClassName("tooling");
    var toolbox = document.getElementsByClassName("toolbox")[0];
    var sliderbox =  document.getElementsByClassName("sliderBox")[0];
    var slider = document.getElementById("slider");
    function scrolling(e) {
      for(var i = 0; i < tooling.length; i++){
          if (isPartiallyVisible(toolbox)) {
            tooling[i].classList.add("visible");
            tooling[i].classList.remove("hidden");
            sliderbox.style.display="none";
          } else {
            tooling[i].classList.add("hidden");
            tooling[i].classList.remove("visible");
            if(isPartiallyVisible(slider)) {
                  sliderbox.style.display="block";
            }else{
                sliderbox.style.display="none";
            }
         }
      }
      
    }
 
    function isPartiallyVisible(el) {
      var elementBoundary = el.getBoundingClientRect();
 
      var top = elementBoundary.top;
      var bottom = elementBoundary.bottom;
      var height = elementBoundary.height;
 
      return ((top + height >= 0) && (height + window.innerHeight >= bottom));
    }
 
    function isFullyVisible(el) {
      var elementBoundary = el.getBoundingClientRect();
 
      var top = elementBoundary.top;
      var bottom = elementBoundary.bottom;
 
      return ((top >= 0) && (bottom <= window.innerHeight));
    }