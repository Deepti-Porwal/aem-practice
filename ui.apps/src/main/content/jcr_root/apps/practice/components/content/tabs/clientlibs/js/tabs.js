document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".cmp-tabs").forEach((tabs) => {
        const tabButtons = tabs.querySelectorAll(".cmp-tabs__tab");
        const panels = tabs.querySelectorAll(".cmp-tabs__panel");
        tabButtons.forEach((button, index) => {
            button.addEventListener("click", () => {
                tabButtons.forEach(btn => btn.classList.remove("is-active"));
                panels.forEach(panel => panel.classList.remove("is-active"));

                button.classList.add("is-active");
                panels[index].classList.add("is-active");
            });
        });
    });
});