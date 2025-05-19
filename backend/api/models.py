from django.db import models

class Reserved(models.Model):
    name = models.CharField(max_length=100)
    time = models.TimeField()
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    def __str__(self):
        return f"{self.name} @ {self.time}"


class Table(models.Model):
    name = models.CharField(max_length=50)
    state = models.IntegerField(default=0)  # 0: Free, 1: Occupied
    reserved = models.ForeignKey(
        Reserved,
        on_delete=models.SET_NULL,
        null=True,
        blank=True,
        related_name="tables"
    )

    def __str__(self):
        return self.name


class MenuCategory(models.Model):
    name = models.CharField(max_length=50)
    parent = models.ForeignKey('self', null=True, blank=True, related_name='subcategories', on_delete=models.CASCADE)

    def __str__(self):
        return f"{self.parent.name + ' > ' if self.parent else ''}{self.name}"

class MenuItem(models.Model):
    name = models.CharField(max_length=100)
    description = models.TextField(blank=True)
    price = models.DecimalField(max_digits=6, decimal_places=2)
    category = models.ForeignKey(MenuCategory, on_delete=models.CASCADE)
    image = models.ImageField(upload_to='menu/', blank=True, null=True)
    
    def __str__(self):
        return f"{self.name} ({self.category})"
