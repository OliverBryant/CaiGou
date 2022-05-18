let li = document.getElementsByClassName("shit");
let own = document.getElementById("own");//个人资料
let setPassword = document.getElementById("setPassword");//修改密码
let myRelease = document.getElementById("Release");//我的发布
let myBuy = document.getElementById("myBuy");//我买到的
let order = document.getElementById("order");//我买到的
li[0].onclick = function () {
    for (let i = 0; i < li.length; i++) {
        li[i].style.boxShadow = "none";
    }
    this.style.boxShadow = " 0 0 6px #afb0b2";
    own.style.display = "block";
    setPassword.style.display = "none";
    myRelease.style.display = "none";
    myBuy.style.display = "none";
    order.style.display = "none";

}
li[1].onclick = function () {
    for (let i = 0; i < li.length; i++) {
        li[i].style.boxShadow = "none";
    }
    this.style.boxShadow = " 0 0 6px #afb0b2";
    own.style.display = "none";
    setPassword.style.display = "block";
    myRelease.style.display = "none";
    myBuy.style.display = "none";
    order.style.display = "none";
}
li[2].onclick = function () {
    for (let i = 0; i < li.length; i++) {
        li[i].style.boxShadow = "none";
    }
    this.style.boxShadow = " 0 0 6px #afb0b2";
    own.style.display = "none";
    setPassword.style.display = "none";
    myRelease.style.display = "block";
    myBuy.style.display = "none";
    order.style.display = "none";
}
li[3].onclick = function () {
    for (let i = 0; i < li.length; i++) {
        li[i].style.boxShadow = "none";
    }
    this.style.boxShadow = " 0 0 6px #afb0b2";
    own.style.display = "none";
    setPassword.style.display = "none";
    myRelease.style.display = "none";
    myBuy.style.display = "block";
    order.style.display = "none";
}
li[4].onclick = function () {
    for (let i = 0; i < li.length; i++) {
        li[i].style.boxShadow = "none";
    }
    this.style.boxShadow = " 0 0 6px #afb0b2";
    order.style.display = "block";
    own.style.display = "none";
    setPassword.style.display = "none";
    myRelease.style.display = "none";
    myBuy.style.display = "none";
}


let iconfont = document.getElementsByClassName("iconfont");
let ChangeAddress = document.getElementById("ChangeAddress");
let save = document.getElementById("save");
let de = document.getElementsByClassName("defaul");
let check = document.getElementById("check");

let n = "";
for (let i = 0; i < iconfont.length; i++) {
    iconfont[i].onclick = function () {
        n = this.value;
        // addressManage.style.filter="blur(2px)";
        ChangeAddress.style.display = "block";
        check.onchange = function () {
            if (check.checked) {
                de[n - 1].style.display = "inline-block";
            } else {
                de[n - 1].style.display = "none";
            }
        }
    }
}
/*save.onclick=function (){
    ChangeAddress.style.display="none";
    $.ajax({
        type:"post",
        dataType:"json",
        url:"/caigou/cart/test",
        data:$(this.form).serialize(),
        success:function (result){
            console.log(result);
            console.log(result.userName);
        },
        error: function (){
            console.log("异常");
        }
    });
}*/

let lable = document.getElementsByClassName("Lable");
let name = document.getElementsByClassName("name");
let str
for (let i = 0; i < lable.length; i++) {
    str = name[i].textContent;
    lable[i].innerText = str[0];
}


let context = document.getElementsByClassName("context");
let xiugai = document.getElementById("xiugai");
let baocun = document.getElementById("baocun");
xiugai.onclick = function () {
    for (let i = 0; i < context.length; i++) {
        context[i].disabled = "";
    }
    context[0].focus();
}
baocun.onclick = function () {
    $.ajax({
        type: "post",
        dataType: "json",
        url: "/caigou/cart/test",
        data: $(this.form).serialize(),
        success: function (result) {
            this.value = result.data;
        },
        error: function () {
            console.log("异常");
        }
    });
    for (let i = 0; i < context.length; i++) {
        context[i].disabled = "disabled";
    }
}
/*context[0].onblur=function (){
    this.disabled="disabled";
}
context[1].onblur=function (){
    this.disabled="disabled";
}
context[2].onblur=function (){
    this.disabled="disabled";
}
context[3].onblur=function (){
    this.disabled="disabled";
}

context[0].onchange=function (){
    console.log("1");
}*/


