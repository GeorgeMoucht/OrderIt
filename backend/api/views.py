from rest_framework import generics
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework import status

class LogoutView(APIView):
    def post(self, request):
        try:
            refresh_token = request.data["refresh"]
            token = RefreshToken(refresh_token)
            token.blacklist() # Blacklist the refresh token
            return Response({
                "message": "Logout successful"
            }, status=status.HTTP_205_RESET_CONTENT)
        except Exception as e:
            return Response({
                "error": "Invalid refresh token"
            }, status=status.HTTP_400_BAD_REQUEST)


class ProtectedView(APIView):
    permission_classes = [IsAuthenticated]

    def get(self, request):
        return Response({
            "message": "This is protected data from Django."
        })