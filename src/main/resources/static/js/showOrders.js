window.addEventListener("DOMContentLoaded", showOrders);
function showOrders(){
    $.get('/showOrders' ,function(data){
         showActiveOrders(data);
        let orderElements = document.getElementsByClassName("order");
        for(let i = 0; i<orderElements.length; i++){
            orderElements[i].addEventListener("click",openProductList );
        }
        function openProductList(event){
            let value = parseInt(event.currentTarget.getElementsByClassName("orderId2")[0].innerHTML);
            $.post('/products',{orderName: value} ,function(data){
                let currentOrder = JSON.parse(JSON.stringify(data))[0];
                let parentDiv = document.getElementById("ordersElement");
                parentDiv.innerHTML="";
                let products = currentOrder.cartProduct;
                let tittleProducts = document.getElementsByClassName("orderList")[0];
                tittleProducts.innerHTML = "List of your products"
                let order = document.getElementById("orderIdPlace");
                order.innerHTML='<div class ="row no-gutters">\n' +
                    '\t\t<div class="col-sm-12">\n' +
                    '\t\t\t<p class="orderId" id="orderId">OrderID:' + currentOrder.id + '</p>\n' +
                    '\t\t</div>\n' +
                    '\t</div>\n' +
                    '\t\n';
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
                        '\t\t\t\t\t<span class="minus">-</span>\n' +
                        '\t\t\t\t\t<span class="number">' + products[i].amount + '</span>\n' +
                        '\t\t\t\t\t<span class="plus">+</span>\n' +
                        '\t\t\t\t</div>\n' +
                        '\t\t\t</div>\n' +
                        '\t\t</div>\n' +
                        '\t</div>';
                }
                parentDiv = document.getElementById("ordersElement");
                parentDiv.innerHTML+='<div class="row">'+
                    '\t\t<div class="col-sm-4"></div>\n' +
                    '\t\t<div class="col-sm-4">\n' +
                    '\t\t\t<div type="button" id="showOrders" class="showOrdersButton">Show orders</div>\n' +
                    '\t\t</div>\n' +
                    '\t</div>\n';

                reloadPage();
                showPlaceOrderButton(currentOrder);
                $.getScript("../js/productCounter.js" , function(data){
                    productCounter();

                });
                $.getScript("../js/placeOrder.js" , function(data){
                    placeOrder();

                });
            });
        }
    });
}
function showPlaceOrderButton(currentOrder){
    let container = document.getElementById("buttonPlace");
    container.innerHTML+='<div class="row no-gutters orderSubmit">\n' +
        '\t\t<div class="col-sm-4"></div>\n' +
        '\t\t<div class="col-sm-4">\n' +
        '\t\t\t<form action="/placeOrder" method="POST">\n' +
        '\t\t\t    <input type="button" id="orderButton" value="Place the order" class="placeOrderButton">\n' +
        '\t\t\t</form>\n' +
        '\t\t</div>\n' +
        '\t\t<div class="col-sm-4">\n' +
        '\t\t\t<span class="orderPrice" id="orderPrice">Total Price: </span>\n' +
        '\t\t</div>\n' +
        '\t</div>\n';
    if(currentOrder.orderStatus==="IRRELEVANT"||currentOrder.orderStatus==="READY"){
        let button = document.getElementById("orderButton");
        button.style.display="none";
        let minuses = document.getElementsByClassName("minus");
        let pluses = document.getElementsByClassName("plus");
        for(let i=0; i<minuses.length; i++){
            minuses[i].style.display="none";
            pluses[i].style.display="none";
        }
    }
}
function showActiveOrders(data){
    let orders = JSON.parse(JSON.stringify(data));
    if(orders.length===0){
        let parentDiv = document.getElementById("ordersElement");
        parentDiv.innerHTML+='<div class ="row no-gutters">\n' +
            '\t<div class="col-sm-12">\n' +
            '\t\t<h3 class="productList">There is not available orders</h3>\n' +
            '\t</div>\n' +
            '</div>\n' +
            '<div class ="row no-gutters">\n' +
            '\t<div class="col-sm-12">\n' +
            '\t\t<img src="../images/shopping-cart.png">\n' +
            '\t</div>\n' +
            '</div>';
    }
    for(let i =0; i<orders.length; i++ ){
        let order = orders[i];
        let parentDiv = document.getElementById("ordersElement");
        parentDiv.innerHTML+='<div class ="row no-gutters order">\n' +
            '\t\t\n' +
            '\t\t<div class="col-sm-4">\n' +
            '\t\t\t<div class="orderElement">\n' +
            '\t\t\t\t<p class="orderId1">Order ID</p><br>\n' +
            '\t\t\t\t<p class="orderId2">'+order.id+'</p>\n' +
            '\t\t\t</div>\n' +
            '\t\t</div>\n' +
            '\t\t<div class="col-sm-4">\n' +
            '\t\t\t<div class="orderElement">\n' +
            '\t\t\t\t<p class="receiver1">Receiver</p><br>\n' +
            '\t\t\t\t<p class="receiver2">'+order.user.firstName+' '+order.user.lastName+'</p>\n' +
            '\t\t\t</div>\n' +
            '\t\t</div>\n' +
            '\t\t<div class="col-sm-4">\n' +
            '\t\t\t<div class="orderElement">\n' +
            '\t\t\t\t<p class="status1">Status</p><br>\n' +
            '\t\t\t\t<p class="status2">'+order.orderStatus+'</p>\n' +
            '\t\t\t</div>\n' +
            '\t\t</div>\n' +
            '\t\t\n' +
            '\t\n' +
            '\t</div>';


    }

}
function reloadPage(){
    let button = document.getElementById("showOrders");
    button.onclick=()=>{
        window.location.reload();
    }
}
