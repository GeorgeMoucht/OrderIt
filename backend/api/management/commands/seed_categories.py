from django.core.management.base import BaseCommand
from api.models import MenuCategory

class Command(BaseCommand):
    help = 'Seed flat menu categories (no nesting)'

    def handle(self, *args, **kwargs):
        MenuCategory.objects.all().delete()

        categories = ["drinks", "coffee", "tea", "food"]

        for cat in categories:
            MenuCategory.objects.create(name=cat)

        self.stdout.write(self.style.SUCCESS("✅ Categories seeded successfully."))
