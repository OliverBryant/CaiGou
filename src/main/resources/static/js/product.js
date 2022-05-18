
var btn = document.getElementById("sub");
var bounced = document.getElementById("bounced");
btn.onclick=function (){
  bounced.style.display="block";
  setTimeout(()=>{
    bounced.style.display="none";
  },2000)
}

var btn1 = document.getElementById("sub1");
var bounced1 = document.getElementById("bounced1");
btn1.onclick=function (){

  console.log(11);
  var word = document.getElementById("word");
  word.value = document.location.href; // 获取当前网站链接
  word.select(); //选中文本
  document.execCommand("copy");
  console.log(word.value);
  bounced1.style.display="block";
  setTimeout(()=>{
    bounced1.style.display="none";
  },2000)
}

$("#leave_word_title").click(function(){
  $("#leave_word").css("display","block");
  $("#comments").css("display","none");
  $("#leave_word_title").css("border-top"," 5px solid #2b57a2");
  $("#leave_word_title").css("color"," #2b57a2");
  $("#comments_title").css("border-top"," 5px solid transparent");
  $("#comments_title").css("color"," #7187b0");
})

$("#comments_title").click(function(){
  $("#comments").css("display","block");
  $("#leave_word").css("display","none");
  $("#leave_word_title").css("border-top"," 5px solid transparent");
  $("#leave_word_title").css("color"," #7187b0");
  $("#comments_title").css("border-top"," 5px solid #2b57a2");
  $("#comments_title").css("color"," #2b57a2");
})

function getPath(o){
  var url = o.src;
  var img = document.getElementById("img");
  img.src=url;
  console.log(url);
}

