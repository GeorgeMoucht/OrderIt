from django.urls import path
from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView
)

urlpatterns = [
    # Authentication
    path('authentication/login/', TokenObtainPairView.as_view(), name='token_obtain_pair'),  # Login
    path('authentication/refresh/', TokenRefreshView.as_view(), name='token_refresh')
]
