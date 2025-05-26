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
            ("drinks", "Sparkling Water", "Refreshing mineral water with bubbles.", 2.50),
            ("drinks", "Lemonade", "Freshly squeezed lemon juice with mint.", 3.00),
            ("drinks", "Mojito Deluxe", "White rum, mint, lime, soda & a touch of magic.", 8.50),
            ("drinks", "Spicy Margarita", "Tequila with chili and lime.", 9.00),
            ("drinks", "Gin & Tonic", "Classic G&T with a citrus twist.", 7.00),
            ("drinks", "Whiskey Sour", "Whiskey, lemon juice, simple syrup.", 7.50),

            ("coffee", "Cappuccino", "Espresso with steamed milk and foam.", 3.20),
            ("coffee", "Flat White", "Smooth coffee with creamy milk.", 3.50),
            ("coffee", "Iced Latte", "Chilled espresso and milk over ice.", 3.80),
            ("coffee", "Cold Brew", "12h slow steeped coffee.", 4.20),

            ("tea", "English Breakfast Tea", "Black tea served hot.", 2.50),
            ("tea", "Chai Latte", "Spiced tea with steamed milk.", 3.00),
            ("tea", "Iced Green Tea", "Chilled sencha with lemon.", 3.00),
            ("tea", "Peach Iced Tea", "Sweet and fruity tea.", 3.20),

            ("food", "Avocado Toast", "Sourdough, smashed avo, seeds.", 6.50),
            ("food", "Eggs Benedict", "Poached eggs, ham, hollandaise.", 7.50),
            ("food", "Club Sandwich", "Chicken, bacon, lettuce, tomato.", 6.80),
            ("food", "Veggie Panini", "Grilled vegetables with pesto.", 6.00),
            ("food", "Salmon Nigiri", "Fresh salmon on seasoned rice.", 5.50),
            ("food", "California Roll", "Crab, avocado, cucumber.", 6.00),
            ("food", "Dragon Roll", "Tempura shrimp, eel, avocado.", 8.50),
            ("food", "Rainbow Roll", "Tuna, salmon, avocado, spicy mayo.", 9.00),
        ]

        media_path = Path("media/menu")

        for category_name, name, description, price in items:
            try:
                category = MenuCategory.objects.get(name=category_name)
            except MenuCategory.DoesNotExist:
                self.stdout.write(self.style.ERROR(f"❌ Category '{category_name}' not found. Skipping item '{name}'"))
                continue

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
