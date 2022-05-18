




var sum=0;
function sum_a(o){
  console.log(sum);
  console.log(o.value);
  var s=o.value;
  var b = s.split("_");
  console.log(b[0]);
   var c = parseFloat(b[0]);
   console.log(c);
  sum+=parseFloat(o.checked? c:-c);//如果选中就加选中的那个复选框的值，否则就减去
  console.log(sum);
  document.getElementById('total_price').value=sum;
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

