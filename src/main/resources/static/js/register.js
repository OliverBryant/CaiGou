
let rePas=document.getElementById("rePas");
let pas=document.getElementById("pas");
let error=document.getElementById("error");

console.log("引入");
let a;
let b;
rePas.onblur=function (){
    a=pas.value;
    b=rePas.value;
    if(a!==""&&b!==""){
        if(b!==a){
            pas.style.borderColor="red";
            rePas.style.borderColor="red";
            error.style.display="block";
        }else{
            pas.style.borderColor="aliceblue";
            rePas.style.borderColor="aliceblue";
            error.style.display="none";
        }
    }else{
        pas.style.borderColor="aliceblue";
        rePas.style.borderColor="aliceblue";
        error.style.display="none";
    }
}
pas.onblur=function (){
    a=pas.value;
    b=rePas.value;
    if(a!==""&&b!==""){
        if(b!==a){
            pas.style.borderColor="red";
            rePas.style.borderColor="red";
            error.style.display="block";
        }else{
            pas.style.borderColor="aliceblue";
            rePas.style.borderColor="aliceblue";
            error.style.display="none";
        }
    }else{
        pas.style.borderColor="aliceblue";
        rePas.style.borderColor="aliceblue";
        error.style.display="none";
    }
}



//右上角头像框
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

