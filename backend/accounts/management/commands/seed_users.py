from django.core.management.base import BaseCommand
from django.contrib.auth import get_user_model
from faker import Faker

User = get_user_model()
fake = Faker()

class Command(BaseCommand):
    help = 'Seed the database with initial user data'

    def handle(self, *args, **kwargs):
        if not User.objects.filter(username='admin').exists():
            User.objects.create_superuser(
                username='admin',
                email='admin@example.com',
                password='admin',
                first_name='Admin',
                last_name='User',
                role='superadmin'
            )
            self.stdout.write(self.style.SUCCESS("Superuser 'admin' created"))

        users = [
                {
                    'username': 'john',
                    'email': 'john@example.com',
                    'password': 'password123',
                    'first_name': 'John',
                    'last_name': 'Doe',
                    'role': 'waiter'
                },
                {
                    'username': 'sara',
                    'email': 'sara@example.com',
                    'password': 'password123',
                    'first_name': 'Sara',
                    'last_name': 'Smith',
                    'role': 'bar'
                },
                {
                    'username': 'mike',
                    'email': 'mike@example.com',
                    'password': 'password123',
                    'first_name': 'Mike',
                    'last_name': 'Brown',
                    'role': 'kitchen'
                },
                {
                    'username': 'anna',
                    'email': 'anna@example.com',
                    'password': 'password123',
                    'first_name': 'Anna',
                    'last_name': 'Taylor',
                    'role': 'waiter'
                },
            ]


        for user in users:
            if not User.objects.filter(username=user['username']).exists():
                User.objects.create_user(
                    username=user['username'],
                    email=user['email'],
                    password=user['password'],
                    first_name=user['first_name'],
                    last_name=user['last_name'],
                    role=user['role']
                )

                self.stdout.write(self.style.SUCCESS(f"User '{user['username']}' created"))

        for i in range(25):
            User.objects.create_user(
                username=f"user{i+1}",
                email=f"user{i+1}@example.com",
                password="demo",
                first_name=fake.first_name(),
                last_name=fake.last_name(),
                role='waiter'
            )

        self.stdout.write(self.style.SUCCESS("User seeding complete!"))
