from django.urls import path
from . import views
from rest_framework.routers import DefaultRouter
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView
)
from .views import LogoutView, ProtectedView, TableViewSet

router = DefaultRouter()
router.register(r'tables', TableViewSet, basename='table')

urlpatterns = [
    # Authentication
    path('authentication/login/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('authentication/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('authentication/logout/', LogoutView.as_view(), name='jwt-logout'),
    path('authentication/protected', ProtectedView.as_view(), name="protected_view"),
]

urlpatterns += router.urls
