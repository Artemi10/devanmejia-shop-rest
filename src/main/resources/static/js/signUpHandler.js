let element = document.getElementById("buttonSignUp");
element.onclick=()=>{
    let firstName = document.getElementById("firstName").value;
    let lastName = document.getElementById("lastName").value;
    let login = document.getElementById("login").value;
    let email = document.getElementById("emailURl").value;
    let password = document.getElementById("password").value;
    let rePassword = document.getElementById("rePassword").value;
    let age = document.getElementById("age").value;
    let city = document.getElementById("city").value;

    if(firstName===null||firstName===""||firstName===" ")
        alert("Please, enter your firstname");

    else if(lastName===null||lastName===""||lastName===" ")
        alert("Please, enter your surname");

    else if(login===null||login===""||login===" ")
        alert("Please, enter your login");

    else if(email===null||email===""||email===" ")
        alert("Please, enter your email");

    else if(password===null||password===""||password===" ")
        alert("Please, enter your password");

    else if(rePassword===null||rePassword===""||rePassword===" ")
        alert("Please, confirm your password");

    else if(password!==rePassword)
        alert("Passwords are not the same")

    else if(age===null||isNaN(Number.parseInt(age)))
        alert("Please, enter your age correctly");


    else if(city===null||city===""||city===" ")
        alert("Please, enter city name");

    else
        element.setAttribute("type", "submit");

};