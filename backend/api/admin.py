from django.contrib import admin
from .models import Table, Reserved  

@admin.register(Table)
class TableAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'state', 'reserved')  

@admin.register(Reserved)
class ReservedAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'time', 'created_at', 'updated_at')

