let deleteSubmitElement = document.getElementById("deleteSubmit");
deleteSubmitElement.onclick=()=>{
    let deleteProductName = document.getElementById("deleteProductName").value;
    if(deleteProductName===null||deleteProductName===""||deleteProductName===" ")
        alert("Please, enter admin login");
    else{
        $.post("/deleteProduct",{deleteProductName:deleteProductName}, function (data){
            alert(data);
        });
    }

};