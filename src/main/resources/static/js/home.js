


onload=function(){
  var arr = document.getElementsByClassName("slide");
  for(var i=0;i<arr.length;i++){
    arr[i].style.left = i*100+"%";
  }
}
function LeftMove(){
  var arr = document.getElementsByClassName("slide");//获取三个子div
  for(var i=0;i<arr.length;i++){
    var left = parseFloat(arr[i].style.left);
    left-=2;
    var width = 100;//图片的宽度
    if(left<=-width){
      left=(arr.length-1)*width;//当图片完全走出显示框，拼接到末尾
      clearInterval(moveId);
    }
    arr[i].style.left = left+"%";
  }
}
function divInterval(){
  moveId=setInterval(LeftMove,10);//设置一个10毫秒定时器
}


timeId=setInterval(divInterval,4000);//设置一个6秒的定时器。

function stop(){
  clearInterval(timeId);
}
function start(){
  clearInterval(timeId);
  timeId=setInterval(divInterval,4000);
}

//页面失去焦点停止
onblur = function(){
  stop();
}
//页面获取焦点时开始
onfocus = function(){
  start();
}


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

