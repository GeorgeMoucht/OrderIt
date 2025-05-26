document.addEventListener("DOMContentLoaded", () => {
    // --- ADD ITEM ---
    const addBtn = document.getElementById("add-item-btn");
    if (addBtn) {
        addBtn.addEventListener("click", () => {
            openModal("add-item-template", "Add Menu Item");

            requestAnimationFrame(() => {
                const form = document.getElementById("add-item-form");
                const categorySelect = form.querySelector("select[name='category']");

                // Clear & fetch categories
                categorySelect.innerHTML = '<option value="">-- Select Category --</option>';
                fetch("/web/menu-categories/json/")
                    .then(res => res.json())
                    .then(categories => {
                        categories.forEach(cat => {
                            const option = document.createElement("option");
                            option.value = cat.id;
                            option.textContent = cat.name;
                            categorySelect.appendChild(option);
                        });
                    });

                form.addEventListener("submit", function (e) {
                    e.preventDefault();
                    const formData = new FormData(form);

                    fetch("/web/menu-items/create/", {
                        method: "POST",
                        headers: {
                            "X-CSRFToken": getCSRFToken(),
                        },
                        body: formData
                    })
                    .then(res => res.json())
                    .then(data => {
                        if (data.success) {
                            showToast("Menu item created!", "success");
                            closeModal();
                            setTimeout(() => location.reload(), 1000);
                        } else {
                            showToast(data.message || "Error", "error");
                        }
                    })
                    .catch(() => showToast("Unexpected error", "error"));
                });
            });
        });
    }

    // --- DELETE ITEM ---
    document.querySelectorAll(".delete-item-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;
            openModal("delete-item-template", "Delete Menu Item");

            requestAnimationFrame(() => {
                document.getElementById("confirm-delete-item").onclick = () => {
                    fetch("/web/menu-items/delete/", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "X-CSRFToken": getCSRFToken(),
                        },
                        body: JSON.stringify({ id })
                    })
                    .then(res => res.json())
                    .then(data => {
                        if (data.success) {
                            showToast("Deleted successfully", "success");
                            closeModal();
                            setTimeout(() => location.reload(), 1000);
                        } else {
                            showToast(data.message || "Delete failed", "error");
                        }
                    });
                };
            });
        });
    });

    // --- EDIT ITEM ---
    document.querySelectorAll(".edit-item-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;

            fetch(`/web/menu-items/${id}/json/`)
                .then(res => res.json())
                .then(item => {
                    openModal("edit-item-template", "Edit Menu Item");

                    requestAnimationFrame(() => {
                        const form = document.getElementById("edit-item-form");
                        const categorySelect = form.querySelector("select[name='category']");

                        form.querySelector("input[name='item_id']").value = item.id;
                        form.querySelector("input[name='name']").value = item.name;
                        form.querySelector("textarea[name='description']").value = item.description || "";
                        form.querySelector("input[name='price']").value = item.price;

                        // Clear and repopulate categories
                        categorySelect.innerHTML = '<option value="">-- Select Category --</option>';
                        fetch("/web/menu-categories/json/")
                            .then(res => res.json())
                            .then(categories => {
                                categories.forEach(cat => {
                                    const option = document.createElement("option");
                                    option.value = cat.id;
                                    option.textContent = cat.name;
                                    if (item.category?.id === cat.id) {
                                        option.selected = true;
                                    }
                                    categorySelect.appendChild(option);
                                });
                            });

                        form.addEventListener("submit", function (e) {
                            e.preventDefault();

                            const formData = new FormData(form);
                            formData.append("id", item.id);

                            fetch("/web/menu-items/update/", {
                                method: "POST",
                                headers: {
                                    "X-CSRFToken": getCSRFToken(),
                                },
                                body: formData,
                            })
                            .then(res => res.json())
                            .then(data => {
                                if (data.success) {
                                    showToast("Item updated!", "success");
                                    closeModal();
                                    setTimeout(() => location.reload(), 1000);
                                } else {
                                    showToast(data.message || "Update failed", "error");
                                }
                            })
                            .catch(() => showToast("Unexpected error", "error"));
                        });
                    });
                });
        });
    });

    // --- DEBOUNCED SEARCH ---
    const searchInput = document.querySelector("#item-search-form input[name='search']");
    let debounceTimeout;

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
