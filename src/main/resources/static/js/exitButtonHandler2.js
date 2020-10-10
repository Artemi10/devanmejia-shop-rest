let element = document.getElementById("orderExitButton");
element.onclick=()=>{
    if(confirm("Do tou want to exit?")) {
        window.location.href = "/logout";
    }
};



