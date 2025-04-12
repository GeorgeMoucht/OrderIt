from django.core.management.base import BaseCommand
from api.models import Table, Reserved
from datetime import datetime, timedelta

class Command(BaseCommand):
    help = 'Seed the database with sample table and reservation data'

    def handle(self, *args, **kwargs):
        # Καθαρίζουμε πρώτα τα δεδομένα
        Table.objects.all().delete()
        Reserved.objects.all().delete()

        self.stdout.write("⏳ Δημιουργία dummy δεδομένων...")

        # Δημιουργούμε 3 κρατήσεις
        reservations = []
        for i in range(3):
            res = Reserved.objects.create(
                name=f"Customer {i+1}",
                time=(datetime.now() + timedelta(hours=i+1)).time(),
                created_at=datetime.now(),
                updated_at=datetime.now()
            )
            reservations.append(res)

        # Δημιουργούμε τραπέζια με 4 σενάρια
        # 1. Ελεύθερα (0, null)
        Table.objects.create(name="T1", state=0, reserved=None)
        Table.objects.create(name="T2", state=0, reserved=None)

        # 2. Κρατημένα (0, reserved)
        Table.objects.create(name="T3", state=0, reserved=reservations[0])
        Table.objects.create(name="T4", state=0, reserved=reservations[1])

        # 3. Κατειλημμένα με κράτηση (1, reserved)
        Table.objects.create(name="T5", state=1, reserved=reservations[2])

        # 4. Κατειλημμένα χωρίς κράτηση (1, null)
        Table.objects.create(name="T6", state=1, reserved=None)
        Table.objects.create(name="T7", state=1, reserved=None)

        self.stdout.write(self.style.SUCCESS("✅ Dummy τραπέζια και κρατήσεις δημιουργήθηκαν!"))
