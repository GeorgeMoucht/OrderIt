from django.urls import path
from . import views
from rest_framework.routers import DefaultRouter
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView
)
from accounts.views import CustomTokenObtainPairView
from .views import LogoutView, ProtectedView, TableViewSet, MenuItemViewSet, MenuCategoryViewSet

router = DefaultRouter()
router.register(r'tables', TableViewSet, basename='table')
router.register(r'menu-items', MenuItemViewSet, basename='menuitem')

# 'menu-categories' -> all categories
# 'menu-categories/1' -> category details
# 'menu-categories/1/products' -> items of the category 1.
router.register(r'menu-categories', MenuCategoryViewSet, basename='menucategory')

urlpatterns = [
    # Authentication
    path('authentication/login/', CustomTokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('authentication/refresh/', CustomTokenObtainPairView.as_view(), name='token_refresh'),
    path('authentication/logout/', LogoutView.as_view(), name='jwt-logout'),
    path('authentication/protected', ProtectedView.as_view(), name="protected_view"),
]

urlpatterns += router.urls
