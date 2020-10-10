let element = document.getElementById("buttonLogIn");
element.onclick=()=>{
  let login = document.getElementById("login").value;
  let password = document.getElementById("password").value;
    if(login===null||login===""||login===" ")
        alert("Please, enter your login");
    else if(password===null||password===""||password===" ")
        alert("Please, enter your password");
    else
        element.setAttribute("type", "submit");
};