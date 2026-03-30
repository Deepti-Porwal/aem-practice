document.addEventListener("DOMContentLoaded", function () {

    document.querySelectorAll(".cmp-carousel").forEach(carousel => {

        let slides = carousel.querySelectorAll(".cmp-carousel__slide");
        let next = carousel.querySelector(".cmp-carousel__next");
        let prev = carousel.querySelector(".cmp-carousel__prev");

        let index = 0;

        function showSlide(i) {
            slides.forEach(s => s.classList.remove("active"));
            slides[i].classList.add("active");
        }

        next.addEventListener("click", () => {
            index = (index + 1) % slides.length;
            showSlide(index);
        });

        prev.addEventListener("click", () => {
            index = (index - 1 + slides.length) % slides.length;
            showSlide(index);
        });

    });

});