//上传头像部分
let img = document.getElementById("img");
let input = document.getElementById("input");
img.onclick = function () {
    input.click();
}

function update() {
    $.ajax({
        type: "post",
        dataType: "json",
        url: "/caigou/user/reversePassword",
        data: $(this.form).serialize(),
        success: function (result) {
            console.log(result);
            console.log(result.userName);
        },
        error: function () {
            console.log("异常");
        }
    });
}

/*------------密码一致判断-------------*/
let pas = document.getElementById("pas");
let rePas = document.getElementById("rePas");
let er = document.getElementById("error");
rePas.onblur = function () {
    console.log(pas.value);
    console.log(rePas.value);
    if (pas.value != rePas.value) {
        rePas.classList.add("error");
        er.style.display = "inline";
    } else {
        rePas.classList.remove("error");
        er.style.display = "none";
    }
}
/*-----------------提交密码-------------------*/
let setbtn = document.getElementById("setBtn");
setbtn.onclick = () => {
    if (pas.value === rePas.value) {
        $.ajax({
            type: "post",
            dataType: "json",
            url: "/caigou/user/reversePassword",
            data: $(this.form).serialize(),
            success: function (result) {
                console.log(result);
            },
            error: function () {
                console.log("异常");
            }
        });
        console.log($(this.form).serialize())
    }
}

const verBtn = document.querySelector('.verBut')
const error  = document.querySelector('.error')
const ver  = document.querySelector('.ver')
let verNum
verBtn.onclick=()=>{
    console.log("获取验证码")
    $.ajax({
        type: "get",
        dataType: "json",
        url: "/caigou/user/code",
        data: {},
        success: function (result) {
            console.log(result);
            verNum=result
        },
        error: function () {
            console.log("异常");
        }
    })
}

let verInput=undefined
ver.onchange=()=>{
    verInput=ver.value;
    if(verInput!=verNum){
        alert("验证码错误")
    }
}
/*----------------------------评价----------------------------------*/
let label=document.getElementsByTagName("label");
let header=document.getElementById("head");
label[4].onclick=function (){
    header.innerText="I just hate it";
}
label[3].onclick=function(){
    header.innerText="I don't like it";
}
label[2].onclick=function(){
    header.innerText="It is awesome";
}
label[1].onclick=function(){
    header.innerText="I just like it";
}
label[0].onclick=function(){
    header.innerText="I just love it";
}
let off=document.getElementsByClassName("off");
let evaluation=document.getElementById("evaluation1");
let evabtn=document.getElementById("evabtn");
for(let i=0;i<off.length;i++){
    off[i].onclick=function (){
        evaluation.style.display="grid";
        label[2].classList.add("white");
        label[1].classList.add("white");
        label[0].classList.add("white");

    }
}

evabtn.onclick=()=>{
    $.ajax({
        type: "post",
        dataType: "json",
        url: "/caigou/user/star",
        data: "rate="+$('input[type=radio][name=rate]:checked').val(),
        success: function (result) {
            console.log(result);
        },
        error: function () {
            console.log("异常");
        }
    });
    console.log($('input[type=radio][name=rate]:checked').val());
    evaluation.style.display="none";
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

// 我的订单 状态映射
const statu = document.querySelectorAll('.status')
const title = document.getElementsByClassName('statu')
const status = new Map([
    ["0","订单异常"],
    ["1","订单取消"],
    ["2","交易成功"]
])
let num
for(let i=0;i<statu.length;i++){
    num=statu[i].innerText
    if(status.has(num)){
        title[i].innerHTML=status.get(num);
    }
}



