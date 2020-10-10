document.addEventListener("DOMContentLoaded", ready);
function ready(){
    $.get("/checkAuthentication", function (data){
        if(data!=="There is no authentication"){
            let strings = data.split("=");
            let element = document.getElementById("hrefSignUp");
            if (strings[0] === "user") {
                let firstName = strings[1];
                let lastName = strings[2];
                element.innerHTML = firstName + " " + lastName;
                element.setAttribute("href", "/orders");
            }
            if (strings[0]=== "admin"){
                element.innerHTML = "Admin";
                element.setAttribute("href", "/admin");
            }
            let hrefLogIn = document.getElementById("hrefLogIn");
            hrefLogIn.innerHTML = "Exit";
            hrefLogIn.setAttribute("id", "exitButton");
            hrefLogIn.setAttribute("href", "/logout");

        }
    });


}








