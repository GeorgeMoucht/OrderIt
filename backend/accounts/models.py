from django.db import models
from django.contrib.auth.models import AbstractUser

class User(AbstractUser):
    ROLE_CHOICE = [
        ('superadmin', 'Super Admin'),
        ('waiter', 'Waiter'),
        ('bar', 'Bar'),
        ('kitchen', 'Kitchen')
    ]

    role = models.CharField(
        max_length=20,
        choices=ROLE_CHOICE,
        default='waiter'
    )

    def __str__(self):
        return f"{self.username} ({self.role})"
