from django.urls import path
from . import views

urlpatterns = [
    path('login/', views.login_view, name='login'),
    path('dashboard/', views.dashboard, name='dashboard'),
    path("users/", views.manage_users, name="users"),
    path("logout/", views.logout_view, name="logout")
]
