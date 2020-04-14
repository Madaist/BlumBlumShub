document.getElementById("submit-btn").addEventListener("click", function(e){
    if(document.getElementById("example-number-input").value == ""){
        Swal.fire("Oops..", "Please insert a number", "error");
        e.preventDefault();
    }
});
