document.getElementById("p_new_category").style.display = "none";

function categoryToggle() {
    var new_cate = document.getElementById("p_new_category");
    var old_cate = document.getElementById("p_category");
    if (new_cate.style.display === "none") {
        new_cate.style.display = "block";
        old_cate.style.display = "none";
    } else {
        old_cate.style.display = "block";
        new_cate.style.display = "none";
    }
}