from rest_framework import serializers
from django.contrib.auth import get_user_model

User = get_user_model()

class UserUpdateSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['first_name', 'last_name', 'email']

    def validate_email(self, value):
        if not value:
            raise serializers.ValidationError("Email is required.")
        return value