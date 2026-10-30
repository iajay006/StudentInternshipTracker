// Student Internship Tracker - Common JavaScript

document.addEventListener("DOMContentLoaded", function () {

    // Confirm before deleting a record
    document.querySelectorAll(".delete-btn").forEach(function (button) {
        button.addEventListener("click", function (event) {
            const confirmed = confirm(
                "Are you sure you want to delete this internship?"
            );

            if (!confirmed) {
                event.preventDefault();
            }
        });
    });

    // Show/hide password
    document.querySelectorAll(".toggle-password").forEach(function (button) {
        button.addEventListener("click", function () {
            const input = document.getElementById(
                button.dataset.target
            );

            if (!input) return;

            if (input.type === "password") {
                input.type = "text";
                button.textContent = "Hide";
            } else {
                input.type = "password";
                button.textContent = "Show";
            }
        });
    });

    // Display a confirmation message when a form is submitted
    document.querySelectorAll("form[data-confirm]").forEach(function (form) {
        form.addEventListener("submit", function (event) {
            if (!confirm(form.dataset.confirm)) {
                event.preventDefault();
            }
        });
    });

});
