from django.urls import path
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView
)
from .views import LogoutView, ProtectedView

urlpatterns = [
    # Authentication
    path('authentication/login/', TokenObtainPairView.as_view(), name='token_obtain_pair'),  # Login
    path('authentication/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
    path('authentication/logout/', LogoutView.as_view(), name='jwt-logout'),

    path('authentication/protected', ProtectedView.as_view(), name="protected_view")
]