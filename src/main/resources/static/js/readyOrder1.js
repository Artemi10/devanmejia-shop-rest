function readyOrder1(){
    let readyOrderButton = document.getElementById("readyOrderButton");
    readyOrderButton.onclick=()=>{
        $.post("/readyOrder", {orderId: document.getElementById("orderId").innerHTML.split(":")[1]}, function (data){
            alert(data);
            window.location.href = "../admin";
        });
    }
}