document.getElementById("head_name").onmouseover = function(){
    var divFloat = document.getElementById("head_list")
    divFloat.style.display="block";
    this.onmouseout = function (){
        divFloat.style.display="none";
    }
}
document.getElementById("head_list").onmouseover = function(){
    var divFloat = document.getElementById("head_list")
    divFloat.style.display="block";
    this.onmouseout = function (){
        divFloat.style.display="none";
    }
}


