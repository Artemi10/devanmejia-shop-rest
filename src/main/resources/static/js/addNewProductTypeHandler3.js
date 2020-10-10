let addProductTypeSubmitElement = document.getElementById("addProductTypeSubmit");
let pictureUrl = document.getElementById("pictureSelector");
let cropperElement;
document.getElementById("pictureSelector").addEventListener("change", function() {
    if (this.files && this.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById("imageSource").setAttribute("src", e.target.result);pictures
            let cropper = new Cropper(document.getElementById("imageSource"), {
                guides: false,
                zoomable: false,
                viewMode: 2,
                aspectRatio: 768 / 514,
                minCropBoxWidth: 768,
                minCropBoxHeight: 514,
                responsive: true
            });
            cropperElement=cropper;
        };
        reader.readAsDataURL(this.files[0]);
    }

});

addProductTypeSubmitElement.onclick=()=>{
    let productTypeName = document.getElementById("productTypeName").value;
    let productPrice = document.getElementById("productPrice").value;
    let productDescription = document.getElementById("productDescription").value;
    if(productTypeName ===null||productTypeName ===""||productTypeName ===" ")
        alert("Please, enter product name");
    else if(productPrice===null||productPrice===""||productPrice===" ")
        alert("Please, enter product price");
    else if(isNaN(parseInt(productPrice)))
        alert("Please, enter correct product price");
    else if(productDescription===null||productDescription===""||productDescription===" ")
        alert("Please, enter product description");
    else if(pictureUrl.value==null||pictureUrl.value===""||pictureUrl.value===" ")
        alert("Please, select picture");
    else{
        $.post("/addNewProductType",{productName:productTypeName, productPrice: productPrice, productDescription: productDescription, productPictureUrl: pictureUrl.value}, function (data){
            alert(data);
        });
        let stringPicture =  cropperElement.getCroppedCanvas({width: 768, height: 514}).toBlob(function (){
            $.post("/imageDownload",{productPicture: stringPicture}, function (blob){
                alert(blob);
            });
        });


    }

};