document.addEventListener("DOMContentLoaded", function () {

    let input = document.getElementById("searchInput");

    input.addEventListener("keyup", function () {

        let keyword = this.value;

        fetch("/bin/practice/search?keyword=" + keyword)
        .then(res => res.json())
        .then(data => {

        let results = document.getElementById("searchResults");
        results.innerHTML = "";

        data.forEach(item => {

            results.innerHTML += `
            <div>
            <a href="${item.path}.html">${item.title}</a>
            </div>
            `;

        });

        });

    });

});