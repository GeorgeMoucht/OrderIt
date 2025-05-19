function openModal(contentId, titleText = "") {
    const template = document.getElementById(contentId);
    const clone = template.content.cloneNode(true);
    const modal = document.getElementById('modal');
    const modalBody = document.getElementById('modal-body');
    const modalTitle = document.getElementById('modal-title');

    modalTitle.textContent = titleText;
    modalBody.innerHTML = "";
    modalBody.appendChild(clone);

    modal.classList.remove("hidden");
}

function closeModal() {
    const modal = document.getElementById("modal");
    modal.classList.add("fade-out");
    setTimeout(() => {
        modal.classList.remove("fade-out");
        modal.classList.add("hidden");
    }, 200);
}

function getCSRFToken() {
    return document.querySelector('[name=csrfmiddlewaretoken]').value;
}

function showToast(message, type = "success") {
    const toastContainer = document.getElementById("toast-container");

    const toast = document.createElement("div");
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <span>${message}</span>
        <button class="close-toast" onclick="this.parentElement.remove()">×</button>
    `;

    toastContainer.appendChild(toast);

    // Auto-remove after 3s
    setTimeout(() => {
        toast.remove();
    }, 3000);
}


document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll('.dropdown-toggle').forEach(toggle => {
        toggle.addEventListener('click', () => {
            toggle.closest('.nav-dropdown').classList.toggle('open');
        });
    });
});