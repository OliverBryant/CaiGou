
var checkAllEle = document.getElementById("checkAll");

function checkAll() {
    console.log(111)
    //1.获取编号前面的复选框

    //2.对编号前面复选框的状态进行判断
    console.log(checkAllEle.checked)
    if (checkAllEle.checked == true) {
        //3.获取下面所有的复选框
        var checkOnes = document.getElementsByName("comId");
        //4.对获取的所有复选框进行遍历
        for (var i = 0; i < checkOnes.length; i++) {
            //5.拿到每一个复选框，并将其状态置为选中
            checkOnes[i].checked = true;
        }
    } else {
        //6.获取下面所有的复选框
        var checkOnes = document.getElementsByName("comId");
        //7.对获取的所有复选框进行遍历
        for (var i = 0; i < checkOnes.length; i++) {
            //8.拿到每一个复选框，并将其状态置为未选中
            checkOnes[i].checked = false;
        }
    }
}
