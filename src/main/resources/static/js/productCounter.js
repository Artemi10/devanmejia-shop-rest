function productCounter(){
    count();
    function count(){
        let products = document.getElementsByClassName("product");
        let totalPrice = 0;
        for (let i = 0; i < products.length; i++) {
            let strings = products[i].getElementsByClassName("productDescription")[0].innerHTML.split(" ");
            let amount = products[i].getElementsByClassName("number")[0].innerHTML;
            if (isNaN(parseInt(strings[2])))
                totalPrice = totalPrice + (parseInt(strings[3]) * parseInt(amount));
            else
                totalPrice = totalPrice + (parseInt(strings[2]) * parseInt(amount));
        }
        let orderPrice = document.getElementById("orderPrice");
        orderPrice.innerHTML = "Total Price: " + totalPrice;
    }

    let elements = document.getElementsByClassName("amountSetter");
    for (let i = 0; i < elements.length; i++) {
        let minus = elements[i].getElementsByClassName("minus")[0];
        let plus = elements[i].getElementsByClassName("plus")[0];
        let number = elements[i].getElementsByClassName("number")[0];
        minus.onclick = () => {
            let amount = parseInt(number.innerHTML);
            if (amount - 1 <= 0)
                number.innerHTML = 0;
            else
                number.innerHTML = amount - 1;
            count();

        };
        plus.onclick = () => {
            let amount = parseInt(number.innerHTML);
            number.innerHTML = amount + 1;
            count();
        };
    }
}