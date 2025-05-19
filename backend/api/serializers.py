from rest_framework import serializers
from .models import Table, Reserved
from .models import MenuItem, MenuCategory

class ReservedSerializer(serializers.ModelSerializer):
    class Meta:
        model = Reserved
        fields = ['id', 'name', 'time', 'created_at', 'updated_at']


class TableSerializer(serializers.ModelSerializer):
    reserved = ReservedSerializer(read_only=True)
    status = serializers.SerializerMethodField()

    class Meta:
        model = Table
        fields = ['id', 'name', 'state', 'reserved', 'status']

    def get_status(self, obj):
        if obj.state == 1 and obj.reserved:
            return "Κατειλημμένο (με κράτηση)"
        elif obj.state == 1 and obj.reserved is None:
            return "Κατειλημμένο"
        elif obj.state == 0 and obj.reserved:
            return "Κρατημένο"
        return "Ελεύθερο"

class RecursiveCategorySerializer(serializers.ModelSerializer):
    subcategories = serializers.SerializerMethodField()

    class Meta:
        model = MenuCategory
        fields= ['id', 'name', 'subcategories']

    def get_subcategories(self, obj):
        return RecursiveCategorySerializer(obj.subcategories.all(), many=True).data

class MenuItemSerializer(serializers.ModelSerializer):
    category = RecursiveCategorySerializer(read_only=True)
    image_url = serializers.SerializerMethodField()

    class Meta:
        model = MenuItem
        fields = ['id', 'name', 'description', 'price', 'category', 'image_url']

        def get_image_url(self, obj):
            request = self.context.get('request')
            if obj.image and request:
                return request.build_absolute_uri(obj.image.url)
            return None
