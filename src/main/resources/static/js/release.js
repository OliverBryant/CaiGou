



//新商品上传图片部分

let img1 = document.getElementById("img_1");
let input1 = document.getElementById("input01");
img1.onclick = function (){
  input1.click();
}
let img2 = document.getElementById("img_2");
let input2 = document.getElementById("input02");
img2.onclick = function (){
  input2.click();
}
let img3 = document.getElementById("img_3");
let input3 = document.getElementById("input03");
img3.onclick = function (){
  input3.click();
}
let img4 = document.getElementById("img_4");
let input4 = document.getElementById("input04");
img4.onclick = function (){
  input4.click();
}

var fileInput1 = document.querySelector('#input01'),
  previewImg1 = document.querySelector('#img_1');
fileInput1.addEventListener('change', function () {

  var file = this.files[0];
  var reader = new FileReader();
  // 监听reader对象的的onload事件，当图片加载完成时，把base64编码賦值给预览图片
  reader.addEventListener("load", function () {
    previewImg1.src = reader.result;
  }, false);
  // 调用reader.readAsDataURL()方法，把图片转成base64
  $("#img02").css("display","block");
  reader.readAsDataURL(file);
}, false);

var fileInput2 = document.querySelector('#input02'),
  previewImg2 = document.querySelector('#img_2');
fileInput2.addEventListener('change', function () {
  var file = this.files[0];
  var reader = new FileReader();
  // 监听reader对象的的onload事件，当图片加载完成时，把base64编码賦值给预览图片
  reader.addEventListener("load", function () {
    previewImg2.src = reader.result;
  }, false);
  // 调用reader.readAsDataURL()方法，把图片转成base64
  $("#img03").css("display","block");
  reader.readAsDataURL(file);
}, false);

var fileInput3 = document.querySelector('#input03'),
  previewImg3 = document.querySelector('#img_3');
fileInput3 .addEventListener('change', function () {
  var file = this.files[0];
  var reader = new FileReader();
  // 监听reader对象的的onload事件，当图片加载完成时，把base64编码賦值给预览图片
  reader.addEventListener("load", function () {
    previewImg3.src = reader.result;
  }, false);
  // 调用reader.readAsDataURL()方法，把图片转成base64
  $("#img04").css("display","block");
  reader.readAsDataURL(file);
}, false);

var fileInput4 = document.querySelector('#input04'),
  previewImg4 = document.querySelector('#img_4');
fileInput4.addEventListener('change', function () {
  var file = this.files[0];
  var reader = new FileReader();
  // 监听reader对象的的onload事件，当图片加载完成时，把base64编码賦值给预览图片
  reader.addEventListener("load", function () {
    previewImg4.src = reader.result;
  }, false);
  // 调用reader.readAsDataURL()方法，把图片转成base64
  reader.readAsDataURL(file);
}, false);



//
// //tijiao
// $('#btnSubmit').on('click', function(){
//   $.ajax({
//     url:"{:U('Sign/signup2')}",
//     type:"POST",
//     success:function(JSON){
// //                    debugger;
//     },
//     error:function(xhr,status,errorThrown){
// //                    debugger;
//     },
//     complete:function(xhr,status){
// //                    alert('The request is complated');
//     },
//     beforesend:function(){
//     },
//     cache:false,//true
//     async:true
//   });//ajax end here
// });//btnSubmit click end here





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

