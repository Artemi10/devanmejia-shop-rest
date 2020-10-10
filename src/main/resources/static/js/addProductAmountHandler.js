let addProductSubmitElement = document.getElementById("addProductSubmit");
addProductSubmitElement.onclick=()=>{
    let productName = document.getElementById("productName").value;
    let productAmount = document.getElementById("productAmount").value;
    if(productName ===null||productName ===""||productName ===" ")
        alert("Please, enter product name");
    else if(productAmount===null||productAmount===""||productAmount===" ")
        alert("Please, enter product amount");
    else if(isNaN(parseInt(productAmount)))
        alert("Please, enter correct amount");
    else{
       $.post("/addProductAmount", {productName: productName, productAmount: productAmount}, function (data){
           alert(data);
       });
    }

};