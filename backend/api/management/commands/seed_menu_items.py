from django.core.management.base import BaseCommand
from api.models import MenuItem, MenuCategory
from decimal import Decimal
from django.utils.text import slugify
from django.core.files import File
from pathlib import Path

class Command(BaseCommand):
    help = "Seed menu items with descriptions and optional images."

    def handle(self, *args, **options):
        MenuItem.objects.all().delete()

        items = [
            (1, "Sparkling Water", "Refreshing mineral water with bubbles.", 2.50),
            (1, "Lemonade", "Freshly squeezed lemon juice with mint.", 3.00),
            (4, "Mojito Deluxe", "White rum, mint, lime, soda & a touch of magic.", 8.50),
            (4, "Spicy Margarita", "Tequila with chili and lime.", 9.00),
            (5, "Gin & Tonic", "Classic G&T with a citrus twist.", 7.00),
            (5, "Whiskey Sour", "Whiskey, lemon juice, simple syrup.", 7.50),
            (7, "Cappuccino", "Espresso with steamed milk and foam.", 3.20),
            (7, "Flat White", "Smooth coffee with creamy milk.", 3.50),
            (8, "Iced Latte", "Chilled espresso and milk over ice.", 3.80),
            (8, "Cold Brew", "12h slow steeped coffee.", 4.20),
            (10, "English Breakfast Tea", "Black tea served hot.", 2.50),
            (10, "Chai Latte", "Spiced tea with steamed milk.", 3.00),
            (11, "Iced Green Tea", "Chilled sencha with lemon.", 3.00),
            (11, "Peach Iced Tea", "Sweet and fruity tea.", 3.20),
            (12, "Avocado Toast", "Sourdough, smashed avo, seeds.", 6.50),
            (12, "Eggs Benedict", "Poached eggs, ham, hollandaise.", 7.50),
            (13, "Club Sandwich", "Chicken, bacon, lettuce, tomato.", 6.80),
            (13, "Veggie Panini", "Grilled vegetables with pesto.", 6.00),
            (14, "Salmon Nigiri", "Fresh salmon on seasoned rice.", 5.50),
            (14, "California Roll", "Crab, avocado, cucumber.", 6.00),
            (15, "Dragon Roll", "Tempura shrimp, eel, avocado.", 8.50),
            (15, "Rainbow Roll", "Tuna, salmon, avocado, spicy mayo.", 9.00),
        ]

        media_path = Path("media/menu")

        for category_id, name, description, price in items:
            category = MenuCategory.objects.get(id=category_id)
            slug = slugify(name)
            img_path = media_path / f"{slug}.jpg"

            item = MenuItem(
                name=name,
                description=description,
                price=Decimal(str(price)),
                category=category
            )

            if img_path.exists():
                with open(img_path, "rb") as f:
                    item.image.save(f"{slug}.jpg", File(f), save=False)

            item.save()

        self.stdout.write(self.style.SUCCESS(f"✅ Seeded {len(items)} menu items."))
