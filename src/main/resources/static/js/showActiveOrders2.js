let activeOrdersButton = document.getElementById("showActiveOrdersButton");
activeOrdersButton.onclick=()=>{
    $.get("/showActiveOrders",function (data){
        if(data==="There is no active orders at the moment") {
            alert(data);
        }else{
            let actions = document.getElementById("divActions");
            actions.innerHTML="";
            actions.setAttribute("id","ordersElement" );
            actions.setAttribute("class","" );
            document.getElementsByClassName("adminPanelTittle")[0].innerHTML = "Active orders";
            $.getScript("../js/showOrders.js" , function(){
                showActiveOrders(data);
                let orderElements = document.getElementsByClassName("order");
                for(let i = 0; i<orderElements.length; i++){
                    orderElements[i].addEventListener("click",openActiveProduct );
                }
                function openActiveProduct(event){
                    let value = parseInt(event.currentTarget.getElementsByClassName("orderId2")[0].innerHTML);
                    let orders = JSON.parse(JSON.stringify(data));
                    let currentOrder;
                    for(let i=0; i<orders.length; i++){
                        if(orders[i].id===value){
                            currentOrder = orders[i];
                        }
                    }
                    let parentDiv = document.getElementById("ordersElement");
                    parentDiv.innerHTML="";
                    let order = document.getElementById("orderIdPlace");
                    order.innerHTML='<div class ="row no-gutters">\n' +
                        '\t\t<div class="col-sm-12">\n' +
                        '\t\t\t<p class="orderId" id="orderId">OrderID:' + currentOrder.id + '</p>\n' +
                        '\t\t</div>\n' +
                        '\t</div>\n' +
                        '\t\n';
                    let products = currentOrder.cartProduct;
                    for(let i = 0; i<products.length; i++) {
                        parentDiv.innerHTML += '\t<div class ="row no-gutters product">\n' +
                            '\t\t<div class="col-sm-4">\n' +
                            '\t\t\t<div class="productElement">\n' +
                            '\t\t\t\t<img src="' + products[i].product.picture.url + '">\n' +
                            '\t\t\t</div>\n' +
                            '\t\t</div>\n' +
                            '\t\t<div class="col-sm-4">\n' +
                            '\t\t\t<div class="productElement">\n' +
                            '\t\t\t\t<p class="productDescription">' + products[i].product.name + ' - ' + products[i].product.price + ' for ' + products[i].product.description + '</p>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t</div>\n' +
                            '\t\t<div class="col-sm-4">\n' +
                            '\t\t\t<div class="productElement">\n' +
                            '\t\t\t\t<div class="amountSetter">\n' +
                            '\t\t\t\t\t<p class="amount">Amount</p>\n' +
                            '\t\t\t\t\t<span class="number">' + products[i].amount + '</span>\n' +
                            '\t\t\t\t</div>\n' +
                            '\t\t\t</div>\n' +
                            '\t\t</div>\n' +
                            '\t</div>';


                    }
                    let orderButtonsPlace = document.getElementById("buttons");
                    orderButtonsPlace.innerHTML+="<div class=\"col-sm-4\">\n" +
                        "            <a id=\"readyOrderButton\" class=\"readyOrderButton\">Order is ready</a>\n";
                    $.getScript("../js/readyOrder1.js", function (){
                        readyOrder1();
                    });


                }

            });
            let button = document.getElementById("showActiveOrdersButton");
            button.setAttribute("href", "/admin")
            button.innerHTML="Admin panel";




        }
    });
};