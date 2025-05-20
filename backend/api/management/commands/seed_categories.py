from django.core.management.base import BaseCommand
from api.models import MenuCategory

class Command(BaseCommand):
    help = 'Seed menu category hierachy'

    def handle(self, *args, **kwargs):
        MenuCategory.objects.all().delete()

        # Top-level
        drinks = MenuCategory.objects.create(name="Drinks")
        food = MenuCategory.objects.create(name="Food")

        # Drinks subcategories
        cocktail = MenuCategory.objects.create(name="Cocktail", parent=drinks)
        MenuCategory.objects.create(name="Special", parent=cocktail)
        MenuCategory.objects.create(name="Basic", parent=cocktail)

        coffee = MenuCategory.objects.create(name="Coffee", parent=drinks)
        MenuCategory.objects.create(name="Warm", parent=coffee)
        MenuCategory.objects.create(name="Cold", parent=coffee)

        tea = MenuCategory.objects.create(name="Tea", parent=drinks)
        MenuCategory.objects.create(name="Warm", parent=tea)
        MenuCategory.objects.create(name="Cold", parent=tea)

        # Food subcategories
        MenuCategory.objects.create(name="Brunch", parent=food)
        MenuCategory.objects.create(name="Sandwiches", parent=food)

        sushi = MenuCategory.objects.create(name="Sushi", parent=food)
        MenuCategory.objects.create(name="Special", parent=sushi)

        self.stdout.write(self.style.SUCCESS("✅ Categories seeded successfully."))