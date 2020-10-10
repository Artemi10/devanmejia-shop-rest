function placeOrder() {
    let elementOrder = document.getElementById("orderButton");
    let cartProducts = [];
    elementOrder.onclick = () => {
        let elementsOrder = document.getElementsByClassName("product");
        for (let i = 0; i < elementsOrder.length; i++) {
            let productName = elementsOrder[i].getElementsByClassName("productDescription")[0].innerHTML.split(" ")[0];
            let amount = elementsOrder[i].getElementsByClassName("number")[0].innerHTML;
            let price = elementsOrder[i].getElementsByClassName("productDescription")[0].innerHTML.split(" ")[2];
            let description = elementsOrder[i].getElementsByClassName("productDescription")[0].innerHTML.split(" ")[5];
            let product = {
                name: productName.toLowerCase(),
                price: price,
                description: description
            }
            cartProducts[i] = {
                amount: amount,
                product: product

            };
        }

        $.post('/placeOrder', {
            orderId: document.getElementById("orderId").innerHTML,
            products: JSON.stringify(cartProducts)

        }, function (data) {
            if(data==="success"){
                alert("You order added successfully");
                window.location.href = '../pages/orders.html';
            }else alert(data);
        });

    };
}

