from django.urls import path
from . import views

urlpatterns = [
    path('login/', views.login_view, name='login'),
    path('dashboard/', views.dashboard, name='dashboard'),
    path("users/", views.manage_users, name="users"),
    path("tables/", views.manage_tables, name="tables"),

    path("logout/", views.logout_view, name="logout"),

    path("users/update/", views.update_user_view, name="update_user"),
    path("users/delete/", views.delete_user_view, name="delete_user"),
    path("users/create/", views.create_user_view, name="create_user"),

    path("tables/delete/", views.delete_table_view, name="delete_table"),
    path("tables/create/", views.create_table_view, name="create_table"),

    path("menu-categories/", views.manage_menu_categories, name="menu_categories"),
    path("menu-categories/create/", views.create_category_view, name="create_menu_category"),
    path("menu-categories/delete/", views.delete_category_view, name="delete_menu_category"),
    path("menu-categories/update/", views.update_category, name="update_category"),
    path("menu-categories/json/", views.get_categories_json, name="menu_categories_json"),


    path("menu-items/", views.manage_menu_items, name="menu_items"),
    path("menu-items/<int:item_id>/json/", views.get_menu_item_json, name="menu_item_json"),
    path("menu-items/create/", views.create_menu_item_view, name="create_menu_item"),
    path("menu-items/update/", views.update_menu_item_view, name="update_menu_item"),
]