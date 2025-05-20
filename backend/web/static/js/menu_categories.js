document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("add-category-btn").addEventListener("click", () => {
        const template = document.getElementById("add-category-template");
        // showModal(template.innerHTML);
        openModal("add-category-template", "Add Category");

    });

    document.querySelectorAll(".delete-category-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;
            const name = btn.dataset.name;

            openModal("delete-category-template", "Delete Category");

            requestAnimationFrame(() => {
                document.getElementById("delete-category-name").innerText = name;

                const oldBtn = document.getElementById("confirm-delete-category");
                const newBtn = oldBtn.cloneNode(true);
                oldBtn.parentNode.replaceChild(newBtn, oldBtn);

                newBtn.addEventListener("click", () => {
                    newBtn.disabled = true;
                    newBtn.innerText = "Deleting...";

                    fetch("/web/menu-categories/delete/", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "X-CSRFToken": getCSRFToken()
                        },
                        body: JSON.stringify({ id })
                    })
                    .then(res => res.json())
                    .then(data => {
                        if (data.success) {
                            showToast("Category deleted successfully", "success");
                            closeModal();
                            setTimeout(() => location.reload(), 1000);
                        } else {
                            showToast(data.message || "Failed to delete category", "error");
                            newBtn.disabled = false;
                            newBtn.innerText = "Yes, delete";
                        }
                    })
                    .catch(error => {
                        console.error("Delete error:", error);
                        showToast("Unexpected error", "error");
                        newBtn.disabled = false;
                        newBtn.innerText = "Yes, delete";
                    });
                });
            });
        });
    });


    document.querySelectorAll(".edit-category-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;
            const name = btn.dataset.name;
            const parentId = btn.dataset.parent || "";

            openModal("edit-category-template", "Edit Category");

            requestAnimationFrame(() => {
                const form = document.getElementById("edit-category-form");
                form.id.value = id;
                form.name.value = name;
                form.parent.value = parentId;

                form.addEventListener("submit", function (e) {
                    e.preventDefault();

                    const data = {
                        id: form.id.value,
                        name: form.name.value.trim(),
                        parent: form.parent.value || null,
                    };

                    fetch("/web/menu-categories/update/", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "X-CSRFToken": getCSRFToken(),
                        },
                        body: JSON.stringify(data),
                    })
                    .then(res => res.json())
                    .then(result => {
                        if (result.success) {
                            showToast("Category updated successfully", "success");
                            closeModal();
                            setTimeout(() => location.reload(), 1000);
                        } else {
                            showToast("Error: " + result.message, "error");
                        }
                    })
                    .catch(error => {
                        console.error("Update error:", error);
                        showToast("Unexpected error", "error");
                    });
                });
            });
        });
    });

    document.body.addEventListener("submit", e => {
        if (e.target.id === "add-category-form") {
            e.preventDefault();

            const form = e.target;
            const name = form.name.value.trim();
            const parent = form.parent.value || null;

            if (!name) {
                showToast("Category name is required.", "error");
                return;
            }

            fetch("/web/menu-categories/create/", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "X-CSRFToken": getCSRFToken()
                },
                body: JSON.stringify({ name, parent })
            })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    showToast("Category created successfully", "success");
                    closeModal();
                    setTimeout(() => location.reload(), 1000);
                } else {
                    showToast(data.message || "Failed to create category", "error");
                }
            })
            .catch(err => {
                console.error("Create error:", err);
                showToast("Unexpected error", "error");
            });
        }
    });

    const searchInput = document.querySelector("#category-search-form input[name='search']");
    let debounceTimeout;

    // Restore focus only if there's already a search value
    if (searchInput && searchInput.value.trim() !== "") {
        searchInput.focus();
        searchInput.setSelectionRange(searchInput.value.length, searchInput.value.length);
    }

    if (searchInput) {
        searchInput.addEventListener("input", () => {
            clearTimeout(debounceTimeout);
            debounceTimeout = setTimeout(() => {
                searchInput.form.submit();
            }, 500);
        });
    }
});

function getCSRFToken() {
    return document.querySelector('[name=csrfmiddlewaretoken]').value;
}
