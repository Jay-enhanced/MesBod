
function doAjax(url, id) {
    if(!confirm("确定操作？")) {
        return;
    }
    $.ajax({
        url: url,
        data: {
            id: id
        },
        type: "POST",
        success: function (re) {
            alert(re);
            location.href = "login.html";
        },
        error: function (re) {
            alert("服务器错误： " + re.status);
        }
    });
}