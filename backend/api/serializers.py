from rest_framework import serializers
from .models import Table, Reserved

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
        
