window.addEventListener("DOMContentLoaded", show);
function show(){
    $.get('/show', function(data){
       let stockProducts = JSON.parse(JSON.stringify(data));
       if(stockProducts.length===0){
           let divProducts = document.getElementById("divProducts");
           divProducts.innerHTML+='<div class ="row no-gutters">\n' +
               '\t<div class="col-sm-12">\n' +
               '\t\t<h3 class="productList">There is not available products </h3>\n' +
               '\t</div>\n' +
               '</div>\n' +
               '<div class ="row no-gutters">\n' +
               '\t<div class="col-sm-12">\n' +
               '\t\t<img src="../images/shopping-cart.png">\n' +
               '\t</div>\n' +
               '</div>';
       }
       for(let i=0; i<stockProducts.length; i++){
           let product = stockProducts[i];
           let divProducts = document.getElementById("divProducts");
           divProducts.innerHTML+='<div class="col-lg-3 col-sm-4">\n' +
               '\t\t\t\t\t<div class="product">\n' +
               '\t\t\t\t\t<img class = "productPhoto" src="'+product.product.picture.url+'">\n' +
               '\t\t\t\t\t\t<p class="header">'+product.product.price+' RUB</p>\n' +
               '\t\t\t\t\t\t<p class="header">'+product.productName.toUpperCase()+' - '+product.product.description+'</p>\n' +
               '\t\t\t\t\t\t<input type="submit" name="" value="Add to Shopping Cart" class="buttonCartHid">\n' +
               '\t\t\t\t\t\t\n' +
               '\t\t\t\t</div>\n' +
               '\t\t\t\t</div>';
           //product hover animation
           let elements= document.getElementsByClassName("product");
           for(let i =0 ; i<elements.length; i++){
               elements[i].addEventListener("mouseover", showButton);
               elements[i].addEventListener("mouseout", hideButton);
           }

           function showButton(element){
               element.currentTarget.getElementsByClassName("buttonCartHid")[0].className="buttonCartShow";
           }

           function hideButton(element){
               element.currentTarget.getElementsByClassName("buttonCartShow")[0].className="buttonCartHid";
           }
           //add product to shopping cart
           let productElements = document.getElementsByClassName("buttonCartHid");
           for(let i =0 ; i<productElements.length; i++){
               productElements.item(i).addEventListener("click", clickButton);
           }
           function clickButton(event){
               let value= event.currentTarget.parentNode.getElementsByClassName("header")
                   .item(1).innerHTML.split(" - ")[0];
               $.post('/add', {productName: value}, function(data){
                   if(data==="Please, sign up"){
                       alert(data);
                       window.location.href="../pages/signUp";
                   }
                   else if(data.indexOf("does not exist")!==-1||data==="There are not enough products in stock"){
                       alert(data);
                   }
                   else{
                       let flag = confirm(data);
                       if(flag)
                           window.location.href="../pages/orders.html";
                   }
               });

           }

       }
    });
}