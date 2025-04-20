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
    let toast = document.getElementById("toast");
    if (!toast) {
        toast = document.createElement("div");
        toast.id = "toast";
        document.body.appendChild(toast);
    }

    toast.textContent = message;
    toast.className = `toast ${type}`;
    toast.style.display = "block";

    setTimeout(() => {
        toast.style.display = "none";
    }, 3000);
}

function handleEditFormSubmit(form, userId) {
    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const firstName = form.first_name.value.trim();
        const lastName = form.last_name.value.trim();
        const email = form.email.value.trim();

        // --- Validation ---
        if (!firstName) {
            showToast("First name is required.", "error");
            return;
        }

        if (!lastName) {
            showToast("Last name is required.", "error");
            return;
        }

        if (!email || !email.includes("@")) {
            showToast("Please enter a valid email address.", "error");
            return;
        }
        
        const data = {
            id: userId,
            first_name: firstName,
            last_name: lastName,
            email: email
        };

        fetch(`/web/users/update/`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-CSRFToken": getCSRFToken(),
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                showToast("User updated successfully", "success");
                closeModal();
                setTimeout(() => location.reload(), 1500);
            } else {
                showToast("Update failed: " + result.message, "error");
            }
        })
        .catch(error => {
            console.error("Update error:", error);
            showToast("Unexpected error", "error");
        });
    });
}

document.addEventListener("DOMContentLoaded", function () {
    // Edit button
    document.querySelectorAll(".edit-btn").forEach((btn) => {
        btn.addEventListener("click", () => {
            const userId = btn.getAttribute("data-userid");
            const firstName = btn.getAttribute("data-firstname") || "";
            const lastName = btn.getAttribute("data-lastname") || "";
            const email = btn.getAttribute("data-email") || "";

            openModal("edit-user-template", "Edit User");

            requestAnimationFrame(() => {
                const form = document.querySelector("#edit-user-form");
                form.querySelector("input[name='first_name']").value = firstName;
                form.querySelector("input[name='last_name']").value = lastName;
                form.querySelector("input[name='email']").value = email;

                handleEditFormSubmit(form, userId);
            });
        });
    });

    // Double click row
    document.querySelectorAll(".user-row").forEach((row) => {
        row.addEventListener("dblclick", () => {
            if (row.dataset.superuser === "true") return;
            const userId = row.getAttribute("data-userid");
            const firstName = row.getAttribute("data-firstname") || "";
            const lastName = row.getAttribute("data-lastname") || "";
            const email = row.getAttribute("data-email") || "";

            openModal("edit-user-template", "Edit User");

            requestAnimationFrame(() => {
                const form = document.querySelector("#edit-user-form");
                form.querySelector("input[name='first_name']").value = firstName;
                form.querySelector("input[name='last_name']").value = lastName;
                form.querySelector("input[name='email']").value = email;

                handleEditFormSubmit(form, userId);
            });
        });
    });

    // Delete button (future logic placeholder)
    document.querySelectorAll(".delete-btn").forEach((btn) =>
        btn.addEventListener("click", () => {
            const userId = btn.getAttribute("data-userid");
            const username = btn.getAttribute("data-username");
    
            openModal("delete-user-template", "Delete User");
    
            requestAnimationFrame(() => {
                document.getElementById("delete-user-name").textContent = username;
    
                const confirmBtn = document.getElementById("confirm-delete");
                confirmBtn.onclick = () => {
                    fetch(`/web/users/delete/`, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "X-CSRFToken": getCSRFToken(),
                        },
                        body: JSON.stringify({ id: userId }),
                    })
                    .then(res => res.json())
                    .then(result => {
                        if (result.success) {
                            showToast("User deleted successfully", "success");
                            closeModal();
                            setTimeout(() => location.reload(), 1200);
                        } else {
                            showToast("Error: " + result.message, "error");
                        }
                    })
                    .catch(error => {
                        console.error("Delete error:", error);
                        showToast("An error occurred.", "error");
                    });
                };
            });
        })
    );
    

    // Click outside modal to close
    document.getElementById("modal").addEventListener("click", function (event) {
        if (event.target === this) {
            closeModal();
        }
    });
});
