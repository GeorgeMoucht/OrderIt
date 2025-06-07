from rest_framework import generics
from rest_framework import status
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework.decorators import api_view , action
from rest_framework import viewsets
from .models import Table, MenuItem, MenuCategory
from .serializers import TableSerializer, MenuItemSerializer, MenuCategorySerializer


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

    def get(self):
        return Response({
            "message": "This is protected data from Django."
        })
    

class TableViewSet(viewsets.ReadOnlyModelViewSet):  # Μόνο GET (list + retrieve)
    queryset = Table.objects.all()
    serializer_class = TableSerializer

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)

        return Response({
            "success": True,
            "data": serializer.data
        })


class MenuItemViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = MenuItem.objects.all()
    serializer_class = MenuItemSerializer


class MenuCategoryViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = MenuCategory.objects.all()
    serializer_class = MenuCategorySerializer

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        serializer = self.get_serializer(queryset, many=True)
        return Response({
            "success": True,
            "data": serializer.data
        })

    @action(detail=True, methods=["get"], url_path="products")
    def products(self, request, pk=None):
        items = MenuItem.objects.filter(category_id=pk)
        serializer = MenuItemSerializer(items, many=True, context={"request": request})
        return Response({
            "success": True,
            "data": serializer.data
        })
