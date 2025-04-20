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
